package com.tianlian.server.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

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
