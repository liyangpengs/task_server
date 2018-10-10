package com.pdd.dao;

import com.pdd.bean.pojo.ScheduleJob;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author:liyangpeng
 * @date:2018/10/8 17:58
 */
@Mapper
public interface ScheduleJobDao {
    /**
     * 查询全部定时任务
     * @return
     */
    List<ScheduleJob> GetAllTaskJob();

    /**
     * 添加定时任务
     * @return
     */
    long AddTaskJob(ScheduleJob job);

    /**
     * 修改定时任务状态
     * @param job
     * @return
     */
    long UpdateTastJobStat(ScheduleJob job);

    /**
     * 重新编辑定时任务
     * @param job
     * @return
     */
    long EditTaskJob(ScheduleJob job);

    /**
     * 删除定时任务
     * @param job_id
     * @return
     */
    long DeleteTaskJob(long job_id);

    /**
     * 通过job_id查询定时任务
     * @param job_id
     * @return
     */
    ScheduleJob getTaskJobById(long job_id);
}
