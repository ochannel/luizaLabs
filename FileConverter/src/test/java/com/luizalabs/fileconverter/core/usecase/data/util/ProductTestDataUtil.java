package com.luizalabs.fileconverter.core.usecase.data.util;

import com.luizalabs.fileconverter.core.entity.Product;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class ProductTestDataUtil {

   public static List<Product> getProducts(){
      Product p1 =  Product.builder().productId(1L).productPrice(BigDecimal.valueOf(3.33)).build();
      Product p2 = Product.builder().productId(2L).productPrice(BigDecimal.valueOf(2.22)).build();
      return Arrays.asList(p1,p2);
    }
}
