package com.example.online_test.service;

import com.example.online_test.entity.User;
import com.example.online_test.model.Result;
import com.example.online_test.payload.ReqUser;

import java.util.List;
import java.util.Map;

public interface UserService {

//    public List<User> getAllUsers();

    public Result create(ReqUser reqUser);

    public Result editUser(ReqUser reqUser, String id);

    public Result deleteUser(String id);

    public Map getAllUsersByPagealable(int page, int size);

}
