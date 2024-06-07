package com.luizalabs.fileconverter.infrastructure.mapper;

import com.luizalabs.fileconverter.core.entity.Order;
import com.luizalabs.fileconverter.core.entity.Product;
import com.luizalabs.fileconverter.infrastructure.entrypoint.vo.MainOrderVO;
import com.luizalabs.fileconverter.infrastructure.entrypoint.vo.OrderVO;
import com.luizalabs.fileconverter.infrastructure.entrypoint.vo.ProductVO;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Component

public class MainOrderVOMapper {
    private final ModelMapper mapper;
    Converter<LocalDate, String> localDateToString = new Converter<LocalDate, String>() {
        public String convert(MappingContext<LocalDate, String> context) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return context.getSource() == null ? null : context.getSource().format(formatter);
        }
    };

    public MainOrderVOMapper(ModelMapper mapper) {
        this.mapper = mapper;
        mapperConfig();
    }

    void mapperConfig() {
        mapper.addConverter(localDateToString);
        TypeMap<Product, ProductVO> productToProductVO = mapper.createTypeMap(Product.class, ProductVO.class);
        productToProductVO.addMappings(mapper -> mapper.map(source -> source.getProductId(), ProductVO::setProductId));
        productToProductVO.addMappings(mapper -> mapper.map(source -> source.getProductPrice(), ProductVO::setProductPrice));

        TypeMap<Order, MainOrderVO> orderToMainOrderVo = mapper.createTypeMap(Order.class, MainOrderVO.class);
        orderToMainOrderVo.addMappings(mapper -> mapper.map(source -> source.getUser().getUserId(), MainOrderVO::setUserId));
        orderToMainOrderVo.addMappings(mapper -> mapper.map(source -> source.getUser().getName(), MainOrderVO::setName));
    }

    public MainOrderVO create(Order order) {
        List<ProductVO> listProductVo = order.getProducts().stream().map(product -> mapper.map(product, ProductVO.class)).collect(Collectors.toList());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        OrderVO orderVO = OrderVO.builder().orderId(order.getOrderId()).dataOrder(order.getOrderDate().format(formatter)).total(order.getTotal()).products(listProductVo).build();
        MainOrderVO mainOrderVO = mapper.map(order, MainOrderVO.class);
        mainOrderVO.setOrders(Arrays.asList(orderVO));
        return mainOrderVO;
    }
}
