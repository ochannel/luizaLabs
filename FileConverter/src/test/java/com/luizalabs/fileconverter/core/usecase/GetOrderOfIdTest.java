package com.luizalabs.fileconverter.core.usecase;

import com.luizalabs.fileconverter.core.entity.Order;
import com.luizalabs.fileconverter.core.exception.NotFoundException;
import com.luizalabs.fileconverter.core.gateway.OrderGateway;
import com.luizalabs.fileconverter.core.usecase.data.util.OrderTestDataUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetOrderOfIdTest {
    @Mock
    private OrderGateway orderGateWay;
    @InjectMocks
    private GetOrderOfId getOrderOfId;

    @DisplayName("Give:id When:executeUseCase Then:Returns an order")
    @Test
    void getOrderOfIdSuccessfully() {
        //GIVEN - ARRANGE
        Long id = 2L;
        Optional<Order> expected = Optional.of(OrderTestDataUtil.getAllOrder().get(0));
        given(orderGateWay.findById(id)).willReturn(expected);
        //WHEN  - ACT
        Order returnOrder = getOrderOfId.execute(id);
        //THEN  - ASSERT
        assertThat(returnOrder, is(expected.get()));
        assertThat(returnOrder.getTotal(), is(expected.get().getTotal()));
    }

    @DisplayName("Give:id When:executeUseCase Then:Returns an NotFoundException")
    @Test
    void getOrderOfIdNotFound() {
        //GIVEN - ARRANGE
        Long id = 100L;
        Optional<Order> expected = Optional.empty();
        given(orderGateWay.findById(id)).willReturn(expected);

        //WHEN  - ACT - GIVEN - ARRANGE
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            getOrderOfId.execute(id);
        });

        //THEN  - ASSERT
        assertInstanceOf(NotFoundException.class, exception);
    }
}