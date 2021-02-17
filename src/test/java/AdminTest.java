import com.zhuk.controller.AdminController;
import com.zhuk.controller.ErrorHandlerController;
import com.zhuk.service.RoomService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class AdminTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AdminController controller;

    @SpyBean
    private RoomService roomService;

    @Before
    public void setUp() throws Exception {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("");
        viewResolver.setSuffix(".ftlh");

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void init() throws Exception {
//        mockMvc.perform(get("/admin/applications"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("admin_applications"))
//                .andExpect(forwardedUrl("admin_applications.ftlh"));
//        mockMvc.perform(get("/admin/edit/rooms"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("admin_rooms"))
//                .andExpect(forwardedUrl("admin_rooms.ftlh"));
    }
}
