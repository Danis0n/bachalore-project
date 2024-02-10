package ru.fp.coreservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.fp.coreservice.dto.PayDocsDto;
import ru.fp.coreservice.service.PayDocsService;

import java.util.List;

@RestController
@RequestMapping("api/paydocs")
@RequiredArgsConstructor
public final class PayDocsController {

    private final PayDocsService payDocsService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PayDocsDto>> findAll() {
        return ResponseEntity.ok(payDocsService.getAllDocuments());
    }
}
