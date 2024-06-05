package com.luizalabs.fileconverter.core.service;

import com.luizalabs.fileconverter.core.entity.Order;
import com.luizalabs.fileconverter.core.entity.Product;
import com.luizalabs.fileconverter.core.entity.User;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ConvertOrderFileToJson {

   public Optional<List<Order>> getListOfOrder(BufferedReader fileBuffer) throws IOException {
       List<Order> listOrder = new ArrayList<>();
       String line = null;
       while ( (line = fileBuffer.readLine()) != null ) {
        getOrder(line).ifPresent(order-> listOrder.add(order));
       }
       if(listOrder.isEmpty()){
           return Optional.empty();
       }
    return Optional.of(listOrder);
   }

    private Optional<Order> getOrder(String line){


        //String idUser = line.substring(0, 10).trim();
        //String name = line.substring(10, 55).trim();
        //String idOrder = line.substring(55, 65).trim();
        //String idProduct= line.substring(65, 75).trim();
        //String productPriceStr = line.substring(75, 87).trim();
        //String dataCompraStr = line.substring(87, 95).trim();
       try {
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
           User user = User.builder()
                   .idUser(Integer.parseInt(line.substring(0, 10).trim()))
                   .name(line.substring(10, 55).trim())
                   .build();
           Product product = Product.builder()
                   .idProduct(Integer.parseInt(line.substring(65, 75).trim()))
                   .productPrice(new BigDecimal(line.substring(75, 87).trim()))
                   .build();
           Order order = Order.builder()
                   .dataOrder(LocalDate.parse(line.substring(87, 95).trim(), formatter))
                   .idOrder(Integer.parseInt(line.substring(55, 65).trim()))
                   .products(Arrays.asList(product))
                   .user(user)
                   .build();
           return  Optional.of(order);

       }catch (Exception e ){
          return Optional.empty();
       }
    }


}
