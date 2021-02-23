package com.zhuk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RegisterTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void existingEmailInputRedirectTest() throws Exception {
        mockMvc.perform(post("/register")
                .param("name", "test")
                .param("surname", "test")
                .param("email", "test1@gmail.com")
                .param("gender", "Male")
                .param("phone", "phone")
                .param("password", "password")
                .param("password2", "password")
                .param("day", "31")
                .param("month", "7")
                .param("year", "2001")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("alert", "User with this email already exists"))
                .andExpect(view().name("register"));
    }

    @Test
    public void contextLoads() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }
}
