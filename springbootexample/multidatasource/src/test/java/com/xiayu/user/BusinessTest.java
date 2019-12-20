package com.xiayu.user;

import com.xiayu.entity.BusinessEntity;
import com.xiayu.mapper.BusinessMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BusinessTest {
    @Autowired
    private BusinessMapper businessMapper;

    @Test
    public void testInsertOrder(){
        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.setId(1);
        businessEntity.setBusinessDetail("businessdetail");
        businessMapper.insertBusiness(businessEntity);
    }

}
