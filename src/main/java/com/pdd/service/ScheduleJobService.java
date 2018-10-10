package com.pdd.service;

import com.pdd.bean.pojo.ScheduleJob;
import com.pdd.web.response.ResponseEntity;

/**
 * @author:liyangpeng
 * @date:2018/10/8 17:44
 */
public interface ScheduleJobService {
    /**
     * 查询全部数据库内的定时任务
     * @return
     */
    ResponseEntity getAllTaskJob();

    /**
     * 动态添加定时任务
     * @return
     */
    ResponseEntity addTaskJob(ScheduleJob scheduleJob);

    /**
     * 开启定时任务
     * @param
     * @return
     */
    ResponseEntity StartTaskJob(ScheduleJob scheduleJob) throws Exception;

    /**
     * 停止定时任务
     * @param
     * @return
     */
    ResponseEntity StopTaskJob(ScheduleJob scheduleJob) throws Exception;

    /**
     * 编辑定时任务
     * @param job
     * @return
     */
    ResponseEntity EditTaskJob(ScheduleJob job) throws Exception;

    /**
     * 删除定时任务
     * @param
     * @return
     */
    ResponseEntity DeleteTaskJob(ScheduleJob job) throws Exception;

    /**
     * 实例化定时任务
     * @param scheduleJob
     * @return
     */
    boolean InitScheduler(ScheduleJob scheduleJob);
}
