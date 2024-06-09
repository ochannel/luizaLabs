package com.luizalabs.fileconverter.core.service;

import com.luizalabs.fileconverter.core.entity.Order;
import com.luizalabs.fileconverter.core.usecase.data.util.FileDataUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class ConvertOrderFileToJsonTest {
    @InjectMocks
    ConvertOrderFileToJson convertOrderFileToJson;

    @DisplayName("Give: bufferedReader When:execute getListOfOrder Then:Returns an order list")
    @Test
    void getListOfOrderSuccessfully() throws IOException {
        //GIVEN - ARRANGE
        //WHEN  - ACT
        List<Order> returnOrderList = convertOrderFileToJson.getListOfOrder(FileDataUtil.getFile("file1.txt"));
        //THEN  - ASSERT
        assertThat(returnOrderList.size(), is(2));
        assertThat(returnOrderList.get(0).getOrderId(), is(753L));
    }

    @DisplayName("Give: bufferedReader  When:execute getListOfOrder Then:Returns an order empty list")
    @Test
    void getListOfOrderEmptyFile() throws IOException {
        //GIVEN - ARRANGE
        //WHEN  - ACT
        List<Order> returnOrderList = convertOrderFileToJson.getListOfOrder(FileDataUtil.getFile("empty.txt"));
        //THEN  - ASSERT
        assertThat(returnOrderList.size(), is(0));
    }
}