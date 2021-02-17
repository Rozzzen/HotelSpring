import com.zhuk.controller.RoomController;
import com.zhuk.domain.room.Room;
import com.zhuk.service.RoomService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class RoomTest {

    private MockMvc mockMvc;

    @InjectMocks
    private RoomController controller;

    @Mock
    private RoomService roomService;

    @Before
    public void setUp() throws Exception {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("");
        viewResolver.setSuffix(".ftlh");

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver)
                .build();

        Room room1 = new Room();
        room1.setId((long) 101);
        Room room2 = new Room();
        room1.setId((long) 102);
        Room room3 = new Room();
        room1.setId((long) 103);

        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "id"));

        when(roomService.findAll(pageRequest)).thenReturn(new PageImpl<>(Arrays.asList(room1, room2, room3)));
    }

    @Test
    public void init() throws Exception {

        mockMvc.perform(get("/rooms?checkin=2021-02-19&checkout=2021-02-27"))
                .andExpect(status().isOk())
                .andExpect(view().name("rooms"))
                .andExpect(forwardedUrl("rooms.ftlh"))
                .andExpect(model().attribute("checkin", LocalDate.parse("2021-02-19")))
                .andExpect(model().attribute("checkout", LocalDate.parse("2021-02-27")));
    }

}
