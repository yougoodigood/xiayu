package com.xiayu.aspect;

import com.xiayu.annotation.DBRouting;
import com.xiayu.config.RequestThreadLocalHolder;
import com.xiayu.constants.DataSourceConstants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
public class DBRoutingAspect {

    @Pointcut("@annotation(com.xiayu.annotation.DBRouting)")
    public void pointCut(){};

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) throws NoSuchMethodException {
        log.info("start request");
        long startTime = System.currentTimeMillis();

        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        DBRouting dbRouting = method.getAnnotation(DBRouting.class);
        String isRead = dbRouting.isRead();
        boolean read = Boolean.parseBoolean(isRead);
        if (read){
            RequestThreadLocalHolder.setDatabaseIndex(DataSourceConstants.SLAVE_ONE_DATA_SOURCE);
        }
        long endTime = System.currentTimeMillis();
        log.info("response end");
        log.info("time:" + (endTime - startTime));
    }

    @After("pointCut()")
    public void after(JoinPoint joinPoint){
        Method method = getMethod(joinPoint);
        DBRouting dbRouting = method.getAnnotation(DBRouting.class);
        String isRead = dbRouting.isRead();
        boolean read = Boolean.parseBoolean(isRead);
        if (read){
            RequestThreadLocalHolder.removeDatabasIndex();
        }
    }

    private Method getMethod(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        return method;
    }

}
