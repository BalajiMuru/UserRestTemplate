package com.springboot.user.service.repostories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.user.service.entities.User;

public interface UserRepository extends JpaRepository<User,String>{

}
