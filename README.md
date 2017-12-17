#Springboot+dubbo+mybatis+lombok+pagehelper+aop动态配置oracle、mysql数据源

    用公司新搭的maven脚手架创建springboot工程，因为脚手架功能未完善，创建出的工程主要就是引了springboot基础包并创建了目录结构，所以需要自己添加框架来搭建工程，也能通过这个过程来更深入了解相关框架，提升自己。
* springboot程序入口:`TianlianModelServerApplication.java`

```
package com.tianlian.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

//因为用springboot默认的数据源只能配置一套，而我们需要从多个数据源来查询数据，
//因此用@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})来关闭自动配置功能。
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
//用@PropertySource来加载properties文件
@PropertySource(value={"classpath:dev/properties/dubbo.properties","classpath:dev/properties/mysql.properties","classpath:dev/properties/oracle.properties"})
//加载xml配置文件
@ImportResource(locations={"classpath:configs/dubbo-customers.xml","classpath:configs/dubbo-server.xml"})
@ComponentScan(basePackages = "com.tianlian.server")
//开启AOP代理自动配置
@EnableAspectJAutoProxy
public class TianlianModelServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(TianlianModelServerApplication.class, args);
  }
}

``` 

* 读取mysql配置:`MySqlDataSourceConfig.java`(OracleDataSourceConfig.java类似)

```
package com.tianlian.server.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *用@Value注解来给属性赋值，分别创建oracle数据源的bean、mysql数据源的bean,
 *并用@componen把bean交给spring管理，方便后面创建不同数据源时使用。
 */
@Data
@Component
public class MySqlDataSourceConfig {

  @Value("${mysql.jdbc.driverClassName}")
  private String driverClassName;

  @Value("${mysql.jdbc.url}")
  private String url;

  @Value("${mysql.jdbc.username}")
  private String username;

  @Value("${mysql.jdbc.password}")
  private String password;

  @Value("${mysql.jdbc.initialSize}")
  private Integer initialSize;

  @Value("${mysql.jdbc.maxActive}")
  private Integer maxActive;

  @Value("${mysql.jdbc.minPoolSize}")
  private Integer minPoolSize;

  @Value("${mysql.jdbc.maxWait}")
  private Long maxWait;

  @Value("${mysql.jdbc.minIdle}")
  private Integer minIdle;

  @Value("${mysql.jdbc.timeBetweenEvictionRunsMillis}")
  private Long timeBetweenEvictionRunsMillis;

  @Value("${mysql.jdbc.minEvictableIdleTimeMillis}")
  private Long minEvictableIdleTimeMillis;

  @Value("${mysql.jdbc.validationQuery}")
  private String validationQuery;

  @Value("${mysql.jdbc.testWhileIdle}")
  private Boolean testWhileIdle;

  @Value("${mysql.jdbc.testOnBorrow}")
  private Boolean testOnBorrow;

  @Value("${mysql.jdbc.testOnReturn}")
  private Boolean testOnReturn;

  @Value("${mysql.jdbc.maxOpenPreparedStatements}")
  private Integer maxOpenPreparedStatements;

  @Value("${mysql.jdbc.removeAbandoned}")
  private Boolean removeAbandoned;

  @Value("${mysql.jdbc.removeAbandonedTimeout}")
  private Integer removeAbandonedTimeout;

  @Value("${mysql.jdbc.logAbandoned}")
  private Boolean logAbandoned;

  @Value("${mysql.jdbc.poolPreparedStatements}")
  private Boolean poolPreparedStatements;

  @Value("${mysql.jdbc.filters}")
  private String filters;
}

```
* 配置mybatis：`MyBatisConfigNew.java`

