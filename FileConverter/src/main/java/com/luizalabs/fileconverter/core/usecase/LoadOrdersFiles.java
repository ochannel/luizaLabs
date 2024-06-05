package com.luizalabs.fileconverter.core.usecase;

import com.luizalabs.fileconverter.core.service.ConvertOrderFileToJson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class LoadOrdersFiles {

   private final ConvertOrderFileToJson convertOrderFileToJson;

    public void execute(BufferedReader fileBuffer) throws IOException {
        convertOrderFileToJson.getListOfOrder(fileBuffer);

    }
}
