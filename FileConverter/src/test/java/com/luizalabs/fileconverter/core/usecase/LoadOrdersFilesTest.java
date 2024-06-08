package com.luizalabs.fileconverter.core.usecase;

import com.luizalabs.fileconverter.core.entity.Order;
import com.luizalabs.fileconverter.core.exception.BadRequestException;
import com.luizalabs.fileconverter.core.gateway.OrderGateWay;
import com.luizalabs.fileconverter.core.service.ConvertOrderFileToJson;
import com.luizalabs.fileconverter.core.usecase.data.util.BufferedReaderDataUtil;
import com.luizalabs.fileconverter.core.usecase.data.util.OrderTestDataUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LoadOrdersFilesTest {
    @Mock
    private ConvertOrderFileToJson convertOrderFileToJson;
    @Mock
    private OrderGateWay orderGateWay;
    @InjectMocks
    private LoadOrdersFiles loadOrdersFiles;

    @DisplayName("Give:bufferedReader When:executeUseCase Then:Returns an order list")
    @Test
    void loadOrdersFilesSuccessfully() throws IOException {
        //GIVEN - ARRANGE
        BufferedReader bufferedReader = BufferedReaderDataUtil.getFile("file1.txt");
        Order expected1 = OrderTestDataUtil.getOrderForLine().get(0);
        Order expected2 = OrderTestDataUtil.getOrderForLine().get(1);
        given(orderGateWay.findById(anyLong())).willReturn(Optional.empty()).willReturn(Optional.empty());
        given(convertOrderFileToJson.getListOfOrder(bufferedReader)).willReturn(OrderTestDataUtil.getOrderForLine());
        given(orderGateWay.save(any(Order.class))).willReturn(expected1).willReturn(expected2);
        //WHEN  - ACT
        List<Order> returnListOrder = loadOrdersFiles.execute(bufferedReader);
        //THEN  - ASSERT
        assertThat(returnListOrder.get(0), is(expected1));
        assertThat(returnListOrder.get(1), is(expected2));
    }

    @DisplayName("Give:bufferedReader When:executeUseCase Then:Returns an BadRequestException")
    @Test
    void loadOrdersFilesInvalidFile() throws IOException {
        //GIVEN - ARRANGE
        BufferedReader bufferedReader = BufferedReaderDataUtil.getFile("file1.txt");
        given(convertOrderFileToJson.getListOfOrder(bufferedReader)).willReturn(List.of());
        //WHEN  - ACT
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            loadOrdersFiles.execute(bufferedReader);
        });

        //THEN  - ASSERT
        assertInstanceOf(BadRequestException.class, exception);
    }

}