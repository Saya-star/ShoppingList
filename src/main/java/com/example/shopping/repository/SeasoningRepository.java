package com.example.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping.entity.Seasoning;

@Repository
public interface SeasoningRepository extends JpaRepository<Seasoning, Long>{

}
