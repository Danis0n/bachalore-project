package ru.fp.coreservice.participant;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.fp.coreservice.dto.UpdateBalanceDto;
import ru.fp.coreservice.entity.Account;
import ru.fp.coreservice.entity.Balances;
import ru.fp.coreservice.entity.Participant;
import ru.fp.coreservice.repository.AccountRepository;
import ru.fp.coreservice.repository.BalancesRepository;
import ru.fp.coreservice.repository.CurrencyRepository;
import ru.fp.coreservice.repository.ParticipantRepository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class UpdateBalancesTests {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BalancesRepository balancesRepository;
    @Autowired
    private MockMvc mockMvc;

//    @Test
//    @Order(1)
//    void testLoadParticipant() {
//        Participant participant = new Participant();
//        participant.setBic("111111111");
//        participant.setIsActive(true);
//        participant.setDateCreated(new Timestamp(new Date().getTime()));
//        participant = participantRepository.save(participant);
//
//        Account account = new Account();
//        account.setParticipant(participant);
//        account.setCode("lalala");
//        account.setCurrency(currencyRepository.findByCode("RUB").get());
//        account.setName("Name");
//        account.setIsActive(true);
//        account.setAccType("cash");
//        account.setOpenDate(new Timestamp(new Date().getTime()));
//
//        account = accountRepository.save(account);
//
//        Balances balances = new Balances();
//        balances.setAccount(account);
//        balances.setCredit(BigDecimal.valueOf(0));
//        balances.setDebit(BigDecimal.valueOf(0));
//        balances.setAmount(BigDecimal.valueOf(100));
//        balances.setDocDebits(0L);
//        balances.setDocCredits(0L);
//
//        balancesRepository.save(balances);
//    }
//
//    @Test
//    @Order(2)
//    void testUpdateBalance_When_is_20_negative() throws Exception {
//        UpdateBalanceDto dto = UpdateBalanceDto
//                .builder()
//                .bic("111111111")
//                .currencyCode("RUB")
//                .value(new BigDecimal(-20))
//                .accountCode("lalala")
//                .build();
//
//        mockMvc.perform(
//                put("/api/balances")
//                        .content(mapper.writeValueAsString(dto))
//                        .contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(status().isOk());
//
//        Account account = accountRepository.findByCode("lalala").get();
//        Balances balances = balancesRepository.findByAccount(account).get();
//
//        Assertions.assertEquals(balances.getAmount().doubleValue(), 80.0);
//        Assertions.assertEquals(balances.getCredit().doubleValue(), 20.0);
//        Assertions.assertEquals(balances.getDebit().doubleValue(), 0.0);
//    }
//
//    @Test
//    @Order(3)
//    void testUpdateBalance_When_is_30_positive() throws Exception {
//        UpdateBalanceDto dto = UpdateBalanceDto
//                .builder()
//                .bic("111111111")
//                .currencyCode("RUB")
//                .value(new BigDecimal(30))
//                .accountCode("lalala")
//                .build();
//
//        mockMvc.perform(
//                put("/api/balances")
//                        .content(mapper.writeValueAsString(dto))
//                        .contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(status().isOk());
//
//        Account account = accountRepository.findByCode("lalala").get();
//        Balances balances = balancesRepository.findByAccount(account).get();
//
//        Assertions.assertEquals(balances.getAmount().doubleValue(), 110.0);
//        Assertions.assertEquals(balances.getCredit().doubleValue(), 20.0);
//        Assertions.assertEquals(balances.getDebit().doubleValue(), 30.0);
//    }

}
