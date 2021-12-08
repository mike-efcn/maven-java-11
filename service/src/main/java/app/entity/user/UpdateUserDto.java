package app.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
  StatusEnum status = null;

  String password = null;

  public void applyTo(User user) {
    if (status != null) {
      user.setStatus(status);
    }
    if (password != null) {
      user.setPassword(password);
    }
    if (status != null || password != null) {
      user.setUpdatedAt(LocalDateTime.now());
    }
  }
}

