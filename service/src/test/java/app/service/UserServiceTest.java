package app.service;

import app.entity.user.CreateUserDto;
import app.entity.user.QueryCriteria;
import app.entity.user.StatusEnum;
import app.entity.user.UpdateUserDto;
import app.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

  @Autowired
  UserMapper userMapper;

  @Autowired
  UserService userService;

  @BeforeEach
  void setUp() {
    userMapper.deleteByMap(new HashMap<>());
    var count = userMapper.selectCount(new QueryWrapper<>());
    assertEquals(0, count);
  }

  @AfterEach
  void tearDown() {
    userMapper.deleteByMap(new HashMap<>());
  }

  @Test
  void findUsers() {
    IntStream.range(0, 3).forEach(i -> {
      var user = userService.createUser(new CreateUserDto(
        RandomStringUtils.randomAlphanumeric(5),
        RandomStringUtils.randomAlphanumeric(5)
      ));
      if (i % 2 == 0) {
        userService.deleteUser(user.getId());
      }
    });

    var users = userService.findUsers(new QueryCriteria());
    assertEquals(3, users.size());

    var criteria = new QueryCriteria();
    criteria.setStatus(StatusEnum.ACTIVE);
    var activeUsers = userService.findUsers(criteria);
    assertEquals(1, activeUsers.size());

    criteria.setStatus(StatusEnum.INACTIVE);
    var inactiveUsers = userService.findUsers(criteria);
    assertEquals(2, inactiveUsers.size());
  }

  @Test
  void createUser() {
    var dto = new CreateUserDto(
      RandomStringUtils.randomAlphanumeric(5),
      RandomStringUtils.randomAlphanumeric(5)
    );
    var created = userService.createUser(dto);

    assertNotNull(created.getId());
    assertEquals(dto.getUsername(), created.getUsername());
    assertEquals(dto.getPassword(), created.getPassword());
    assertEquals(StatusEnum.ACTIVE, created.getStatus());
    assertNotNull(created.getCreatedAt());
    assertNotNull(created.getUpdatedAt());

    var users = userService.findUsers(new QueryCriteria());
    assertEquals(1, users.size());
    assertEquals(created.getId(), users.get(0).getId());
    assertEquals(dto.getUsername(), users.get(0).getUsername());
    assertEquals(dto.getPassword(), users.get(0).getPassword());

    var user = userService.findUser(created.getId());
    assertEquals(created.getId(), user.getId());
    assertEquals(dto.getUsername(), user.getUsername());
    assertEquals(dto.getPassword(), user.getPassword());
  }

  @Test
  void updateUser() {
    var user = userService.createUser(new CreateUserDto(
      RandomStringUtils.randomAlphanumeric(5),
      RandomStringUtils.randomAlphanumeric(5)
    ));

    var dto1 = new UpdateUserDto(
      null,
      RandomStringUtils.randomAlphanumeric(5)
    );
    var updated1 = userService.updateUser(user.getId(), dto1);
    assertEquals(user.getId(), updated1.getId());
    assertEquals(dto1.getPassword(), updated1.getPassword());

    userService.deleteUser(user.getId());
    var updated2 = userService.findUser(user.getId());
    assertEquals(user.getId(), updated2.getId());
    assertEquals(StatusEnum.INACTIVE, updated2.getStatus());
    assertEquals(dto1.getPassword(), updated2.getPassword());

    var dto2 = new UpdateUserDto(StatusEnum.ACTIVE, null);
    var updated3 = userService.updateUser(user.getId(), dto2);
    assertEquals(user.getId(), updated3.getId());
    assertEquals(StatusEnum.ACTIVE, updated3.getStatus());
    assertEquals(dto1.getPassword(), updated3.getPassword());
  }

  @Test
  void notFoundUser() {
    var id = 96;
    assertThrows(RuntimeException.class, () -> {
      userService.findUser(id);
    });
    assertThrows(RuntimeException.class, () -> {
      userService.deleteUser(id);
    });
    assertThrows(RuntimeException.class, () -> {
      userService.updateUser(id, new UpdateUserDto());
    });
  }
}
