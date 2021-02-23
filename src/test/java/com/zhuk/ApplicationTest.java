package com.zhuk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create-user-before.sql", "/sql/create-room-before.sql", "/sql/create-application-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create-application-after.sql", "/sql/create-room-after.sql", "/sql/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @WithUserDetails(value = "test1@gmail.com")
    @Test
    public void correctAmountOfApplicationsForUserTest() throws Exception {
        mockMvc.perform(get("/applications"))
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(view().name("applications"))
                .andExpect(xpath("//tbody[@id='application-container']/tr").nodeCount(2));
    }

    @WithUserDetails(value = "admin1@gmail.com")
    @Test
    public void correctAmountOfApplicationsForAdminTest() throws Exception {
        mockMvc.perform(get("/admin/applications"))
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(view().name("admin_applications"))
                .andExpect(xpath("//tbody[@id='application-container']/tr").nodeCount(5));
    }
}
