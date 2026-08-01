package com.expensetracker.expensetrackerapi.repository;

import com.expensetracker.expensetrackerapi.model.Expense;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExpenseRepository {

    private static final String FILE_NAME = "expenses.json";

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    public List<Expense> getAllExpenses() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {

            return objectMapper.readValue(
                    file,
                    new TypeReference<List<Expense>>() {
                    });

        } catch (IOException e) {

            return new ArrayList<>();

        }

    }

    public void saveExpenses(List<Expense> expenses) {

        try {

            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(FILE_NAME), expenses);

        } catch (IOException e) {

            throw new RuntimeException("Unable to save expenses.");

        }

    }

}