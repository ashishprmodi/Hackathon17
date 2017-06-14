package com.siemens.hackathon.application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.siemens.hackathon.application.user.registration.entity.ActionItem;


public interface ActionItemRepository extends JpaRepository<ActionItem, Long> {

	List<ActionItem> findAllByType(String taskType);

}
