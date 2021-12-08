package app.service;

import app.mapper.UserMapper;
import app.entity.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

  @Autowired
  UserMapper userMapper;

  public List<User> findUsers(QueryCriteria queryCriteria) {
    return userMapper.selectList(queryCriteria.toQueryWrapper());
  }

  public User createUser(CreateUserDto createUserDto) {
    var user = createUserDto.instantiate();
    userMapper.insert(user);
    return user;
  }

  public User findUser(Integer id) {
    var user = userMapper.selectById(id);
    if (user == null) {
      throw new RuntimeException(String.format("No such record: User(id=%d)", id));
    }

    return user;
  }

  public User updateUser(Integer id, UpdateUserDto updateUserDto) {
    var user = userMapper.selectById(id);
    if (user == null) {
      throw new RuntimeException(String.format("No such record: User(id=%d)", id));
    }
    updateUserDto.applyTo(user);
    userMapper.updateById(user);
    return user;
  }

  public void deleteUser(Integer id) {
    var user = userMapper.selectById(id);
    if (user == null) {
      throw new RuntimeException(String.format("No such record: User(id=%d)", id));
    }

    user.setStatus(StatusEnum.INACTIVE);
    userMapper.updateById(user);
  }
}
