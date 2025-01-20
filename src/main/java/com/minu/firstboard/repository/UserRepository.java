package com.minu.firstboard.repository;

import com.minu.firstboard.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

}