```
package com.tianlian.server.configs;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = "com.tianlian.server.dao.mapper")
public class MyBatisConfigNew {

  @Autowired
  private MySqlDataSourceConfig mysqlJdbcMapper;

  @Autowired
  private OracleDataSourceConfig oracleJdbcMapper;

  /**
   *根据oracle和mysql的配置属性bean分别创建oracle数据源bean、mysql数据源bean
   */
  @Bean
  public DataSource mysqlDataSource() throws Exception {
    DruidDataSource datasource = new DruidDataSource();

    datasource.setUrl(mysqlJdbcMapper.getUrl());
    datasource.setUsername(mysqlJdbcMapper.getUsername());
    datasource.setPassword(mysqlJdbcMapper.getPassword());
    datasource.setDriverClassName(mysqlJdbcMapper.getDriverClassName());
    datasource.setInitialSize(mysqlJdbcMapper.getInitialSize());
    datasource.setMinIdle(mysqlJdbcMapper.getMinIdle());
    datasource.setMaxActive(mysqlJdbcMapper.getMaxActive());
    datasource.setMaxWait(mysqlJdbcMapper.getMaxWait());
    datasource.setTimeBetweenEvictionRunsMillis(mysqlJdbcMapper.getTimeBetweenEvictionRunsMillis());
    datasource.setMinEvictableIdleTimeMillis(mysqlJdbcMapper.getMinEvictableIdleTimeMillis());
    datasource.setValidationQuery(mysqlJdbcMapper.getValidationQuery());
    datasource.setTestWhileIdle(mysqlJdbcMapper.getTestWhileIdle());
    datasource.setTestOnBorrow(mysqlJdbcMapper.getTestOnBorrow());
    datasource.setTestOnReturn(mysqlJdbcMapper.getTestOnReturn());
    datasource.setPoolPreparedStatements(mysqlJdbcMapper.getPoolPreparedStatements());
    return datasource;
  }

  @Bean
  public DataSource oracleDataSource() throws Exception {
    DruidDataSource datasource = new DruidDataSource();

    datasource.setUrl(oracleJdbcMapper.getUrl());
    datasource.setUsername(oracleJdbcMapper.getUsername());
    datasource.setPassword(oracleJdbcMapper.getPassword());
    datasource.setDriverClassName(oracleJdbcMapper.getDriverClassName());
    datasource.setInitialSize(oracleJdbcMapper.getInitialSize());
    datasource.setMinIdle(oracleJdbcMapper.getMinIdle());
    datasource.setMaxActive(oracleJdbcMapper.getMaxActive());
    datasource.setMaxWait(oracleJdbcMapper.getMaxWait());
    datasource
        .setTimeBetweenEvictionRunsMillis(oracleJdbcMapper.getTimeBetweenEvictionRunsMillis());
    datasource.setMinEvictableIdleTimeMillis(oracleJdbcMapper.getMinEvictableIdleTimeMillis());
    datasource.setValidationQuery(oracleJdbcMapper.getValidationQuery());
    datasource.setTestWhileIdle(oracleJdbcMapper.getTestWhileIdle());
    datasource.setTestOnBorrow(oracleJdbcMapper.getTestOnBorrow());
    datasource.setTestOnReturn(oracleJdbcMapper.getTestOnReturn());
    datasource.setPoolPreparedStatements(oracleJdbcMapper.getPoolPreparedStatements());
    return datasource;
  }

  /**
   * 创建动态数据源，将上面创建的数据源交给动态数据源管理(即放到抽象类AbstractRoutingDataSource中的targetDataSources管理)
   */
  @Bean
  @Primary
  public DynamicDataSource dataSource(
      @Qualifier("mysqlDataSource") DataSource mysqlDataSource,
      @Qualifier("oracleDataSource") DataSource oracleDataSource) {
    Map<Object, Object> targetDataSources = new HashMap<>();
    targetDataSources.put(DatabaseType.mysqlDb, mysqlDataSource);
    targetDataSources.put(DatabaseType.oracleDb, oracleDataSource);
    DynamicDataSource dataSource = new DynamicDataSource();
    // 该方法是AbstractRoutingDataSource的方法
    dataSource.setTargetDataSources(targetDataSources);
    // 默认的datasource设置为myTestDbDataSource
    dataSource.setDefaultTargetDataSource(mysqlDataSource);
    return dataSource;
  }

  /**
   *将动态数据源交给mybatis的SqlSessionFactoryBean，并添加PageHelper分页插件。
   *因为要切换数据源，必须要把PageHelper的autoRuntimeDialect属性设置为true才能在不同类新的数据源切换时，
   *使用不同数据源的分页方式。
   */
  @Bean
  public SqlSessionFactory sqlSessionFactory(
      @Qualifier("mysqlDataSource") DataSource mysqlDataSource,
      @Qualifier("oracleDataSource") DataSource oracleDataSource) throws Exception {
    SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
    fb.setDataSource(this.dataSource(mysqlDataSource, oracleDataSource));
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    //分页插件
    PageHelper pageHelper = new PageHelper();
    Properties properties = new Properties();
    properties.setProperty("reasonable", "true");
    properties.setProperty("supportMethodsArguments", "true");
    properties.setProperty("returnPageInfo", "check");
    properties.setProperty("params", "count=countSql");
    properties.setProperty("autoRuntimeDialect", "true");
    pageHelper.setProperties(properties);
    //添加插件
    fb.setPlugins(new Interceptor[]{pageHelper});
    
    //添加XML目录
    try {
      fb.setMapperLocations(resolver.getResources("classpath:mapper/**/*.xml"));
      return fb.getObject();
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  @Bean
  public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
    return new SqlSessionTemplate(sqlSessionFactory);
  }

  /**
   * 配置事务管理器
   */
  @Bean
  public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource)
      throws Exception {
    return new DataSourceTransactionManager(dataSource);
  }
}

```

