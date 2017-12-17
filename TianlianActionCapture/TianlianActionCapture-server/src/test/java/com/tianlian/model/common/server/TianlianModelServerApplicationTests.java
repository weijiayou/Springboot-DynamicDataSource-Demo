package com.tianlian.model.common.server;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tianlian.server.TianlianModelServerApplication;
import com.tianlian.server.configs.MySqlDataSourceConfig;
import com.tianlian.actionCapture.foo.service.IFooService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TianlianModelServerApplication.class)
public class TianlianModelServerApplicationTests {

  @Autowired
//	@Reference(check = false)
  private IFooService fooService;
//

  @Autowired
  private MySqlDataSourceConfig config;

//  @Test
  public void contextLoads() {
//		fooService.aaa();
//		fooService.aaa();
//		fooService.getUserName();
//    System.out.println(fooService.getStaff());
    System.out.println(fooService.getUser());
//    System.out.println(config.getDriverClassName());
  }

}
