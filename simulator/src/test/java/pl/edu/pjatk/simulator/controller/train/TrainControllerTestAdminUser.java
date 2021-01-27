package pl.edu.pjatk.simulator.controller.train;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.edu.pjatk.simulator.controller.TrainController;
import pl.edu.pjatk.simulator.service.TrainService;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.edu.pjatk.simulator.security.util.SecurityConstants.HEADER_STRING;
import static pl.edu.pjatk.simulator.security.util.SecurityConstants.TOKEN_PREFIX;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TrainControllerTestAdminUser {

    @MockBean
    TrainService trainService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    TrainController trainController;

    private String TOKEN;

    @BeforeEach
    public void setUp() throws Exception {
        var response = mvc.perform(MockMvcRequestBuilders.get("/login").contentType(MediaType.APPLICATION_JSON)
                .content("""
                           {
                               "username":"admin",
                               "password":"admin"
                           }
                        """)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String content = response.getResponse().getContentAsString();
        String token = content.split(" ")[1];
        this.TOKEN = TOKEN_PREFIX + token;
    }

    @Test
    public void postTrain() throws Exception {
        String str = "{\n" +
                "    \"current_station\": 6,\n" +
                "    \"going_back\": false,\n" +
                "    \"waiting\": 1,\n" +
                "    \"compartments\": [\n" +
                "        {\n" +
                "            \"compartment_size\": 2,\n" +
                "            \"people\": [\n" +
                "                {\n" +
                "                    \"destination\": 4,\n" +
                "                    \"lastname_name\": \"TestLast\",\n" +
                "                    \"first_name\": \"TestName\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        mvc.perform(MockMvcRequestBuilders
                .post("/trains")
                .content(str)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).header(HEADER_STRING, TOKEN))
                .andExpect(status().isAccepted());
    }

    @Test
    public void deleteTrain() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/trains/{id}", 1).header(HEADER_STRING, TOKEN))
                .andExpect(status().isAccepted());

    }

    @Test
    public void updateTrain() throws Exception {
        String str = "{\n" +
                "    \"id\": 4,\n" +
                "    \"current_station\": 6,\n" +
                "    \"going_back\": false,\n" +
                "    \"waiting\": 1,\n" +
                "    \"compartments\": [\n" +
                "        {\n" +
                "            \"id\": 4,\n" +
                "            \"compartment_size\": 2,\n" +
                "            \"train_id\": 4,\n" +
                "            \"people\": [\n" +
                "                {\n" +
                "                    \"id\": 4,\n" +
                "                    \"destination\": 4,\n" +
                "                    \"lastname_name\": \"TestLast\",\n" +
                "                    \"first_name\": \"TestName\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        mvc.perform(MockMvcRequestBuilders
                .put("/trains")
                .content(str)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).header(HEADER_STRING, TOKEN))
                .andExpect(status().isAccepted());
    }

    @Test
    public void getTrain() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/trains")
                .accept(MediaType.APPLICATION_JSON).header(HEADER_STRING, TOKEN))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
