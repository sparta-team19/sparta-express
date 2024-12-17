package com.sparta_express.company_product.stock.service;

import com.sparta_express.company_product.common.CustomException;
import com.sparta_express.company_product.common.ErrorType;
import com.sparta_express.company_product.stock.dto.CreateStockRequest;
import com.sparta_express.company_product.stock.dto.StockResponse;
import com.sparta_express.company_product.stock.dto.UpdateStockRequest;
import com.sparta_express.company_product.stock.model.Stock;
import com.sparta_express.company_product.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockService {

    private final StockRepository stockRepository;

    // 재고 생성
    @Transactional
    public Stock createStock(CreateStockRequest request) {
        Stock stock = Stock.of(request.getProductId(), request.getHubId(), request.getStockQuantity());
        return stockRepository.save(stock);
    }

    // 재고 목록 조회
    public List<StockResponse> getStocks() {
        List<Stock> stocks = stockRepository.findAllByIsDeleteFalse();
        return stocks.stream()
                .map(StockResponse::from)
                .toList();
    }

    // 재고 상세 조회
    public StockResponse getStockById(UUID id) {
        Stock stock = stockRepository.findByIdAndIsDeleteFalse(id)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND));
        return StockResponse.from(stock);
    }

    // 재고 수정
    public StockResponse updateStock(UUID id, UpdateStockRequest request) {
        Stock stock = stockRepository.findByIdAndIsDeleteFalse(id)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND));
        stock.update(request.getStockQuantity());
        return StockResponse.from(stock);
    }

    // 재고 삭제
    public void deleteStock(UUID id, String email) {
        Stock stock = stockRepository.findByIdAndIsDeleteFalse(id)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND));
        stock.delete(email);
    }

}