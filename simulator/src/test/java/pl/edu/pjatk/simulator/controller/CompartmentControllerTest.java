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
import pl.edu.pjatk.simulator.service.CompartmentService;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CompartmentController.class)
public class CompartmentControllerTest {

    @MockBean
    CompartmentService compartmentService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    CompartmentController compartmentController;


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
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    public void deleteCompartment() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/compartments/{id}", 3))
                .andExpect(status().isAccepted());

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
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    public void getTrain() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/compartments")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
