package com.cyxlab.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cyxlab.model.DBUser;

public interface DBUserRepository extends JpaRepository<DBUser, Integer> {
	public DBUser findByUsername(String username);
}