package com.jiocoders.portfolio.controllers;

import com.jiocoders.portfolio.dto.*;
import com.jiocoders.portfolio.models.JioResponse;
import com.jiocoders.portfolio.services.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${jio.api.prefix}/groups")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Expense Management", description = "Endpoints for Groups, Expenses, and Settlements")
public class ExpenseController {

	private final ExpenseService expenseService;

	// --- Groups ---

	@PostMapping
	@Operation(summary = "Create a new group")
	public ResponseEntity<JioResponse<GroupDTO>> createGroup(@RequestBody GroupDTO groupDTO) {
		log.info("Creating group: {}", groupDTO.getName());
		GroupDTO created = expenseService.createGroup(groupDTO);
		return ResponseEntity.ok(JioResponse.success(created, "Group created successfully"));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get group details")
	public ResponseEntity<JioResponse<GroupDTO>> getGroup(@PathVariable Long id) {
		GroupDTO group = expenseService.getGroup(id);
		return ResponseEntity.ok(JioResponse.success(group, "Group fetched successfully"));
	}

	@GetMapping("/user/{userId}")
	@Operation(summary = "Get all groups for a user")
	public ResponseEntity<JioResponse<List<GroupDTO>>> getUserGroups(@PathVariable Long userId) {
		List<GroupDTO> groups = expenseService.getUserGroups(userId);
		return ResponseEntity.ok(JioResponse.success(groups, "Groups fetched successfully"));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a group (soft delete)")
	public ResponseEntity<JioResponse<String>> deleteGroup(@PathVariable Long id) {
		// Implementation would require a method in ExpenseService
		// This is a placeholder for future implementation
		return ResponseEntity.ok(JioResponse.success("Not implemented yet", "Group deletion not implemented"));
	}

	// --- Expenses ---

	@PostMapping("/{groupId}/expenses")
	@Operation(summary = "Add an expense to a group")
	public ResponseEntity<JioResponse<ExpenseDTO>> addExpense(@PathVariable Long groupId,
			@RequestBody ExpenseDTO expenseDTO) {
		log.info("Adding expense '{}' to group {}", expenseDTO.getTitle(), groupId);
		ExpenseDTO created = expenseService.addExpense(groupId, expenseDTO);
		return ResponseEntity.ok(JioResponse.success(created, "Expense added successfully"));
	}

	@GetMapping("/{groupId}/expenses")
	@Operation(summary = "Get all expenses for a group")
	public ResponseEntity<JioResponse<List<ExpenseDTO>>> getExpenses(@PathVariable Long groupId) {
		List<ExpenseDTO> expenses = expenseService.getExpenses(groupId);
		return ResponseEntity.ok(JioResponse.success(expenses, "Expenses fetched successfully"));
	}

	// --- Balances ---

	@GetMapping("/{groupId}/balances")
	@Operation(summary = "Get net balances for all group members")
	public ResponseEntity<JioResponse<List<BalanceDTO>>> getBalances(@PathVariable Long groupId) {
		List<BalanceDTO> balances = expenseService.getBalances(groupId);
		return ResponseEntity.ok(JioResponse.success(balances, "Balances calculated successfully"));
	}

	// --- Settlements ---

	@PostMapping("/{groupId}/settlements")
	@Operation(summary = "Record a settlement payment")
	public ResponseEntity<JioResponse<SettlementDTO>> addSettlement(@PathVariable Long groupId,
			@RequestBody SettlementDTO settlementDTO) {
		log.info("Recording settlement of {} from user {} to {}", settlementDTO.getAmount(),
				settlementDTO.getFromUserId(), settlementDTO.getToUserId());
		SettlementDTO created = expenseService.addSettlement(groupId, settlementDTO);
		return ResponseEntity.ok(JioResponse.success(created, "Settlement recorded successfully"));
	}

	// --- Group Members ---

	@PostMapping("/{groupId}/members")
	@Operation(summary = "Add a member to group")
	public ResponseEntity<JioResponse<String>> addMember(@PathVariable Long groupId, @RequestBody UserDTO userDTO) {
		// Implementation would require a method in ExpenseService
		// This is a placeholder for future implementation
		return ResponseEntity.ok(JioResponse.success("Not implemented yet", "Member addition not implemented"));
	}

	@DeleteMapping("/{groupId}/members/{userId}")
	@Operation(summary = "Remove a member from group")
	public ResponseEntity<JioResponse<String>> removeMember(@PathVariable Long groupId, @PathVariable Long userId) {
		// Implementation would require a method in ExpenseService
		// This is a placeholder for future implementation
		return ResponseEntity.ok(JioResponse.success("Not implemented yet", "Member removal not implemented"));
	}

	@GetMapping("/{groupId}/members")
	@Operation(summary = "Get all members of a group")
	public ResponseEntity<JioResponse<List<UserDTO>>> getMembers(@PathVariable Long groupId) {
		// Implementation would require a method in ExpenseService
		// This is a placeholder for future implementation
		return ResponseEntity.ok(JioResponse.success(List.of(), "Not implemented yet"));
	}

	@DeleteMapping("/{groupId}/expenses/{expenseId}")
	@Operation(summary = "Delete an expense (soft delete)")
	public ResponseEntity<JioResponse<String>> deleteExpense(@PathVariable Long groupId, @PathVariable Long expenseId) {
		// Implementation would require a method in ExpenseService
		// This is a placeholder for future implementation
		return ResponseEntity.ok(JioResponse.success("Not implemented yet", "Expense deletion not implemented"));
	}

}