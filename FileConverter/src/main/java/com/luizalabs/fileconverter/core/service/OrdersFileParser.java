package com.luizalabs.fileconverter.core.service;

import com.luizalabs.fileconverter.core.entity.Order;
import com.luizalabs.fileconverter.core.entity.Product;
import com.luizalabs.fileconverter.core.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class OrdersFileParser {

    public List<Order> getListOfOrder(BufferedReader fileBuffer) throws IOException {
        List<Order> listOrder = new ArrayList<>();
        String line = null;
        while ((line = fileBuffer.readLine()) != null) {
            getOrder(line).ifPresent(order -> listOrder.add(order));
        }
        return listOrder;
    }

    private Optional<Order> getOrder(String line) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            User user = User.builder()
                    .userId(Long.parseLong(line.substring(0, 10).trim()))
                    .name(line.substring(10, 55).trim())
                    .build();
            Product product = Product.builder()
                    .productId(Long.parseLong(line.substring(65, 75).trim()))
                    .productPrice(new BigDecimal(line.substring(75, 87).trim()))
                    .build();
            Order order = Order.builder()
                    .orderDate(LocalDate.parse(line.substring(87, 95).trim(), formatter))
                    .orderId(Long.parseLong(line.substring(55, 65).trim()))
                    .products(Collections.singletonList(product))
                    .user(user)
                    .build();
            return Optional.of(order);

        } catch (Exception e) {
            log.warn("Line didn't process," + line);
            return Optional.empty();
        }
    }
}
