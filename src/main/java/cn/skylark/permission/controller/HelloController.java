package cn.skylark.permission.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * todo
 *
 * @author yaomianwei
 * @since 18:15 2025/10/31
 **/
@RestController
public class HelloController {
  @GetMapping("/hello")
  public String hello() {
    return "hello";
  }
}
