package com.siemens.hackathon.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.siemens.hackathon.application.user.registration.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	

}