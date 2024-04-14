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
import ru.fp.coreservice.entity.paydoc.PayDoc;
import ru.fp.coreservice.repository.AccountRepository;
import ru.fp.coreservice.repository.BalancesRepository;
import ru.fp.coreservice.repository.CurrencyRepository;
import ru.fp.coreservice.repository.ParticipantRepository;
import ru.fp.coreservice.repository.PayDocsRepository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc
//@TestMethodOrder(OrderAnnotation.class)
public class UpdateBalancesTests {

//    @Autowired
//    private ObjectMapper mapper;
//    @Autowired
//    private AccountRepository accountRepository;
//    @Autowired
//    private BalancesRepository balancesRepository;
//    @Autowired
//    private PayDocsRepository payDocsRepository;
//    @Autowired
//    private MockMvc mockMvc;

    private static final String BIC_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Test
    @Order(1)
    void test_Create_Participant_OK() {
//        Participant participant = new Participant();
//        participant.setBic(generateBic());
//        Account account = new Account();
//        account.setParticipant(participant);
//        account.setCode("lalala");
//        account = accountRepository.save(account);
//        Balances balances = new Balances();
//        balances.setAccount(account);
//        balances.setCredit(BigDecimal.valueOf(0));
//        balancesRepository.save(balances);
    }

    @Test
    @Order(2)
    void test_UpdateBalance_When_is_20_negative() throws Exception {
//        UpdateBalanceDto dto = UpdateBalanceDto
//                .builder()
//                .accountCode("somecode")
//                .build();
//
//        mockMvc.perform(
//                put("/api/balances")
//                        .content(mapper.writeValueAsString(dto))
//                        .contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(status().isOk());
//
//        Account account = accountRepository.findByCode("somecode").get();
//        Balances balances = balancesRepository.findByAccount(account).get();
//
//        Assertions.assertEquals(balances.getAmount().doubleValue(), 80.0);
//        Assertions.assertEquals(balances.getCredit().doubleValue(), 20.0);
//        Assertions.assertEquals(balances.getDebit().doubleValue(), 0.0);
    }

    @Test
    @Order(3)
    void test_UpdateBalance_When_is_30_positive() throws Exception {
//        UpdateBalanceDto dto = UpdateBalanceDto
//                .builder()
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
    }

    @Test
    @Order(4)
    void test_FindAllPaydocs_Is_Empty() {
//        List<PayDoc> payDocs = payDocsRepository.findAll();
//        Assertions.assertTrue(payDocs.isEmpty());
    }

    private String generateBic() {
        final StringBuilder bic = new StringBuilder();
        final Random random = ThreadLocalRandom.current();

        for (int i = 0; i < 6; i++) {
            bic.append(BIC_ALPHABET.charAt(random.nextInt(BIC_ALPHABET.length())));
        }

        bic.append(random.nextInt(10, 99));

        return bic.toString();
    }

}
