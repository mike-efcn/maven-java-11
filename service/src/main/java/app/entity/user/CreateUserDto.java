package app.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreateUserDto {
  String username = null;

  String password = null;

  public User instantiate() {
    return new User(
      null,
      StatusEnum.ACTIVE,
      username,
      password,
      LocalDateTime.now(),
      LocalDateTime.now()
    );
  }
}
