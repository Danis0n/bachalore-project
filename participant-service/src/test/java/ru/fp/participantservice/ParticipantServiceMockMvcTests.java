package ru.fp.participantservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.fp.participantservice.client.RestClient;
import ru.fp.participantservice.repository.ParticipantRepository;
import ru.fp.participantservice.repository.TypeRepository;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class ParticipantServiceMockMvcTests {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TypeRepository repository;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestClient participantClient;

//    @Test
//    @WithAnonymousUser
//    public void givenTypeWhenAddThenStatus201AndTypeReturned() throws Exception {
//        Type type = new Type();
//        type.setName("TestType1");
//        type.setDescription("TestDescription1");
//
//        mockMvc.perform(
//                post("/api/types")
//                        .content(objectMapper.writeValueAsString(type))
//                        .contentType(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(status().isCreated());
//
//        repository.deleteAll();
//    }
//
//    @Test
//    void givenParticipantFromAllServicesWhenAddThenTopUpBalanceAndCheck() throws Exception {
//        Mockito.doNothing()
//                .when(participantClient).sendParticipantRequest(any());
//
//        val participantDto = ParticipantDto.builder()
//                .name("Ivan")
//                .bic("044525980")
//                .typeName("pacs008")
//                .password("1234")
//                .login("login")
//                .build();
//
//        mockMvc.perform(
//                        post("/api/authentication/register")
//                                .content(objectMapper.writeValueAsString(participantDto))
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isCreated());
//
//        participantRepository.deleteAll();
//    }
}

