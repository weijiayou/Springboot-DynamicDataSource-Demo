package com.tianlian.server.configs;

import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DynamicDataSourceAspect {

  private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

  @Pointcut(value = "execution(* com.tianlian.server.dao.mapper.*.*(..))")
  private void pointcut() {
  }

  //  @Before("pointcut()")
  @Around("pointcut()")
//  public void before(JoinPoint point) {
  public void around(ProceedingJoinPoint point) throws Throwable {
    DataSourceContextHolder.clearDatabaseType();
    Object target = point.getTarget();
    String method = point.getSignature().getName();

    Class<?>[] classz = target.getClass().getInterfaces();

    Class<?>[] parameterTypes = ((MethodSignature) point.getSignature())
        .getMethod().getParameterTypes();
    //设置默认数据源
    DatabaseType dataSource = DataSourceContextHolder.DEFAULT_DS;
    String methodName = "";
    try {
      Method m = classz[0].getMethod(method, parameterTypes);
      if (m != null && m.isAnnotationPresent(DS.class)) {
        DS annotation = m
            .getAnnotation(DS.class);
        dataSource = annotation.value();
        methodName = m.getName();
      }
    } catch (Exception e) {
      logger.error("DataSource switch error:{}", e.getMessage(), e);
    } finally {
      logger.info("{} | method  {}  | datasource  {}  | begin",
          ((MethodSignature) point.getSignature()).getMethod().getDeclaringClass(), methodName,
          dataSource);
    }
    DataSourceContextHolder.setDatabaseType(dataSource);

    point.proceed();

    DataSourceContextHolder.clearDatabaseType();

    logger.info("{} | method  {}  | datasource  {}  | end",
        ((MethodSignature) point.getSignature()).getMethod().getDeclaringClass(), methodName,
        dataSource);

  }

//  @After("pointcut()")
//  public void after(JoinPoint point) {
//
//    Object target = point.getTarget();
//    String method = point.getSignature().getName();
//    System.out.println(method);
//    Class<?>[] parameterTypes = ((MethodSignature) point.getSignature())
//        .getMethod().getParameterTypes();
//    String methodName = "";
//
//    Class<?>[] classz = target.getClass().getInterfaces();
//    try {
//      Method m = classz[0].getMethod(method, parameterTypes);
//      methodName = m.getName();
//    } catch (Exception e) {
//      logger.error("DataSource switch after error:{}", e.getMessage(), e);
//    }
//
//    logger.info("{} | method  {}  | datasource  {}  | end",
//        ((MethodSignature) point.getSignature()).getMethod().getDeclaringClass(), methodName,
//        DataSourceContextHolder.getDatabaseType());
//
//    DataSourceContextHolder.clearDatabaseType();
//  }

}
