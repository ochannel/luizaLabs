package com.luizalabs.fileconverter.core.usecase.data.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileDataUtil {


    public static BufferedReader getFile(String fileName) throws IOException {
        Resource resource = new ClassPathResource(fileName);
        InputStream inputStream = resource.getInputStream();
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    public static byte[] getBytes(String fileName) throws IOException {
        Resource resource = new ClassPathResource(fileName);
        return resource.getContentAsByteArray();
    }
}
