package com.imooc.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ServiceLogAspect {
    /**
     * AOP通知:
     * 1.前置通知：在方法调用之前执行
     * 2.后置通知：在方法正常调用之后执行
     * 3.环绕通知：在方法调用之前和之后，都分别可以执行的通知
     * 4.异常通知:如果在方法调用过程中发生异常，则通知
     * 5.最终通知：在方法调用之后执行
     */

    /**
     * 切面表达式：
     * execution 代表所要执行的表达式主体
     * 第一处 * 代表方法返回类型 *代表所有类型
     * 第二处 包名代表aop所监控的包
     * 第三处 .. 代表该包以及其子包下的所有类方法
     * 第四处 * 代表类名，*代表所有类
     * 第五处 *(..) *代表类中的方法名， (..)表示方法中的任何参数
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.imooc.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("====== 开始执行 类：{} 方法：{} ======",
                joinPoint.getTarget().getClass(),  //类
                joinPoint.getSignature().getName());  //方法名

        //记录开始时间
        long begin = System.currentTimeMillis();

        //执行目标 service
        Object result = joinPoint.proceed();

        //记录结束时间
        long end = System.currentTimeMillis();

        //时间差
        long takeTime = end - begin;

        //如果超过3秒没执行 就是错误了
        if (takeTime > 3000) {
            log.error("======= 执行结束，耗时:{}毫秒 ========", takeTime);
        } else if (takeTime > 2000) {
            log.warn("======= 执行结束，耗时:{}毫秒 ========", takeTime);
        } else {
            log.info("======= 执行结束，耗时:{}毫秒 ========", takeTime);
        }

        return result;
    }

}
