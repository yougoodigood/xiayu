package com.xiayu.user;

import com.xiayu.entity.BusinessEntity;
import com.xiayu.mapper.BusinessMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BusinessTest {
    @Autowired
    private BusinessMapper businessMapper;

    Logger log = LoggerFactory.getLogger(BusinessTest.class);

    @Test
    public void testInsertOrder(){
        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.setId(4);
        businessEntity.setBusinessDetail("businessdetail");
        businessMapper.insertBusiness(businessEntity);
        log.info("success");
        log.error("failed");
    }

    @Test
    public void testLog(){
        log.error("error");
        log.info("info");
        log.debug("debug");
        log.trace("trace");
    }
}
