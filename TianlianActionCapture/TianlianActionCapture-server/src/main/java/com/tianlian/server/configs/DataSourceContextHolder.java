package com.tianlian.server.configs;

public class DataSourceContextHolder {

  /**
   * 默认数据源
   */
  public static final DatabaseType DEFAULT_DS = DatabaseType.mysqlDb;
//  public static final DatabaseType DEFAULT_DS = DatabaseType.oracleDb;

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
