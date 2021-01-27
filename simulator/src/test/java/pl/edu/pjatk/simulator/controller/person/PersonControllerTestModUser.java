package pl.edu.pjatk.simulator.controller.person;

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
import pl.edu.pjatk.simulator.controller.PersonController;
import pl.edu.pjatk.simulator.service.PersonService;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.edu.pjatk.simulator.security.util.SecurityConstants.HEADER_STRING;
import static pl.edu.pjatk.simulator.security.util.SecurityConstants.TOKEN_PREFIX;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonControllerTestModUser {

    @MockBean
    PersonService personService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    PersonController personController;
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
                .accept(MediaType.APPLICATION_JSON).header(HEADER_STRING, TOKEN))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deletePerson() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/people/{id}", 3).header(HEADER_STRING, TOKEN))
                .andExpect(status().isForbidden());

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
                .accept(MediaType.APPLICATION_JSON).header(HEADER_STRING, TOKEN))
                .andExpect(status().isAccepted());
    }

    @Test
    public void getPeople() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/people")
                .accept(MediaType.APPLICATION_JSON).header(HEADER_STRING, TOKEN))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
