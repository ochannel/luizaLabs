package com.luizalabs.fileconverter.infrastructure.mongodb;

import com.luizalabs.fileconverter.core.entity.Order;
import com.luizalabs.fileconverter.core.usecase.data.util.OrderDocumentTestDataUtil;
import com.luizalabs.fileconverter.core.usecase.data.util.OrderTestDataUtil;
import com.luizalabs.fileconverter.infrastructure.mapper.OrderDocumentMapper;
import com.luizalabs.fileconverter.infrastructure.mapper.OrderEntityMapper;
import com.luizalabs.fileconverter.infrastructure.mongodb.document.OrderDocument;
import com.luizalabs.fileconverter.infrastructure.mongodb.repository.OrderRepository;
import com.luizalabs.fileconverter.infrastructure.mongodb.repository.OrderRepositoryPagination;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderMongoWapperTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderRepositoryPagination orderRepositoryPagination;
    @Spy
    private OrderDocumentMapper orderDocumentMapper;
    @Spy
    private OrderEntityMapper orderEntityMapper;
    @InjectMocks
    private OrderMongoWapper orderMongoWapper;

    @Test
    @DisplayName("Give:order When:orderRepository.save Then: order returned")
    void save() {
        //Given
        Order order = OrderTestDataUtil.getAllOrders().get(0);
        OrderDocument orderDocumentExpected = orderDocumentMapper.create(order);
        given(orderRepository.save(orderDocumentExpected)).willReturn(orderDocumentExpected);
        //when
        Order resultOrder = orderMongoWapper.save(order);
        //Then
        assertThat(order, is(resultOrder));
    }

    @Test
    @DisplayName("Give:order is null When:orderRepository.save Then: IllegalArgumentException returned")
    void saveOrderNull() {
        //WHEN  - ACT - GIVEN - ARRANGE
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> {
                    orderMongoWapper.save(null);
                });
        //THEN  - ASSERT
        assertInstanceOf(IllegalArgumentException.class, exception);
    }

    @Test
    @DisplayName("Give:page and size When:orderMongoWapper.getAllOrders Then: Page<Order>  returned")
    void getAllOrders() {
        //Given
        int page = 1;
        int size = 1;
        Page<OrderDocument> expected = OrderDocumentTestDataUtil.getPageOrder(page, size);
        Page<Order> expectedOrderPage = orderEntityMapper.create(expected);
        given(orderRepositoryPagination.findAll(expected.getPageable())).willReturn(expected);
        //when
        Page<Order> resultPage = orderMongoWapper.getAllOrders(page, size);
        //Then
        assertThat(expectedOrderPage, is(resultPage));
    }

    @Test
    @DisplayName("Give:page and size, db is empty When:orderMongoWapper.getAllOrders Then: empty Page<Order> returned")
    void getAllOrdersEmpty() {
        //Given
        int page = 1;
        int size = 1;
        Page<OrderDocument> expected = OrderDocumentTestDataUtil.getPageOrderEmpty(page, size);
        Page<Order> expectedOrderPage = orderEntityMapper.create(expected);
        given(orderRepositoryPagination.findAll(expected.getPageable())).willReturn(expected);
        //when
        Page<Order> resultPage = orderMongoWapper.getAllOrders(page, size);
        //Then
        assertThat(expectedOrderPage, is(resultPage));
    }

    @Test
    @DisplayName("Give: id When:orderMongoWapper.findById Then: Optional<Order>  returned")
    void findById() {
        //Given
        long id = 10L;
        Optional<OrderDocument> orderDocument = Optional.of(OrderDocumentTestDataUtil.getOrder());
        Order expected = orderEntityMapper.create(orderDocument.get());
        given(orderRepository.findById(id)).willReturn(orderDocument);
        //When
        Optional<Order> resultOrder = orderMongoWapper.findById(id);
        //Then
        assertThat(expected, is(resultOrder.get()));
    }

    @Test
    @DisplayName("Give: id not found When:orderMongoWapper.findById Then: Optional<Order>  returned")
    void findByIdNotFound() {
        //Given
        long id = 15L;
        Optional<OrderDocument> orderDocument = Optional.of(OrderDocumentTestDataUtil.getOrder());
        Order expected = orderEntityMapper.create(orderDocument.get());
        given(orderRepository.findById(id)).willReturn(orderDocument);
        //When
        Optional<Order> resultOrder = orderMongoWapper.findById(id);
        //Then
        assertThat(expected, is(resultOrder.get()));
    }

    @Test
    @DisplayName("Give: startDate and endDate When: orderMongoWapper.findByOrderDateBetween Then: List<Order> returned")
    void findByOrderDateBetween() {
        //Given
        LocalDate startDate = LocalDate.of(2011, 01, 01);
        LocalDate endDate = LocalDate.of(2011, 01, 10);
        List<OrderDocument> orderDocumentlist = List.of(OrderDocumentTestDataUtil.getOrder());
        List<Order> expected = OrderTestDataUtil.getAllOrders();
        given(orderRepository.findByOrderDateBetween(startDate, endDate)).willReturn(orderDocumentlist);
        //When
        List<Order> resultOrderList = orderMongoWapper.findByOrderDateBetween(startDate, endDate);
        //Then
        assertThat(expected, is(resultOrderList));
    }

    @Test
    @DisplayName("Give: date period not found When: orderMongoWapper.findByOrderDateBetween Then: empty List<Order> returned")
    void findByOrderDateBetweenDatePeriodNotFound() {
        //Given
        LocalDate startDate = LocalDate.of(2000, 01, 01);
        LocalDate endDate = LocalDate.of(2000, 01, 10);
        given(orderRepository.findByOrderDateBetween(startDate, endDate)).willReturn(List.of());
        //When
        List<Order> resultOrderList = orderMongoWapper.findByOrderDateBetween(startDate, endDate);
        //Then
        assertThat(List.of(), is(resultOrderList));
    }
}