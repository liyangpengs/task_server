package com.pdd.bean.pojo;

import java.util.Date;

/**
 * @author:liyangpeng
 * @date:2018/10/8 17:43
 */
public class ScheduleJob {
    private Integer job_id;
    private String  job_name;
    private String  job_group;
    private String  job_status;
    private String  cron_expression;
    private String  bean_class;
    private Date create_time;
    private Date    update_time;
    private String  job_desc;
    private String job_isdel;

    public String getJob_isdel() {
        return job_isdel;
    }

    public void setJob_isdel(String job_isdel) {
        this.job_isdel = job_isdel;
    }

    public Integer getJob_id() {
        return job_id;
    }

    public void setJob_id(Integer job_id) {
        this.job_id = job_id;
    }

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public String getJob_group() {
        return job_group;
    }

    public void setJob_group(String job_group) {
        this.job_group = job_group;
    }

    public String getJob_status() {
        return job_status;
    }

    public void setJob_status(String job_status) {
        this.job_status = job_status;
    }

    public String getCron_expression() {
        return cron_expression;
    }

    public void setCron_expression(String cron_expression) {
        this.cron_expression = cron_expression;
    }

    public String getBean_class() {
        return bean_class;
    }

    public void setBean_class(String bean_class) {
        this.bean_class = bean_class;
    }

    public Date getCreate_time() {
        if(create_time==null)
            return new Date();
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        if(update_time==null)
            return new Date();
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getJob_desc() {
        return job_desc;
    }

    public void setJob_desc(String job_desc) {
        this.job_desc = job_desc;
    }

    @Override
    public String toString() {
        return "ScheduleJob{" +
                "job_id=" + job_id +
                ", job_name='" + job_name + '\'' +
                ", job_group='" + job_group + '\'' +
                ", job_status='" + job_status + '\'' +
                ", cron_expression='" + cron_expression + '\'' +
                ", bean_class='" + bean_class + '\'' +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                ", job_desc='" + job_desc + '\'' +
                '}';
    }
}
