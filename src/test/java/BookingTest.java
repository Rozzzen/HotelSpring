import com.zhuk.controller.BookingController;
import com.zhuk.domain.room.RoomType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class BookingTest {

    private MockMvc bookingMvc;

    @InjectMocks
    private BookingController bookingController;

    @Before
    public void setUp() throws Exception {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("");
        viewResolver.setSuffix(".ftlh");

        bookingMvc = MockMvcBuilders.standaloneSetup(bookingController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void init() throws Exception {
        bookingMvc.perform(get("/booking"))
                .andExpect(status().isOk())
                .andExpect(view().name("booking"))
                .andExpect(forwardedUrl("booking.ftlh"))
                .andExpect(model().attribute("roomtypes", RoomType.values()));

    }

}
