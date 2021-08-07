package com.trendyol.product.service.impl;

import com.trendyol.product.domain.Product;
import com.trendyol.product.domain.ProductRepository;
import com.trendyol.product.exception.BadRequestException;
import com.trendyol.product.exception.ProductNotFoundException;
import com.trendyol.product.message.PriceChangedMessage;
import com.trendyol.product.message.StockChangedMessage;
import com.trendyol.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public void updatePrice(long productId, BigDecimal price) {
        var optProduct = productRepository.findById(productId);
        if(optProduct.isEmpty())
            throw new ProductNotFoundException();
        var product = optProduct.get();
        product.setOldPrice(product.getPrice());
        product.setPrice(price);
        productRepository.save(product);
        sendPriceChangedMessage(product.getId(), product.getPrice(), product.getOldPrice());
    }

    @Override
    public void updateStock(long productId, int quantity) {
        var optProduct = productRepository.findById(productId);
        if(optProduct.isEmpty())
            throw new ProductNotFoundException();
        var product = optProduct.get();
        var oldQuantity = product.getQuantity();
        product.setQuantity(quantity);
        productRepository.save(product);
        sendStockChangedMessage(product.getId(), product.getQuantity(), oldQuantity);
    }

    @Override
    public void save(Product product) {
        if(product == null)
            throw new BadRequestException();
        productRepository.save(product);
    }

    @Override
    public Product get(long productId) {
        if(productId <= 0L)
            throw new BadRequestException();
        var product  = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        return product;
    }

    @Override
    public void sendPriceChangedMessage(long productId, BigDecimal price, BigDecimal oldPrice){
        var priceChangedMessage = new PriceChangedMessage();
        priceChangedMessage.setPrice(price);
        priceChangedMessage.setOldPrice(oldPrice);
        priceChangedMessage.setProductId(productId);
        //TO DO: send message to message broker
    }

    @Override
    public void sendStockChangedMessage(long productId, int quantity, int oldQuantity) {
        var stockChangedMessage = new StockChangedMessage();
        stockChangedMessage.setProductId(productId);
        stockChangedMessage.setQuantity(quantity);
        stockChangedMessage.setOldQuantity(oldQuantity);
        //TO DO: send message to message broker
    }
}
