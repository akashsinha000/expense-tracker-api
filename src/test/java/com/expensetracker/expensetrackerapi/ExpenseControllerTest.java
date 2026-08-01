package com.expensetracker.expensetrackerapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void cleanUp() {

        File file = new File("expenses.json");

        if (file.exists()) {
            file.delete();
        }

    }

    @Test
    void shouldAddExpense() throws Exception {

        String expense = """
                {
                    "title":"Pizza",
                    "amount":450,
                    "category":"Food",
                    "date":"2026-08-02"
                }
                """;

        mockMvc.perform(post("/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expense))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Pizza"))
                .andExpect(jsonPath("$.amount").value(450))
                .andExpect(jsonPath("$.category").value("Food"));

    }

    @Test
    void shouldGetAllExpenses() throws Exception {

        String expense = """
                {
                    "title":"Burger",
                    "amount":300,
                    "category":"Food",
                    "date":"2026-08-02"
                }
                """;

        mockMvc.perform(post("/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expense));

        mockMvc.perform(get("/expenses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Burger"));

    }

    @Test
    void shouldGetExpenseById() throws Exception {

        String expense = """
                {
                    "title":"Netflix",
                    "amount":499,
                    "category":"Entertainment",
                    "date":"2026-08-02"
                }
                """;

        mockMvc.perform(post("/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expense));

        mockMvc.perform(get("/expenses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Netflix"));

    }

    @Test
    void shouldSearchExpense() throws Exception {

        String expense = """
                {
                    "title":"Milk",
                    "amount":60,
                    "category":"Grocery",
                    "date":"2026-08-01"
                }
                """;

        mockMvc.perform(post("/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expense));

        mockMvc.perform(get("/expenses/search")
                        .param("keyword", "milk"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Milk"));

    }

    @Test
    void shouldReturnMonthlySummary() throws Exception {

        String expense = """
                {
                    "title":"Movie",
                    "amount":500,
                    "category":"Entertainment",
                    "date":"2026-08-10"
                }
                """;

        mockMvc.perform(post("/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expense));

        mockMvc.perform(get("/expenses/monthly-summary")
                        .param("year", "2026")
                        .param("month", "8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(500));

    }

    @Test
    void shouldCalculateTotalExpense() throws Exception {

        String expense = """
                {
                    "title":"Laptop",
                    "amount":60000,
                    "category":"Electronics",
                    "date":"2026-08-01"
                }
                """;

        mockMvc.perform(post("/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expense));

        mockMvc.perform(get("/expenses/total"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(60000));

    }

    @Test
    void shouldUpdateExpenseUsingPut() throws Exception {

        String expense = """
                {
                    "title":"Pizza",
                    "amount":450,
                    "category":"Food",
                    "date":"2026-08-02"
                }
                """;

        mockMvc.perform(post("/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expense));

        String updatedExpense = """
                {
                    "title":"Burger",
                    "amount":650,
                    "category":"Fast Food",
                    "date":"2026-08-05"
                }
                """;

        mockMvc.perform(put("/expenses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedExpense))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Burger"))
                .andExpect(jsonPath("$.amount").value(650));

    }

    @Test
    void shouldUpdateExpenseUsingPatch() throws Exception {

        String expense = """
                {
                    "title":"Pizza",
                    "amount":450,
                    "category":"Food",
                    "date":"2026-08-02"
                }
                """;

        mockMvc.perform(post("/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expense));

        String patchRequest = """
                {
                    "amount":700
                }
                """;

        mockMvc.perform(patch("/expenses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(700));

    }

    @Test
    void shouldDeleteExpense() throws Exception {

        String expense = """
                {
                    "title":"Shoes",
                    "amount":2500,
                    "category":"Shopping",
                    "date":"2026-08-02"
                }
                """;

        mockMvc.perform(post("/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expense));

        mockMvc.perform(delete("/expenses/1"))
                .andExpect(status().isOk());

    }

    @Test
    void shouldReturnValidationError() throws Exception {

        String expense = """
                {
                    "title":"",
                    "amount":-100,
                    "category":"",
                    "date":null
                }
                """;

        mockMvc.perform(post("/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expense))
                .andExpect(status().isBadRequest());

    }

}