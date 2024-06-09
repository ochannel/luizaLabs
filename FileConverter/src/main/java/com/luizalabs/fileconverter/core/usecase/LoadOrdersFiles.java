package com.luizalabs.fileconverter.core.usecase;

import com.luizalabs.fileconverter.core.entity.Order;
import com.luizalabs.fileconverter.core.entity.Product;
import com.luizalabs.fileconverter.core.exception.BadRequestException;
import com.luizalabs.fileconverter.core.gateway.OrderGateway;
import com.luizalabs.fileconverter.core.service.ConvertOrderFileToJson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoadOrdersFiles {
    private final ConvertOrderFileToJson convertOrderFileToJson;
    private final OrderGateway orderGateWay;

    public List<Order> execute(BufferedReader fileBuffer) throws IOException {
        List<Order> returnlist = new ArrayList<>();
        List<Order> listOfOrder = new ArrayList<>();
        convertOrderFileToJson.getListOfOrder(fileBuffer).forEach(order -> listOfOrder.add(order));
        if (listOfOrder.isEmpty()) {
            throw new BadRequestException("Please send a valid file or it is empty.Only .txt files are allowed.");
        }
        listOfOrder.stream().forEach(order -> removeDuplicateReturn(returnlist, saveOrUpdate(order)));
        return returnlist;
    }

    private void removeDuplicateReturn(List<Order> returnlist, Order order) {
        returnlist.remove(order);
        returnlist.add(order);
    }

    private Order saveOrUpdate(Order order) {
        Optional<Order> orderDb = orderGateWay.findById(order.getOrderId());
        if (orderDb.isEmpty()) {
            return orderGateWay.save(order);
        }
        orderDb.get().getProducts().remove(order.getProducts().get(0));
        List<Product> listProductTemp = new ArrayList<>();
        listProductTemp.add(order.getProducts().get(0));
        listProductTemp.addAll(orderDb.get().getProducts());
        order.setProducts(listProductTemp);
        return orderGateWay.save(order);
    }
}
