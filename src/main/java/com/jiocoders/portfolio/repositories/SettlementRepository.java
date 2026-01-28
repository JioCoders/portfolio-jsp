package com.jiocoders.portfolio.repositories;

import com.jiocoders.portfolio.models.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long> {

	List<Settlement> findByGroupId(Long groupId);

}
