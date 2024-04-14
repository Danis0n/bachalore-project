package ru.fp.coreservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fp.coreservice.dto.AccountBalanceDto;
import ru.fp.coreservice.dto.UpdateBalanceDto;
import ru.fp.coreservice.service.BalancesService;

import java.util.List;

@RestController
@RequestMapping("api/balances")
@RequiredArgsConstructor
public class BalanceController {

    private final BalancesService balancesService;

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public void updateBalance(@RequestBody UpdateBalanceDto dto) {
        balancesService.updateBalance(dto);
    }

    @GetMapping("/by-code/{code}")
    public ResponseEntity<String> getAccountBalance(@PathVariable String code) {
        return ResponseEntity.ok(balancesService.findCurrencyByAccountCode(code));
    }

    @GetMapping
    public ResponseEntity<List<AccountBalanceDto>> getAccountBalances() {
        return ResponseEntity.ok(balancesService.getAccountBalances());
    }

    @GetMapping("/by-bic/{bic}")
    public ResponseEntity<List<AccountBalanceDto>> getAccountBalancesByBic(@PathVariable String bic) {
        return ResponseEntity.ok(balancesService.getAccountBalancesByBic(bic));
    }

}
