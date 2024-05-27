package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    @Pointcut("execution(* com.sky.mapper.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {
    }

    @Around("autoFillPointCut()")
    public boolean autoFill(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("开始公共字段自动填充");
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        OperationType type = methodSignature.getMethod().getAnnotation(AutoFill.class).value();

        Object[] args = proceedingJoinPoint.getArgs();
        if (args == null || args.length == 0) {
            return false;
        }
        Object entity = args[0];
        Long currentId = BaseContext.getCurrentId();
        Method setCreteUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
        Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
        Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
        Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);

        if (type == OperationType.INSERT) {
            setCreteUser.invoke(entity, currentId);
            setUpdateUser.invoke(entity, currentId);
            setCreateTime.invoke(entity, LocalDateTime.now());
            setUpdateTime.invoke(entity, LocalDateTime.now());
        } else if (type == OperationType.UPDATE) {
            setUpdateUser.invoke(entity, currentId);
            setUpdateTime.invoke(entity, LocalDateTime.now());

        }
        proceedingJoinPoint.proceed();
        return true;
    }


}
