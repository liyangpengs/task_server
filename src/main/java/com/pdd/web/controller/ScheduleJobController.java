package com.pdd.web.controller;

import com.pdd.bean.pojo.ScheduleJob;
import com.pdd.service.ScheduleJobService;
import com.pdd.util.ErrorCode;
import com.pdd.web.response.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:liyangpeng
 * @date:2018/10/8 18:06
 */
@RestController
public class ScheduleJobController {

    @Autowired
    private ScheduleJobService sjs;

    @GetMapping("/getAllTaskJob")
    public ResponseEntity getAllTaskJob(){
        return sjs.getAllTaskJob();
    }

    @PostMapping("/addTaskJon")
    public ResponseEntity addTaskJon(ScheduleJob job){
        return sjs.addTaskJob(job);
    }

    @PostMapping("/StartTaskJob")
    public ResponseEntity StartTaskJob(ScheduleJob job_id){
      try{
          return sjs.StartTaskJob(job_id);
      }catch (Exception e){
          e.printStackTrace();
          return ResponseEntity.error(ErrorCode.SYSTEM_ERROR_CODE,"开启定时任务出现异常.");
      }
    }

    @PostMapping("/StopTaskJob")
    public ResponseEntity StopTaskJob(ScheduleJob job_id){
        try{
            return sjs.StopTaskJob(job_id);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.error(ErrorCode.SYSTEM_ERROR_CODE,"停止定时任务出现异常.");
        }
    }

    @PostMapping("/EditTaskJob")
    public ResponseEntity EditTaskJob(ScheduleJob job){
        try{
            return sjs.EditTaskJob(job);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.error(ErrorCode.SYSTEM_ERROR_CODE,"编辑定时任务出现异常.");
        }
    }

    @PostMapping("/DeleteTaskJob")
    public ResponseEntity DeleteTaskJob(ScheduleJob job_id){
        try{
            return sjs.DeleteTaskJob(job_id);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.error(ErrorCode.SYSTEM_ERROR_CODE,"删除定时任务出现异常.");
        }
    }
}