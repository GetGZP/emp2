package com.emp2.aop;

import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName: WebLogAspect
 * Package: com.mybatisplus.aop
 *
 * @author: guanzepeng
 * @Date: 2021/1/26 22:59 星期二
 * @Description: 请求日志切面
 */
@Aspect
@Component
public class WebLogAspect {

    private final static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    String logStr = null;

    /**
     * 以 controller 包下定义的所有请求为切入点
     */
    @Pointcut("execution(public * com.emp2.controller..*.*(..))")
    public void webLog() {
    }

    @Pointcut("execution(* com.emp2.service..*.*(..))")
    public void serviceLog() {
    }

    /**
     * 在切点之前织入
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 打印请求相关参数
        logger.info("========================================== Start ==========================================");
        // 打印请求 url
        logger.info("URL            : {}", request.getRequestURL().toString());
        // 打印 Http method
        logger.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        logger.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        // 打印请求的 IP
        logger.info("IP             : {}", request.getRemoteAddr());
        // 打印请求入参
        logger.info("Request Args   : {}", new Gson().toJson(joinPoint.getArgs()));
    }

    /**
     * 在切点之后织入
     *
     * @throws Throwable
     */
    @After("webLog()")
    public void doAfter() throws Throwable {
        logger.info("THE END!");
        // 每个请求之间空一行
    }

    /**
     * 环绕
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        // 打印出参
        logger.info("Response Args  : {}", new Gson().toJson(result));
        // 执行耗时
        logger.info("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);

        logger.info("=========================================== End ===========================================");
        // 每个请求之间空一行
        logger.info("");
        return result;
    }

    @Around("serviceLog()")
    public Object around(ProceedingJoinPoint call) throws Throwable {
        logger.info("++++++++++++++++++++++++++++++++++++++++++++开始++++++++++++++++++++++++++++++++++++++++++++");
        Object result = null;

        Object[] args = call.getArgs();

        //取得类名和方法名
        String className = call.getTarget().getClass().getName();
        String methodName = call.getSignature().getName();

        //相当于前置通知
        logStr = className + "类的" + methodName + "方法开始执行******Start******";
        logger.info(logStr);

        try {
            result = call.proceed();
            //相当于后置通知
            logStr = className + "." + methodName + "()方法正常执行结束...";
            logger.info(logStr);

        } catch (Throwable e) {
            //相当于异常抛出后通知
            StackTraceElement stackTraceElement = e.getStackTrace()[e.getStackTrace().length - 1];

            logger.info("++++++++++++++++++++++++++++++++++++++++++++异常++++++++++++++++++++++++++++++++++++++++++++");
            logger.error("===执行{}类的{}()方法的{}行", className, methodName, stackTraceElement.getLineNumber());
            logger.error("===异常信息为：{}  ", e.fillInStackTrace().toString());
            logger.error("===参数信息为：{}  ", args);
            throw e;

        } finally {
            //相当于最终通知
            logStr = className + "类的" + methodName + "方法执行结束******End******";
            logger.info(logStr);
            logger.info("++++++++++++++++++++++++++++++++++++++++++++结束++++++++++++++++++++++++++++++++++++++++++++");
        }

        return result;
    }


}
