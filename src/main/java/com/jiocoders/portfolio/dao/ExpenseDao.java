package com.jiocoders.portfolio.dao;

import com.jiocoders.portfolio.entity.*;
import com.jiocoders.portfolio.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExpenseDao {

	private final GroupRepository groupRepository;

	private final GroupMemberRepository groupMemberRepository;

	private final ExpenseRepository expenseRepository;

	private final ExpenseDistributionRepository expenseDistributionRepository;

	private final SettlementRepository settlementRepository;

	private final AuditLogRepository auditLogRepository;

	// Group Operations
	public Group saveGroup(Group group) {
		return groupRepository.save(group);
	}

	public Optional<Group> findGroupById(Long id) {
		return groupRepository.findById(id);
	}

	public List<Group> findActiveGroups() {
		return groupRepository.findByDeletedFalse();
	}

	// Method to find all groups a user belongs to
	public List<Group> findAllGroupsByUser(Long userId) {
		List<GroupMember> groupMemberships = groupMemberRepository.findByUserId(userId);
		return groupMemberships.stream()
				.map(GroupMember::getGroup)
				.filter(group -> !group.isDeleted()) // Only return non-deleted groups
				.toList();
	}

	// Member Operations
	public GroupMember addMemberToGroup(GroupMember member) {
		return groupMemberRepository.save(member);
	}

	public List<GroupMember> findMembersByGroup(Long groupId) {
		return groupMemberRepository.findByGroupId(groupId);
	}

	// Expense Operations
	public Expense saveExpense(Expense expense) {
		return expenseRepository.save(expense);
	}

	public List<Expense> findExpensesByGroup(Long groupId) {
		return expenseRepository.findByGroupIdAndDeletedFalseOrderByExpenseDateDesc(groupId);
	}

	public List<ExpenseDistribution> findDistributionsByGroup(Long groupId) {
		return expenseDistributionRepository.findByGroupId(groupId);
	}

	// Settlement Operations
	public Settlement saveSettlement(Settlement settlement) {
		return settlementRepository.save(settlement);
	}

	public List<Settlement> findSettlementsByGroup(Long groupId) {
		return settlementRepository.findByGroupId(groupId);
	}

	// Audit Operations
	public void logAudit(AuditLog auditLog) {
		auditLogRepository.save(auditLog);
	}

	public List<AuditLog> findAuditLogsByGroup(Long groupId) {
		return auditLogRepository.findByGroupIdOrderByCreatedAtDesc(groupId);
	}

}