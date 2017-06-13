package com.siemens.hackathon.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.siemens.hackathon.application.user.registration.entity.ActionItem;


public interface ActionItemRepository extends JpaRepository<ActionItem, Long> {

	//Object save(ActionItem t);

}
