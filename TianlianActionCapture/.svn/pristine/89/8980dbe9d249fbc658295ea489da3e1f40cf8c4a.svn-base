package com.tianlian.server.dao.mapper;

import com.tianlian.actionCapture.foo.model.StaffPO;
import com.tianlian.actionCapture.foo.model.UserPO;
import com.tianlian.server.configs.DS;
import com.tianlian.server.configs.DatabaseType;
//import com.tianlian.server.configs.MysqlDS;
//import com.tianlian.server.configs.OracleDS;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FooMapper {

  @DS(DatabaseType.mysqlDb)
  String getStaffNameById(@Param("userId") String userId);

  @DS(DatabaseType.mysqlDb)
  List<StaffPO> getStaff();

  @DS(DatabaseType.oracleDb)
  String getUserName(@Param("userId") String userId);

  @DS(DatabaseType.oracleDb)
  List<UserPO> getUsers();
}
