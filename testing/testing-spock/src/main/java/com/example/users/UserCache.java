package com.example.users;

import java.util.Map;

/**
 * @author Ivan Khalopik
 */
public interface UserCache {

  User getUser(String name);

  void invalidate();
}
