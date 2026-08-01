package com.expensetracker.expensetrackerapi.service;

import com.expensetracker.expensetrackerapi.dto.ExpenseUpdateRequest;
import com.expensetracker.expensetrackerapi.exception.ResourceNotFoundException;
import com.expensetracker.expensetrackerapi.model.Expense;
import com.expensetracker.expensetrackerapi.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }

    // Add Expense
    public Expense addExpense(Expense expense) {

        List<Expense> expenses = repository.getAllExpenses();

        Long nextId = expenses.stream()
                .mapToLong(Expense::getId)
                .max()
                .orElse(0);

        expense.setId(nextId + 1);

        expenses.add(expense);

        repository.saveExpenses(expenses);

        return expense;
    }

    // Get Expense By ID
    public Expense getExpenseById(Long id) {

        return repository.getAllExpenses()
                .stream()
                .filter(expense -> expense.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Expense with ID " + id + " not found."));
    }

    // View All Expenses
    public List<Expense> getAllExpenses() {

        return repository.getAllExpenses();

    }

    // Filter By Category
    public List<Expense> getExpensesByCategory(String category) {

        return repository.getAllExpenses()
                .stream()
                .filter(expense ->
                        expense.getCategory().equalsIgnoreCase(category))
                .toList();

    }

    // Search Expenses
    public List<Expense> searchExpenses(String keyword) {

        return repository.getAllExpenses()
                .stream()
                .filter(expense ->

                        expense.getTitle()
                                .toLowerCase()
                                .contains(keyword.toLowerCase())

                                ||

                                expense.getCategory()
                                        .toLowerCase()
                                        .contains(keyword.toLowerCase())

                )
                .toList();

    }

    // Monthly Summary
    public double getMonthlyExpense(int year, int month) {

        return repository.getAllExpenses()
                .stream()
                .filter(expense ->

                        expense.getDate().getYear() == year
                                &&
                                expense.getDate().getMonthValue() == month

                )
                .mapToDouble(Expense::getAmount)
                .sum();

    }

    // Total Expense
    public double getTotalExpense() {

        return repository.getAllExpenses()
                .stream()
                .mapToDouble(Expense::getAmount)
                .sum();

    }

    // Total Expense By Category
    public double getTotalExpenseByCategory(String category) {

        return repository.getAllExpenses()
                .stream()
                .filter(expense ->
                        expense.getCategory().equalsIgnoreCase(category))
                .mapToDouble(Expense::getAmount)
                .sum();

    }

    // Replace Entire Expense
    public Expense replaceExpense(Long id,
                                  Expense updatedExpense) {

        List<Expense> expenses = repository.getAllExpenses();

        Expense existingExpense = expenses.stream()
                .filter(expense -> expense.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Expense with ID " + id + " not found."));

        existingExpense.setTitle(updatedExpense.getTitle());
        existingExpense.setAmount(updatedExpense.getAmount());
        existingExpense.setCategory(updatedExpense.getCategory());
        existingExpense.setDate(updatedExpense.getDate());

        repository.saveExpenses(expenses);

        return existingExpense;

    }

    // Partial Update
    public Expense updateExpense(Long id,
                                 ExpenseUpdateRequest request) {

        List<Expense> expenses = repository.getAllExpenses();

        Expense expense = expenses.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Expense with ID " + id + " not found."));

        if (request.getTitle() != null) {
            expense.setTitle(request.getTitle());
        }

        if (request.getAmount() != null) {
            expense.setAmount(request.getAmount());
        }

        if (request.getCategory() != null) {
            expense.setCategory(request.getCategory());
        }

        if (request.getDate() != null) {
            expense.setDate(request.getDate());
        }

        repository.saveExpenses(expenses);

        return expense;

    }

    // Delete Expense
    public String deleteExpense(Long id) {

        List<Expense> expenses = repository.getAllExpenses();

        boolean removed =
                expenses.removeIf(expense -> expense.getId().equals(id));

        if (!removed) {

            throw new ResourceNotFoundException(
                    "Expense with ID " + id + " not found.");

        }

        repository.saveExpenses(expenses);

        return "Expense deleted successfully.";

    }

}