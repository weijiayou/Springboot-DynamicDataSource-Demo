package com.tianlian.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@PropertySource(value={"classpath:dev/properties/dubbo.properties","classpath:dev/properties/mysql.properties","classpath:dev/properties/oracle.properties"})
@ImportResource(locations={"classpath:configs/dubbo-customers.xml","classpath:configs/dubbo-server.xml"})
@ComponentScan(basePackages = "com.tianlian.server")
@EnableAspectJAutoProxy
public class TianlianModelServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(TianlianModelServerApplication.class, args);
  }
}
