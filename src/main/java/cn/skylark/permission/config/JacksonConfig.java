package cn.skylark.permission.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Jackson配置类 - 全局日期时间格式化
 *
 * @author yaomianwei
 * @since 2025/11/3
 */
@Configuration
public class JacksonConfig {

  /**
   * 日期时间格式化器：YYMMDD HHMMSS
   */
  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

  @Bean
  public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
    Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
    
    // 注册自定义的 LocalDateTime 序列化器
    SimpleModule module = new SimpleModule();
    module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
    builder.modules(module);
    
    return builder;
  }

  /**
   * LocalDateTime 自定义序列化器
   */
  public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws java.io.IOException {
      if (value == null) {
        gen.writeNull();
      } else {
        gen.writeString(value.format(DATE_TIME_FORMATTER));
      }
    }
  }
}

