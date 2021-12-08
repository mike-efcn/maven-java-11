package app.controller.v1;

import app.entity.user.*;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1/users")
public class UserController {

  @Autowired
  UserService userService;

  @GetMapping()
  public List<User> findUsers(QueryCriteria queryCriteria) {
    return userService.findUsers(queryCriteria);
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public User createUser(@RequestBody CreateUserDto params) {
    return userService.createUser(params);
  }

  @GetMapping("{id}")
  public User findUser(@PathVariable("id") Integer id) {
    return userService.findUser(id);
  }

  @PutMapping("{id}")
  @PatchMapping("{id}")
  public User updateUser(
    @PathVariable("id") Integer id,
    @RequestBody UpdateUserDto params
  ) {
    return userService.updateUser(id, params);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteUser(@PathVariable("id") Integer id) {
    userService.deleteUser((id));
  }

  @PostMapping("{id}/login")
  public Map<Object, Object> login(@PathVariable("id") Integer id) {
    userService.findUser(id);
    return Map.of();
  }
}
