package com.pdd.service.impl;

import com.pdd.bean.pojo.ScheduleJob;
import com.pdd.dao.ScheduleJobDao;
import com.pdd.service.ScheduleJobService;
import com.pdd.util.ErrorCode;
import com.pdd.web.response.ResponseEntity;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:liyangpeng
 * @date:2018/10/8 17:44
 */
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {

    //日志工具类
    private Logger logger= LoggerFactory.getLogger(ScheduleJobServiceImpl.class);

    @Autowired
    private ScheduleJobDao sjd;

    @Autowired
    private Scheduler scheduler;

    @Override
    public ResponseEntity getAllTaskJob() {
        try{
            List<ScheduleJob> taskList=sjd.GetAllTaskJob();
            return ResponseEntity.success(taskList);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.error(ErrorCode.SYSTEM_ERROR_CODE,e.getMessage());
        }
    }


    @Override
    public ResponseEntity addTaskJob(ScheduleJob scheduleJob){
        try{
            boolean initSuccess=this.InitScheduler(scheduleJob);
            if(!initSuccess){
                return ResponseEntity.error(ErrorCode.SYSTEM_ERROR_CODE,"实例化定时任务失败.");
            }
            //添加定时任务时默认停止定时任务
            scheduler.pauseJob(JobKey.jobKey(scheduleJob.getJob_name(),scheduleJob.getJob_group()));
            long Result=sjd.AddTaskJob(scheduleJob);
            if(Result>0){
                logger.info("定时任务添加成功:"+scheduleJob);
                return ResponseEntity.success("定时任务添加成功.");
            }else{
                logger.error("定时任务添加失败:"+scheduleJob);;
                return ResponseEntity.success("定时任务添加失败.");
            }
        }catch (Exception e){
            logger.error("定时任务添加失败:"+e.getMessage());
            e.printStackTrace();
            return ResponseEntity.error(ErrorCode.SYSTEM_ERROR_CODE,e.getMessage());
        }
    }

    @Override
    public ResponseEntity StartTaskJob(ScheduleJob scheduleJob) throws Exception {
        boolean Exists=scheduler.checkExists(JobKey.jobKey(scheduleJob.getJob_name(),scheduleJob.getJob_group()));
        if(Exists){
            //恢复定时任务
            scheduler.resumeJob(JobKey.jobKey(scheduleJob.getJob_name(),scheduleJob.getJob_group()));
            ScheduleJob job=new ScheduleJob();
            job.setJob_id(scheduleJob.getJob_id());
            job.setJob_status("1");
            long result=sjd.UpdateTastJobStat(job);
            if(result>0){
                logger.info("定时任务开启成功:"+job);
                return ResponseEntity.success("定时任务开启成功.");
            }else{
                logger.info("定时任务开启失败:"+job);
                return ResponseEntity.error(ErrorCode.SYSTEM_ERROR_CODE,"定时任务开启失败.");
            }
        }else{
            return ResponseEntity.error(ErrorCode.SYSTEM_ERROR_CODE,"定时任务不存在无法开始.");
        }
    }

    @Override
    public ResponseEntity StopTaskJob(ScheduleJob scheduleJob) throws Exception {
        boolean Exists=scheduler.checkExists(JobKey.jobKey(scheduleJob.getJob_name(),scheduleJob.getJob_group()));
        if(Exists){
            //暂停定时任务
            scheduler.pauseJob(JobKey.jobKey(scheduleJob.getJob_name(),scheduleJob.getJob_group()));
            ScheduleJob job=new ScheduleJob();
            job.setJob_id(scheduleJob.getJob_id());
            job.setJob_status("0");
            long result=sjd.UpdateTastJobStat(job);
            if(result>0){
                logger.info("定时任务停止成功:"+job);
                return ResponseEntity.success("定时任务停止成功.");
            }else{
                logger.info("定时任务停止失败:"+job);
                return ResponseEntity.error(ErrorCode.SYSTEM_ERROR_CODE,"定时任务停止失败.");
            }
        }else{
            return ResponseEntity.error(ErrorCode.SYSTEM_ERROR_CODE,"定时任务不存在无法停止.");
        }
    }

    @Override
    public ResponseEntity EditTaskJob(ScheduleJob job) throws Exception {
        //查询出老的定时任务信息
        ScheduleJob olb=sjd.getTaskJobById(job.getJob_id());
        boolean Exists=scheduler.checkExists(JobKey.jobKey(olb.getJob_name(),olb.getJob_group()));
        //首先查询老定时器是否存在 存在则先删除
        if(Exists){
            boolean isdel=scheduler.deleteJob(JobKey.jobKey(job.getJob_name(),job.getJob_group()));
            logger.info("编辑定时器时移除老版定时器结果："+isdel);
        }
        boolean initSuccess=this.InitScheduler(job);
        if(!initSuccess){
            return ResponseEntity.error(ErrorCode.SYSTEM_ERROR_CODE,"实例化定时任务失败.");
        }
        //如果前期定时任务状态为停止则默认停止定时任务
        if(olb.getJob_status().equals("0")){
            scheduler.pauseJob(JobKey.jobKey(job.getJob_name(),job.getJob_group()));
        }
        long EditResult=sjd.EditTaskJob(job);
        if(EditResult>0){
            logger.info("修改定时器成功："+job);
            return ResponseEntity.success("编辑定时任务成功.");
        }else{
            logger.info("修改定时器失败："+job);
            return ResponseEntity.error(ErrorCode.SYSTEM_ERROR_CODE,"编辑定时任务失败.");
        }
    }

    @Override
    public ResponseEntity DeleteTaskJob(ScheduleJob job) throws Exception {
        boolean Exists=scheduler.checkExists(JobKey.jobKey(job.getJob_name(),job.getJob_group()));
        if(Exists){
            boolean isdel=scheduler.deleteJob(JobKey.jobKey(job.getJob_name(),job.getJob_group()));
            logger.info("定时器删除状态:"+isdel);
            if(isdel){
                long result=sjd.DeleteTaskJob(job.getJob_id());
                if(result>0){
                    logger.info("定时任务删除成功,数据库字段已修改:"+job);
                    return ResponseEntity.success("定时任务删除成功.");
                }else{
                    logger.info("定时任务删除失败,数据库字段未修改:"+job);
                    return ResponseEntity.error(ErrorCode.SYSTEM_ERROR_CODE,"定时任务删除失败.");
                }
            }else{
                return ResponseEntity.error(ErrorCode.SYSTEM_ERROR_CODE,"定时任务删除失败.");
            }
        }else{
            return ResponseEntity.error(ErrorCode.SYSTEM_ERROR_CODE,"定时任务不存在无法删除.");
        }
    }

    /**
     * 实例化定时任务
     * @param scheduleJob
     * @return
     */
    public boolean InitScheduler(ScheduleJob scheduleJob){
       try{
           //实例化当前添加的定时任务
           Job job=(Job)Class.forName(scheduleJob.getBean_class()).newInstance();
           //构建定时任务详情信息
           JobDetail jobDetail= JobBuilder.newJob(job.getClass()).withIdentity(scheduleJob.getJob_name(),scheduleJob.getJob_group()).withDescription(scheduleJob.getJob_desc()).build();
           //构建定时任务执行表达式
           CronScheduleBuilder scheduleBuilder= CronScheduleBuilder.cronSchedule(scheduleJob.getCron_expression());
           //构建定时任务CronTrigger
           CronTrigger trigger= TriggerBuilder.newTrigger().withIdentity(scheduleJob.getBean_class(), scheduleJob.getJob_group()).withSchedule(scheduleBuilder).build();
           //将定时器添加入内存
           scheduler.scheduleJob(jobDetail, trigger);
           logger.info("实例化定时任务成功:"+scheduleJob);
           return true;
       }catch(Exception e){
           logger.info("实例化定时任务失败:"+e.getMessage());
           e.printStackTrace();
       }
       return false;
    }
}
