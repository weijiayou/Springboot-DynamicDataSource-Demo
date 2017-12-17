package com.tianlian.server.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tianlian.actionCapture.foo.model.StaffPO;
import com.tianlian.actionCapture.foo.model.UserPO;
import com.tianlian.actionCapture.foo.service.IFooService;
import com.tianlian.saasMgr.baseService.efftoolsService.agent.service.IAgentService;
import com.tianlian.server.configs.DataSourceContextHolder;
import com.tianlian.server.configs.DatabaseType;
import com.tianlian.server.dao.mapper.FooMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class FooServiceImpl implements IFooService {

  private static final Logger logger = LoggerFactory.getLogger(FooServiceImpl.class);

  @Autowired
  private FooMapper fooMapper;

  @Reference(check = false)
  private IAgentService agentService;

  @Override
  public void aaa() {
    String s = fooMapper.getStaffNameById("0074c187282d439eb0a74aa6fc3f0190");
    String s1 = fooMapper.getStaffNameById("0074c187282d439eb0a74aa6fc3f0190");
    logger.info(s);
    logger.info(s1);
  }

  @Override
  public List<StaffPO> getStaff() {
//    DataSourceContextHolder.setDatabaseType(DatabaseType.oracleDb);
//    PageHelper.startPage(17, 10);10
    System.out.println(fooMapper.getUserName("201888"));
//    DataSourceContextHolder.setDatabaseType(DatabaseType.mysqlDb);
    PageHelper.startPage(17, 10);
    List<StaffPO> list = fooMapper.getStaff();
    PageInfo page = new PageInfo(list);
    logger.info(JSON.toJSONString(list));
    logger.info(JSON.toJSONString(page));
//    agentService.getAgentByProvinceId("146");
    System.out.println(fooMapper.getUserName("201888"));

    PageHelper.startPage(17, 10);
    List<StaffPO> list1 = fooMapper.getStaff();
    PageInfo page1 = new PageInfo(list);
    logger.info(JSON.toJSONString(list1));
    logger.info(JSON.toJSONString(page1));
    System.out.println(fooMapper.getUserName("201888"));
    return list;
  }

  @Override
  public String getUserName() {
    System.out.println(fooMapper.getUserName("201888"));
    System.out.println(fooMapper.getUserName("201888"));
    System.out.println(fooMapper.getUserName("201888"));
    System.out.println(fooMapper.getUserName("201888"));
    String s = fooMapper.getStaffNameById("0074c187282d439eb0a74aa6fc3f0190");
    return "123";
  }

  @Override
  public List<UserPO> getUser() {
    PageHelper.startPage(1, 10);
    List<UserPO> list = fooMapper.getUsers();
    PageInfo page = new PageInfo(list);
    logger.info(JSON.toJSONString(list));
    logger.info(JSON.toJSONString(page));
    return null;
  }
}
