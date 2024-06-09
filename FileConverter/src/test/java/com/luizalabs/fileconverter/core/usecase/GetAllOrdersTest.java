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
import org.springframework.data.domain.Page;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class GetAllOrdersTest {
    @Mock
    private OrderGateWay orderGateWay;
    @InjectMocks
    private GetAllOrders getAllOrders;

    @DisplayName("Give:page and size When:executeUseCase Then:Returns an Page<order> ")
    @Test
    void getAllOrderSuccessfully() {
        //GIVEN - ARRANGE
        int page = 1;
        int size = 1;
        Page<Order> expected = OrderTestDataUtil.getPageOrder(page, size);
        given(orderGateWay.getAllOrder(page, size)).willReturn(expected);
        //WHEN  - ACT
        Page<Order> retunPage = getAllOrders.execute(page, size);
        //THEN  - ASSERT
        assertThat(retunPage, is(expected));
        assertThat(retunPage.getTotalElements(), is(2L));
    }

    @DisplayName("Give:page and size When:executeUseCase Then:Returns an empty Page<Order> ")
    @Test
    void getAllOrderEmpty() {
        //GIVEN - ARRANGE
        int page = 1;
        int size = 1;
        Page<Order> expected = OrderTestDataUtil.getPageOrderEmpty(page, size);
        given(orderGateWay.getAllOrder(page, size)).willReturn(expected);
        //WHEN  - ACT
        Page<Order> retunPage = getAllOrders.execute(page, size);
        //THEN  - ASSERT
        assertThat(retunPage, is(expected));
        assertThat(retunPage.getTotalElements(), is(0L));
    }
}