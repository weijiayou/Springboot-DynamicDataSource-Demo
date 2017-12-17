package com.tianlian.server.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
public class OracleDataSourceConfig {

  @Value("${oracle.jdbc.driverClassName}")
  private String driverClassName;

  @Value("${oracle.jdbc.url}")
  private String url;

  @Value("${oracle.jdbc.username}")
  private String username;

  @Value("${oracle.jdbc.password}")
  private String password;

  @Value("${oracle.jdbc.initialSize}")
  private Integer initialSize;

  @Value("${oracle.jdbc.maxActive}")
  private Integer maxActive;

  @Value("${oracle.jdbc.minPoolSize}")
  private Integer minPoolSize;

  @Value("${oracle.jdbc.maxWait}")
  private Long maxWait;

  @Value("${oracle.jdbc.minIdle}")
  private Integer minIdle;

  @Value("${oracle.jdbc.timeBetweenEvictionRunsMillis}")
  private Long timeBetweenEvictionRunsMillis;

  @Value("${oracle.jdbc.minEvictableIdleTimeMillis}")
  private Long minEvictableIdleTimeMillis;

  @Value("${oracle.jdbc.validationQuery}")
  private String validationQuery;

  @Value("${oracle.jdbc.testWhileIdle}")
  private Boolean testWhileIdle;

  @Value("${oracle.jdbc.testOnBorrow}")
  private Boolean testOnBorrow;

  @Value("${oracle.jdbc.testOnReturn}")
  private Boolean testOnReturn;

  @Value("${oracle.jdbc.maxOpenPreparedStatements}")
  private Integer maxOpenPreparedStatements;

  @Value("${oracle.jdbc.removeAbandoned}")
  private Boolean removeAbandoned;

  @Value("${oracle.jdbc.removeAbandonedTimeout}")
  private Integer removeAbandonedTimeout;

  @Value("${oracle.jdbc.logAbandoned}")
  private Boolean logAbandoned;

  @Value("${oracle.jdbc.poolPreparedStatements}")
  private Boolean poolPreparedStatements;

  @Value("${oracle.jdbc.filters}")
  private String filters;
}
