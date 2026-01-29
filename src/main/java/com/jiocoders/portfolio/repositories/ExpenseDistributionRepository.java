package com.jiocoders.portfolio.repositories;

import com.jiocoders.portfolio.entity.ExpenseDistribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseDistributionRepository extends JpaRepository<ExpenseDistribution, Long> {

	List<ExpenseDistribution> findByExpenseId(Long expenseId);

	@Query("SELECT ed FROM ExpenseDistribution ed JOIN ed.expense e WHERE e.group.id = :groupId AND e.deleted = false")
	List<ExpenseDistribution> findByGroupId(@Param("groupId") Long groupId);

}
