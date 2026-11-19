package com.paytrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paytrack.model.DBUser;

public interface DBUserRepository extends JpaRepository<DBUser, Integer> {
	public DBUser findByUsername(String username);
}