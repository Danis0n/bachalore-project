package ru.fp.receiptservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fp.receiptservice.dto.ReceiptDto;
import ru.fp.receiptservice.service.ReceiptService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/receipt")
public class ReceiptController {

    private final ReceiptService receiptService;

    @PostMapping()
    public void create(@RequestBody ReceiptDto receiptDto) {

    }

}
