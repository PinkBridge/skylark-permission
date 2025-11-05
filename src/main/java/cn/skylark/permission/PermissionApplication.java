package cn.skylark.permission;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yaomianwei
 */
@SpringBootApplication
@MapperScan("cn.skylark.permission.oauth2.mapper")
public class PermissionApplication {
  public static void main(String[] args) {
    SpringApplication.run(PermissionApplication.class, args);
  }

}
