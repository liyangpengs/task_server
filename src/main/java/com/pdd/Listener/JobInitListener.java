package com.pdd.Listener;

import com.pdd.bean.pojo.ScheduleJob;
import com.pdd.dao.ScheduleJobDao;
import com.pdd.service.ScheduleJobService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

/**
 * @author:liyangpeng
 * @date:2018/10/9 17:33
 */
@WebListener
public class JobInitListener implements ServletContextListener {

    @Autowired
    private ScheduleJobDao sjd;

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private ScheduleJobService sjs;

    private final Logger logger= LoggerFactory.getLogger(JobInitListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce){
        //从数据库加载出全部定时任务
        List<ScheduleJob> jobList=sjd.GetAllTaskJob();
        for (ScheduleJob scheduleJob:jobList) {
           try{
               logger.info("开始实例化定时任务:"+scheduleJob);
               //实例化当前添加的定时任务
               boolean InitSuccess=sjs.InitScheduler(scheduleJob);
               logger.info("实例化结果:"+InitSuccess);
               if(scheduleJob.getJob_status().equals("0")){
                   //如果定时任务状态为暂停则先停止定时任务.
                   logger.info("定时任务状态更新为停止:"+scheduleJob);
                   scheduler.pauseJob(JobKey.jobKey(scheduleJob.getJob_name(),scheduleJob.getJob_group()));
               }
               logger.info("实例化定时任务成功:"+scheduleJob);
           }catch (Exception e){
               logger.info("实例化定时任务出现异常:"+scheduleJob);
                e.printStackTrace();
           }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("监听器已经销毁.");
        List<ScheduleJob> jobList=sjd.GetAllTaskJob();
        for (ScheduleJob scheduleJob:jobList) {
            try{
                logger.info("开始移除定时任务:"+scheduleJob);
                boolean isdel=scheduler.deleteJob(JobKey.jobKey(scheduleJob.getJob_name(),scheduleJob.getJob_group()));
                logger.info("移除定时任务结果:"+isdel);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
