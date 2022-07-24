package com.example.demo.service;


import java.util.List;

import com.example.demo.model.UserList;
import com.example.demo.respository.UserListRepository;
import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

@Service
public class UserListService implements IUserListService {

    @Autowired
    private UserListRepository repository;

    @Override
    public List<UserList> findAll() {

        return (List<UserList>) repository.findAll();
    }
}