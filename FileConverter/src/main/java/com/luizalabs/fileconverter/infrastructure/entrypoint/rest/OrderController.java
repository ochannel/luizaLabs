package com.luizalabs.fileconverter.infrastructure.entrypoint.rest;

import com.luizalabs.fileconverter.core.usecase.FindByOrderDateBetweenStartAndEnd;
import com.luizalabs.fileconverter.core.usecase.GetAllOrder;
import com.luizalabs.fileconverter.core.usecase.GetOrderOfId;
import com.luizalabs.fileconverter.core.usecase.LoadOrdersFiles;
import com.luizalabs.fileconverter.infrastructure.entrypoint.vo.MainOrderVO;
import com.luizalabs.fileconverter.infrastructure.mapper.BufferedReaderMapper;
import com.luizalabs.fileconverter.infrastructure.mapper.MainOrderVOMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Valid
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final MainOrderVOMapper mainOrderVOMapper;
    private final BufferedReaderMapper mapper;
    private final LoadOrdersFiles useCase;
    private final GetOrderOfId getOrderOfId;
    private final FindByOrderDateBetweenStartAndEnd findByOrderDateBetweenStartAndEnd;
    private final GetAllOrder getAllOrder;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public List<MainOrderVO> createOrder(@NotNull(message = "file cannot be null") @RequestParam("file") MultipartFile file) throws IOException {
        return useCase.execute(mapper.create(file)).stream().map(order -> mainOrderVOMapper.create(order)).collect(Collectors.toList());
    }

    @GetMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public MainOrderVO getOrder(@PathVariable Long orderId) {
        return mainOrderVOMapper.create(getOrderOfId.execute(orderId));
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<MainOrderVO> getOrderBetweenDates(
            @NotNull(message = "Date cannot be null")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @NotNull(message = "Date cannot be null")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return findByOrderDateBetweenStartAndEnd.execute(startDate, endDate).stream().map(order -> mainOrderVOMapper.create(order)).collect(Collectors.toList());
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<MainOrderVO> getAllOrder(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return mainOrderVOMapper.create(getAllOrder.execute(page, size));
    }
}
