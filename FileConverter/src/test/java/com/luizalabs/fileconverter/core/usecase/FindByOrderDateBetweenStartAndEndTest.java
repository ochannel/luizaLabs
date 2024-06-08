package com.luizalabs.fileconverter.core.usecase;

import com.luizalabs.fileconverter.core.entity.Order;
import com.luizalabs.fileconverter.core.gateway.OrderGateWay;
import com.luizalabs.fileconverter.core.usecase.data.util.OrderTestDataUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FindByOrderDateBetweenStartAndEndTest {
    @Mock
    private OrderGateWay orderGateWay;
    @InjectMocks
    private FindByOrderDateBetweenStartAndEnd findByOrderDateBetweenStartAndEnd;

    @DisplayName("Give:starDate and endDate When:executeUseCase Then:List order returned")
    @Test
    void findOrdersSuccessfully() {
        //GIVEN - ARRANGE
        LocalDate startDate = LocalDate.of(2011, 01, 01);
        LocalDate endDate = LocalDate.of(2011, 01, 10);
        List<Order> expected = OrderTestDataUtil.getAllOrder();
        given(orderGateWay.findByOrderDateBetween(startDate, endDate)).willReturn(expected);
        //WHEN  - ACT
        List<Order> returnList = findByOrderDateBetweenStartAndEnd.execute(startDate, endDate);
        //THEN  - ASSERT
        assertThat(returnList.get(0), is(returnList.get(0)));
        assertThat(returnList, is(expected));

    }

    @DisplayName("Give:starDate and endDate When:executeUseCase Then:List order returned empty")
    @Test
    void findOrdersNotFound() {
        //GIVEN - ARRANGE
        LocalDate startDate = LocalDate.of(2001, 01, 01);
        LocalDate endDate = LocalDate.of(2001, 01, 10);
        List<Order> expected = Arrays.asList();
        given(orderGateWay.findByOrderDateBetween(startDate, endDate)).willReturn(expected);
        //WHEN  - ACT
        List<Order> returnList = findByOrderDateBetweenStartAndEnd.execute(startDate, endDate);
        //THEN  - ASSERT
        assertThat(returnList.size(), is(0));
        assertThat(returnList, is(expected));
    }

}