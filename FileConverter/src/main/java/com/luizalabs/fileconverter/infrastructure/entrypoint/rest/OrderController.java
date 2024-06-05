package com.luizalabs.fileconverter.infrastructure.entrypoint.rest;

import com.luizalabs.fileconverter.core.usecase.LoadOrdersFiles;
import com.luizalabs.fileconverter.infrastructure.mapper.MapperBufferedReader;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@Valid
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final MapperBufferedReader mapper;
    private final LoadOrdersFiles useCase;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrder(@RequestParam("file") MultipartFile file ) throws IOException {
      useCase.execute(mapper.create(file));
        return "teste";
    }
}
