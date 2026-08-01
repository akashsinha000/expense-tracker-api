package com.expensetracker.expensetrackerapi.controller;

import com.expensetracker.expensetrackerapi.dto.ExpenseUpdateRequest;
import com.expensetracker.expensetrackerapi.dto.MonthlySummaryResponse;
import com.expensetracker.expensetrackerapi.dto.TotalExpenseResponse;
import com.expensetracker.expensetrackerapi.model.Expense;
import com.expensetracker.expensetrackerapi.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
@Tag(name = "Expense API", description = "Operations for managing expenses")
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    // Add Expense
    @Operation(summary = "Add a new expense")
    @PostMapping
    public Expense addExpense(@Valid @RequestBody Expense expense) {

        return service.addExpense(expense);

    }

    // Get Expense By ID
    @Operation(summary = "Get expense by ID")
    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable Long id) {

        return service.getExpenseById(id);

    }

    // View All or Filter by Category
    @Operation(summary = "View all expenses or filter by category")
    @GetMapping
    public List<Expense> getExpenses(
            @RequestParam(required = false) String category) {

        if (category != null && !category.isBlank()) {

            return service.getExpensesByCategory(category);

        }

        return service.getAllExpenses();

    }

    // Search Expense
    @Operation(summary = "Search expenses by title or category")
    @GetMapping("/search")
    public List<Expense> searchExpenses(
            @RequestParam String keyword) {

        return service.searchExpenses(keyword);

    }

    // Monthly Summary
    @Operation(summary = "Get monthly expense summary")
    @GetMapping("/monthly-summary")
    public MonthlySummaryResponse getMonthlySummary(
            @RequestParam int year,
            @RequestParam int month) {

        double total = service.getMonthlyExpense(year, month);

        return new MonthlySummaryResponse(year, month, total);

    }

    // Total Expense
    @Operation(summary = "Calculate total expenses")
    @GetMapping("/total")
    public TotalExpenseResponse getTotalExpense(
            @RequestParam(required = false) String category) {

        if (category != null && !category.isBlank()) {

            return new TotalExpenseResponse(
                    service.getTotalExpenseByCategory(category));

        }

        return new TotalExpenseResponse(
                service.getTotalExpense());

    }

    // Replace Entire Expense
    @Operation(summary = "Replace an entire expense")
    @PutMapping("/{id}")
    public Expense replaceExpense(
            @PathVariable Long id,
            @Valid @RequestBody Expense expense) {

        return service.replaceExpense(id, expense);

    }

    // Update Selected Fields
    @Operation(summary = "Partially update an expense")
    @PatchMapping("/{id}")
    public Expense updateExpense(
            @PathVariable Long id,
            @RequestBody ExpenseUpdateRequest request) {

        return service.updateExpense(id, request);

    }

    // Delete Expense
    @Operation(summary = "Delete an expense")
    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable Long id) {

        return service.deleteExpense(id);

    }

}