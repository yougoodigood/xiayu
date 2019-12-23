package com.xiayu.cronjob;

import lombok.Data;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Data
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class CronJobDemo implements Job {
    private static Logger logger = LoggerFactory.getLogger(CronJobDemo.class);

    private String cronJobKeyOne;

    private String cronJobKeyTwo;

    private String cronJobKeyThree;

    @Override
    public void execute(JobExecutionContext Context) throws JobExecutionException {
        JobDataMap jobDataMap = Context.getJobDetail().getJobDataMap();
        logger.info("jobDataMap:"+jobDataMap.toString());
        logger.info("triggerJobDataMap:"+Context.getTrigger().getJobDataMap().size());
        logger.info("Context.getJobDetail().getKey():"+Context.getJobDetail().getKey());
        logger.info("Context.getTrigger().getKey():"+Context.getTrigger().getKey());
        logger.info(cronJobKeyOne);
        logger.info(cronJobKeyTwo);
        logger.info(cronJobKeyThree);
        logger.info("cronJobDemo start");
        logger.info("cronJobDemo running");
        logger.info("cronJobDemo stop");
    }
}
