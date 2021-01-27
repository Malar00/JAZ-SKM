package pl.edu.pjatk.simulator.controller.compartment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.edu.pjatk.simulator.controller.CompartmentController;
import pl.edu.pjatk.simulator.service.CompartmentService;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.edu.pjatk.simulator.security.util.SecurityConstants.HEADER_STRING;
import static pl.edu.pjatk.simulator.security.util.SecurityConstants.TOKEN_PREFIX;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompartmentControllerTestModUser {

    @MockBean
    CompartmentService compartmentService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    CompartmentController compartmentController;

    private String TOKEN;

    @BeforeEach
    public void setUp() throws Exception {
        var response = mvc.perform(MockMvcRequestBuilders.get("/login").contentType(MediaType.APPLICATION_JSON)
                .content("""
                           {
                               "username":"mod",
                               "password":"mod"
                           }
                        """)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String content = response.getResponse().getContentAsString();
        String token = content.split(" ")[1];
        this.TOKEN = TOKEN_PREFIX + token;
    }


    @Test
    public void postCompartment() throws Exception {
        String str = "{\n" +
                "    \"compartment_size\": 2,\n" +
                "    \"people\": [\n" +
                "        {\n" +
                "            \"destination\": 4,\n" +
                "            \"lastname_name\": \"TestLast\",\n" +
                "            \"first_name\": \"TestName\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        mvc.perform(MockMvcRequestBuilders
                .post("/compartments")
                .content(str)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).header(HEADER_STRING, TOKEN))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteCompartment() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/compartments/{id}", 3).header(HEADER_STRING, TOKEN))
                .andExpect(status().isForbidden());

    }

    @Test
    public void updateCompartment() throws Exception {
        String str = "{\n" +
                "    \"id\": 3,\n" +
                "    \"compartment_size\": 2,\n" +
                "    \"people\": [\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"destination\": 4,\n" +
                "            \"lastname_name\": \"TestLast\",\n" +
                "            \"first_name\": \"TestName\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        mvc.perform(MockMvcRequestBuilders
                .put("/compartments")
                .content(str)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).header(HEADER_STRING, TOKEN))
                .andExpect(status().isAccepted());
    }

    @Test
    public void getTrain() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/compartments")
                .accept(MediaType.APPLICATION_JSON).header(HEADER_STRING, TOKEN))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
