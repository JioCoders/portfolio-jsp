package com.jiocoders.portfolio.repositories;

import com.jiocoders.portfolio.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	List<Expense> findByGroupIdAndDeletedFalseOrderByExpenseDateDesc(Long groupId);

}
