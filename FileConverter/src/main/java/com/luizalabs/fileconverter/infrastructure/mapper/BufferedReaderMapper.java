package com.luizalabs.fileconverter.infrastructure.mapper;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


@Component
public class BufferedReaderMapper {
    public BufferedReader create(MultipartFile file) throws IOException {
        return new BufferedReader(new InputStreamReader(file.getInputStream()));
    }
}
