package com.ron.springboot_api.aspect;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.ron.springboot_api.api.LogTestApi.log(..))")
    public void log() {

    }

    @Before("log()")
    public void doBefore() {
        logger.info("--------- doBefore 1-------");
    }

    @After("log()")
    public void doAfter() {
        logger.info("------------ doAfter 2------");
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturning(Object result) {
        logger.info("------------doAfterReturning --------- : 內容 {}",result);
    }
}
