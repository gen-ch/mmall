package com.gen.mmall.service;

import com.gen.mmall.common.ServerResponse;
import com.gen.mmall.pojo.User;

public interface IUserService {

    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str,String type);

}
