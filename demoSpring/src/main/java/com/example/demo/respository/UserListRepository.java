package com.example.demo.respository;

 import com.example.demo.model.UserList;
 import org.springframework.data.repository.CrudRepository;
 import org.springframework.stereotype.Repository;

@Repository
public interface UserListRepository extends CrudRepository<UserList, Long> {

}