package com.kea.cosmeticsbackend.service;

import com.kea.cosmeticsbackend.model.User;

import java.util.List;

public interface IUserService extends ICrudService<User,Long>{
    List<User> findByName(String name);
}
