package ru.fp.coreservice.balance;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.fp.coreservice.dto.IncomingMessageDto;
import ru.fp.coreservice.dto.ParticipantDto;
import ru.fp.coreservice.dto.UpdateBalanceDto;
import ru.fp.coreservice.entity.Account;
import ru.fp.coreservice.repository.AccountRepository;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "events")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EventsBalanceTransferMoneyTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountRepository accountRepository;

//    @Test
//    @Order(1)
//    void testCreateParticipant() throws Exception {
//
//        ParticipantDto participantDto1 = new ParticipantDto();
//        participantDto1.setBic("RXVTDDRY");
//
//        mockMvc.perform(
//                        post("/api/participant")
//                                .content(objectMapper.writeValueAsString(participantDto1))
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isCreated());
//
//        ParticipantDto participantDto2 = new ParticipantDto();
//        participantDto2.setBic("LXTFQK66");
//
//        mockMvc.perform(
//                        post("/api/participant")
//                                .content(objectMapper.writeValueAsString(participantDto2))
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isCreated());
//    }
//
//    @Test
//    @Order(2)
//    void testInitBalanceForParticipants() throws Exception {
//
//        Account account1 = accountRepository.findByName("LXTFQK66").get();
//        Account account2 = accountRepository.findByName("RXVTDDRY").get();
//
//        UpdateBalanceDto updateBalanceDto1 = UpdateBalanceDto.builder()
//                .accountCode(account1.getCode())
//                .bic("LXTFQK66")
//                .currencyCode("RUB")
//                .value(BigDecimal.valueOf(5000))
//                .build();
//
//        UpdateBalanceDto updateBalanceDto2 = UpdateBalanceDto.builder()
//                .accountCode(account2.getCode())
//                .bic("RXVTDDRY")
//                .currencyCode("RUB")
//                .value(BigDecimal.valueOf(10000))
//                .build();
//
//        mockMvc.perform(
//                        put("/api/balances")
//                                .content(objectMapper.writeValueAsString(updateBalanceDto1))
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk());
//
//        mockMvc.perform(
//                        put("/api/balances")
//                                .content(objectMapper.writeValueAsString(updateBalanceDto2))
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @Order(3)
//    void testSuccessTransfer1500RUB() throws Exception {
//
//        String success1500RUBXML = new String(
//                Files.readAllBytes(Paths.get("src/test/resources/success1500RUB.xml")));
//
//        IncomingMessageDto incomingMessageDto = IncomingMessageDto.builder()
//                .sender("LXTFQK66")
//                .type("PACS008")
//                .msg(success1500RUBXML)
//                .build();
//
//        mockMvc.perform(
//                        post("/api/incoming-message")
//                                .content(objectMapper.writeValueAsString(incomingMessageDto))
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isCreated());
//    }
//
//    @Test
//    @Order(4)
//    void testFailTransfer20000RUBNotEnoughMoney() throws Exception {
//
//        String fail20000RUBXML = new String(
//                Files.readAllBytes(Paths.get("src/test/resources/fail20000RUB.xml")));
//
//        IncomingMessageDto incomingMessageDto = IncomingMessageDto.builder()
//                .sender("LXTFQK66")
//                .type("PACS008")
//                .msg(fail20000RUBXML)
//                .build();
//
//        mockMvc.perform(
//                        post("/api/incoming-message")
//                                .content(objectMapper.writeValueAsString(incomingMessageDto))
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isBadRequest());
//    }

}
