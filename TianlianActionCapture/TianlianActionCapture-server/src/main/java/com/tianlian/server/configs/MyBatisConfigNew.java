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
   * 创建数据源(数据源的名称：方法名可以取为XXXDataSource(),XXX为数据库名称,该名称也就是数据源的名称)
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
   * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
   * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
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
    dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
    dataSource.setDefaultTargetDataSource(mysqlDataSource);// 默认的datasource设置为myTestDbDataSource
//    dataSource.setDefaultTargetDataSource(oracleDataSource);// 默认的datasource设置为myTestDbDataSource

    return dataSource;
  }

  @Bean
  public SqlSessionFactory sqlSessionFactory(
      @Qualifier("mysqlDataSource") DataSource myTestDbDataSource,
      @Qualifier("oracleDataSource") DataSource myTestDb2DataSource) throws Exception {
    SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
    fb.setDataSource(this.dataSource(myTestDbDataSource, myTestDb2DataSource));
//    fb.setTypeAliasesPackage("com.tianlian.server.model");
//             fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapperLocations")));
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//    fb.setMapperLocations(resolver.getResources("classpath:mapper/**/*.xml"));

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
//    ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
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
