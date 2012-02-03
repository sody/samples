package com.example.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 */
public class UserCacheImpl implements UserCache {
  private final UserDAO dao;
  private final Map<String, User> cache = new HashMap<String, User>();

  private boolean initialized = false;

  public UserCacheImpl(final UserDAO dao) {
    this.dao = dao;
  }

  public User getUser(final String name) {
    if (!initialized) {
      cache.clear();
      final List<User> users = dao.findAll();
      for (User user : users) {
        cache.put(user.getName(), user);
      }
      initialized = true;
    }
    return cache.get(name);
  }

  public void invalidate() {
    initialized = false;
  }
}