* 用ThreadLocal为每个线程保存各自选择的数据源：`DataSourceContextHolder.java`

```
package com.tianlian.server.configs;

public class DataSourceContextHolder {

  /**
   * 默认数据源
   */
  public static final DatabaseType DEFAULT_DS = DatabaseType.mysqlDb;
  //public static final DatabaseType DEFAULT_DS = DatabaseType.oracleDb;

  private static final ThreadLocal<DatabaseType> contextHolder = new ThreadLocal<>();

  public static void setDatabaseType(DatabaseType type) {
    contextHolder.set(type);
  }

  public static DatabaseType getDatabaseType() {
    return contextHolder.get();
  }

  public static void clearDatabaseType() {
    contextHolder.remove();
  }
}

```
* 继承抽象类AbstractRoutingDataSource实现抽象方法——选择切换到哪个数据源的方法：`DynamicDataSource.java`

```
package com.tianlian.server.configs;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

  @Override
  protected Object determineCurrentLookupKey() {
    return DataSourceContextHolder.getDatabaseType();
  }

}

```

* 定义一个注解DS，后面用它来标识用哪个数据源来查询:`DS.java`

```
package com.tianlian.server.configs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({
    ElementType.METHOD
})
public @interface DS {

  DatabaseType value() default DatabaseType.mysqlDb;

}

```
* 利用spring的aop来实现根据注解来动态切换数据源的动作:`DynamicDataSourceAspect.java`

```
package com.tianlian.server.configs;

import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DynamicDataSourceAspect {

  private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

  //首先要定义一个切点——mapper接口下的所有查询方法
  @Pointcut(value = "execution(* com.tianlian.server.dao.mapper.*.*(..))")
  private void pointcut() {
  }

  @Around("pointcut()")
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
      //根据方法上的DS注解的值来设置数据源
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

}

```
* 接下来就可以写测试用的查询接口来尝试一下数据切换功能了。

**如果有写的不好的地方欢迎拍砖**<br />
源码地址：<https://github.com/weijiayou/JavaUtil_CopyProperties>
