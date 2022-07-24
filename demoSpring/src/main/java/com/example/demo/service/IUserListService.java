package com.example.demo.service;


import com.example.demo.model.UserList;
import java.util.List;

public interface IUserListService {

    List<UserList> findAll();
}