package com.sparta_express.order_shipment.stock.controller;

import com.sparta_express.order_shipment.common.ResponseDataDto;
import com.sparta_express.order_shipment.common.ResponseMessageDto;
import com.sparta_express.order_shipment.common.ResponseStatus;
import com.sparta_express.order_shipment.stock.dto.CreateStockRequest;
import com.sparta_express.order_shipment.stock.dto.StockResponse;
import com.sparta_express.order_shipment.stock.dto.UpdateStockRequest;
import com.sparta_express.order_shipment.stock.model.Stock;
import com.sparta_express.order_shipment.stock.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    // 재고 생성
    @PostMapping
    public ResponseEntity<ResponseDataDto<StockResponse>> createStock(@Valid @RequestBody CreateStockRequest request) {
        Stock stock = stockService.createStock(request);
        StockResponse stockResponse = StockResponse.from(stock);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.CREATE_SUCCESS, stockResponse));
    }

    // 재고 목록 조회
    @GetMapping
    public ResponseEntity<ResponseDataDto<List<StockResponse>>> getStocks() {
        List<StockResponse> stocks = stockService.getStocks();
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.LIST_SUCCESS, stocks));
    }

    // 재고 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDataDto<StockResponse>> getStockById(@PathVariable UUID id) {
        StockResponse stockResponse = stockService.getStockById(id);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.DETAIL_SUCCESS, stockResponse));
    }

    // 재고 수정
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDataDto<StockResponse>> updateStock(
            @PathVariable UUID id,
            @RequestBody UpdateStockRequest request) {
        StockResponse stockResponse = stockService.updateStock(id, request);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.UPDATE_SUCCESS, stockResponse));
    }

    // 재고 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessageDto> deleteStock(
            @RequestHeader("X-User-Id") String email,
            @PathVariable UUID id) {
        stockService.deleteStock(id, email);
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.DELETE_SUCCESS));
    }

}