package com.luizalabs.fileconverter.core.usecase.data.util;


import com.luizalabs.fileconverter.infrastructure.mongodb.document.ProductDocument;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductDocumentTestDataUtil {
    public static List<ProductDocument> getProducts() {
        ProductDocument p1 = ProductDocument.builder().productId(1L).productPrice(BigDecimal.valueOf(3.33)).build();
        ProductDocument p2 = ProductDocument.builder().productId(2L).productPrice(BigDecimal.valueOf(2.22)).build();
        List<ProductDocument> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        return list;
    }
}
