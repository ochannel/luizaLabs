package com.luizalabs.fileconverter.infrastructure.entrypoint.rest;

import com.luizalabs.fileconverter.core.usecase.data.util.FileDataUtil;
import com.luizalabs.fileconverter.core.usecase.data.util.OrderDocumentTestDataUtil;
import com.luizalabs.fileconverter.infrastructure.mongodb.document.OrderDocument;
import com.luizalabs.fileconverter.infrastructure.mongodb.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
    }

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2")
            .withExposedPorts(27017)
            .withReuse(true);

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    @DisplayName("Give: a file with two lines of the same order When: call post to /api/order Then: an order is returned")
    void createOrder() throws Exception {
        //Given
        String fileName = "file1.txt";
        MockMultipartFile file = new MockMultipartFile(
                "file",
                fileName,
                "text/plain",
                FileDataUtil.getBytes(fileName));
        //when - Then
        mockMvc.perform(multipart("/api/orders")
                        .file(file))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].user_id", is(70)));
    }

    @Test
    @DisplayName("Give: two lines of the two orders When: call post to /api/order Then: two order are returned")
    void createOrderTwoLinesOfTheTwoOrders() throws Exception {
        //Given
        String fileName = "twoOrders.txt";
        MockMultipartFile file = new MockMultipartFile(
                "file",
                fileName,
                "text/plain",
                FileDataUtil.getBytes(fileName));
        //when - Then
        mockMvc.perform(multipart("/api/orders")
                        .file(file))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].user_id", is(72)));
    }

    @Test
    @DisplayName("Give: empty file When: call post to /api/order Then: 400 returned")
    void createOrderEmptyFile() throws Exception {
        //Given
        String fileName = "empty.txt";
        MockMultipartFile file = new MockMultipartFile(
                "file",
                fileName,
                "text/plain",
                FileDataUtil.getBytes(fileName));
        //when - Then
        mockMvc.perform(multipart("/api/orders")
                        .file(file))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Give: the file was not sent When: call post to /api/order Then: 415 is returned")
    void createOrderFileNotSent() throws Exception {
        //Given
        //When - Then
        mockMvc.perform(post("/api/orders"))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    @DisplayName("Give: orderId When: call get to /api/order Then: order returned")
    void getOrder() throws Exception {
        //Given
        OrderDocument savedOrder = orderRepository.save(OrderDocumentTestDataUtil.getOrder());
        //When - Then
        mockMvc.perform(get("/api/orders/" + savedOrder.getOrderId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orders[0].order_id", is(savedOrder.getOrderId().intValue())));
    }

    @Test
    @DisplayName("Give: nonexistent order When: call get to /api/order Then: 404 is returned")
    void getOrderNonExistentOrder() throws Exception {
        //Given
        String orderId = "2";
        //When - Then
        mockMvc.perform(get("/api/orders/" + orderId))
                .andExpect(status().isNotFound()
                );
    }

    @Test
    @DisplayName("Give: startDate and endDate When: call get to /api/order/search Then: 404 is returned")
    void getOrderBetweenDates() throws Exception {
        //Given
        String startDate = "2021-01-01";
        String endDate = "2021-01-01";
        OrderDocument savedOrder = orderRepository.save(OrderDocumentTestDataUtil.getOrder());

        mockMvc.perform(get("/api/orders/search")
                        .param("startDate", startDate)
                        .param("endDate", endDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].orders[0].order_id", is(1)))
                .andExpect(jsonPath("$[0].orders[0].date", is(savedOrder.getOrderDate().toString())));

    }

    @Test
    @DisplayName("Give: dates don't exist in the DB When: call get to /api/order/search Then: Order empty list returned")
    void getOrderBetweenDatesDatesDontExistInYheDb() throws Exception {
        //Given
        String startDate = "2021-01-01";
        String endDate = "2021-01-01";
        //When - Then
        mockMvc.perform(get("/api/orders/search")
                        .param("startDate", startDate)
                        .param("endDate", endDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Give: page and size When: call get to /api/order Then: Order list returned")
    void getAllOrder() throws Exception {
        //Given
        OrderDocument savedOrder = orderRepository.save(OrderDocumentTestDataUtil.getOrder());
        //When - Then
        mockMvc.perform(get("/api/orders/")
                        .param("page", "0")
                        .param("size", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].orders[0].order_id", is(1)));
    }

    @Test
    @DisplayName("Give: page and size, Db is empty When: call get to /api/order Then: Order empty list returned")
    void getAllOrderDbIsEmpty() throws Exception {
        //Given
        //When - Then
        mockMvc.perform(get("/api/orders/")
                        .param("page", "0")
                        .param("size", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }
}