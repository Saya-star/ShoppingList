package com.example.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopping.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
