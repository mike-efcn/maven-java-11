package app.entity.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;

@Data
public class QueryCriteria {
  StatusEnum status = null;

  public QueryWrapper<User> toQueryWrapper() {
    var query = new QueryWrapper<User>();
    if (status != null) {
      query.eq("status", status);
    }
    return query;
  }
}
