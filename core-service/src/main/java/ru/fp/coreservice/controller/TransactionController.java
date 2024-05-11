package ru.fp.coreservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.fp.coreservice.dto.TransactionDto;
import ru.fp.coreservice.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("by-bic/{bic}")
    public ResponseEntity<List<TransactionDto>> findLatestTransactions(
            @PathVariable("bic") String bic,
            @RequestParam(
                    value = "limit",
                    defaultValue = "10",
                    required = false
            ) Integer limit
    ) {
        return ResponseEntity.ok(transactionService.findLatestTransactions(bic, limit));
    }

}
