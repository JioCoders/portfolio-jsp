package com.jiocoders.portfolio.repositories;

import com.jiocoders.portfolio.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

	List<Group> findByCreatorIdAndDeletedFalse(Long creatorId);

	List<Group> findByDeletedFalse();

}
