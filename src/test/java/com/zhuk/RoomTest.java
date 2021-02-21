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

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/create-room-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-room-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RoomTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void init() throws Exception {

        mockMvc.perform(get("/rooms?checkin=2021-09-19&checkout=2021-09-27"))
                .andExpect(status().isOk())
                .andExpect(view().name("rooms"))
                .andExpect(model().attribute("checkin", LocalDate.parse("2021-09-19")))
                .andExpect(model().attribute("checkout", LocalDate.parse("2021-09-27")))
                .andExpect(xpath("//div[@id='card-container']/div").nodeCount(5));
    }

}
