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
import pl.edu.pjatk.simulator.service.PersonService;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @MockBean
    PersonService personService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    PersonController personController;


    @Test
    public void postPerson() throws Exception {
        String str = "{\n" +
                "    \"first_name\": \"Post\",\n" +
                "    \"last_name\": \"Test\",\n" +
                "    \"destination\": 5,\n" +
                "    \"compartment\": {\n" +
                "        \"id\": 2\n" +
                "    }\n" +
                "}}";
        mvc.perform(MockMvcRequestBuilders
                .post("/people")
                .content(str)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    public void deletePerson() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/people/{id}", 3))
                .andExpect(status().isAccepted());

    }

    @Test
    public void updatePerson() throws Exception {
        String str = "{\n" +
                "    \"id\": 2,\n" +
                "    \"first_name\": \"Post\",\n" +
                "    \"last_name\": \"Test\",\n" +
                "    \"destination\": 5,\n" +
                "    \"compartment\": {\n" +
                "        \"id\": 2\n" +
                "    }\n" +
                "}";
        mvc.perform(MockMvcRequestBuilders
                .put("/people")
                .content(str)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    public void getPeople() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/people")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
