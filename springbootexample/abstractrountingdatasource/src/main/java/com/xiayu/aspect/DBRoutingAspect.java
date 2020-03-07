package com.xiayu.aspect;

import com.xiayu.annotation.DBRouting;
import com.xiayu.config.RequestThreadLocalHolder;
import com.xiayu.constants.DataSourceConstants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
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
    public void pointCut(){}; //连接点

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) throws NoSuchMethodException { //前置通知
        log.info("start request");
        long startTime = System.currentTimeMillis();

        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod(); //获取到加入注解的方法
        DBRouting dbRouting = method.getAnnotation(DBRouting.class); //获取到DBRouting注解
        String isRead = dbRouting.isRead(); //获取到设置的值
        boolean read = Boolean.parseBoolean(isRead);
        if (read){
            RequestThreadLocalHolder.setDatabaseIndex(DataSourceConstants.SLAVE_ONE_DATA_SOURCE);
            //如果read 为true的话，就将线程局部变量设置为从库，然后在通过与lookupkey进行比较，获取到从库的数据源
        }
        long endTime = System.currentTimeMillis();
        log.info("response end");
        log.info("time:" + (endTime - startTime));
    }

    @After("pointCut()")
    public void after(JoinPoint joinPoint){ //后置通知，将线程局部变量清除掉
        Method method = getMethod(joinPoint);
        DBRouting dbRouting = method.getAnnotation(DBRouting.class);
        String isRead = dbRouting.isRead();
        boolean read = Boolean.parseBoolean(isRead);
        if (read){
            RequestThreadLocalHolder.removeDatabasIndex(); //remove
        }
    }

    private Method getMethod(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        return method;
    }

}
