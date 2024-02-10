package ru.fp.billingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fp.billingservice.dto.DescriptorDto;
import ru.fp.billingservice.service.CommissionService;

import java.util.List;

@RestController
@RequestMapping("api/descriptor")
@RequiredArgsConstructor
public final class DescriptorController {

    private final CommissionService commissionService;

    @GetMapping
    public ResponseEntity<List<DescriptorDto>> getAll() {
        return ResponseEntity.ok(commissionService.getDescriptors());
    }
}
