package com.jiocoders.portfolio.repositories;

import com.jiocoders.portfolio.models.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

	List<AuditLog> findByGroupIdOrderByCreatedAtDesc(Long groupId);

}
