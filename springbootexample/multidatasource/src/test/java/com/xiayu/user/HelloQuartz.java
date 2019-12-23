package com.xiayu.user;

import com.xiayu.cronjob.CronJobDemo;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class HelloQuartz {
    public static void main(String[] args) {
        try{
            //通过schedulerFactory创建Schedule
            SchedulerFactory schedulerFactory= new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();

            //创建jobDetail，需要指定实现Job接口的类
            JobDetail job = JobBuilder.newJob(CronJobDemo.class)
                    .withIdentity("jobDetailId", "jobDetailGroupName")//helloJob为id,hello为jobDetail组
                    .usingJobData("cronJobKeyOne","jobDataElementOneValue") //key 与 CronJobDemo的属性一致并且有set方法，会自动赋值
                    .usingJobData("cronJobKeyTwo","jobDataElementTwoValue")
                    .usingJobData("cronJobKeyThree","jobDataElementThreeValue")
                    .build();

//            Trigger trigger = TriggerBuilder.newTrigger()
//                                            .withIdentity("triggerId","triggerGroup")
//                                            .usingJobData("triggerJobDataKeyOne","triggerJobDataValueOne")
//                                            .usingJobData("triggerJobDataKeyTwo","triggerJobDataValueOne")
//                                            .usingJobData("triggerJobDataKeyThree","triggerJobDataValueOne")
//                                            .startNow()
//                                            .withSchedule(SimpleScheduleBuilder
//                                                    .simpleSchedule()
//                                                    .withIntervalInSeconds(5)
//                                                    .repeatForever())
//                                            .build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("triggerId","triggerGroup")
                    .usingJobData("triggerJobDataKeyOne","triggerJobDataValueOne")
                    .usingJobData("triggerJobDataKeyTwo","triggerJobDataValueOne")
                    .usingJobData("triggerJobDataKeyThree","triggerJobDataValueOne")
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/1 * * * * ? "))
                    .build();
            scheduler.scheduleJob(job,trigger);
            scheduler.start();
        }catch (Exception e){

        }

    }
}
