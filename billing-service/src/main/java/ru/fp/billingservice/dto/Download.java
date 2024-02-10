package ru.fp.billingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.io.InputStreamResource;

@AllArgsConstructor
@Getter
public class Download {
    private String fileName;
    private InputStreamResource resource;
}
