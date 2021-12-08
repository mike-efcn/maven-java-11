package app.entity.user;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {
  ACTIVE(10, "ACTIVE"),
  INACTIVE(20, "INACTIVE");

  @EnumValue
  private Integer enumValue;

  @JsonValue
  private String jsonValue;
}
