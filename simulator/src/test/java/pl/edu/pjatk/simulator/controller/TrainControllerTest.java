package pl.edu.pjatk.simulator.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.edu.pjatk.simulator.service.TrainService;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TrainController.class)
public class TrainControllerTest {

    @MockBean
    TrainService trainService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    TrainController trainController;


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
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    public void deleteTrain() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/trains/{id}", 1))
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
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    public void getTrain() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/trains")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
