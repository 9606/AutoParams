package com.neu.autoparams.mvc.service;


import com.neu.autoparams.mvc.handler.TaskWebSocketHandler;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;


@Service
public class TaskQuartzJobBean extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        TaskWebSocketHandler.sendTaskProcessMessage(100);

        System.out.println("Previous Fire Time: " + jobExecutionContext.getPreviousFireTime());//上次触发任务的时间
        System.out.println("Current Fire Time: " + jobExecutionContext.getFireTime());//当然触发时间
        System.out.println("Next Fire Time: " + jobExecutionContext.getNextFireTime());//下次触发时间
        System.out.println("[]");
    }
}
