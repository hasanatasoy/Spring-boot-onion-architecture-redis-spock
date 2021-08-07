package com.trendyol.product;

import com.trendyol.product.domain.Product;
import com.trendyol.product.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.management.Notification;
import java.math.BigDecimal;

@SpringBootApplication
public class ProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

	@Bean
	public CommandLineRunner firstRun(ProductService productService){
		return args -> {
			for(var i=0; i<100;i++){
				var product = new Product();
				var price = BigDecimal.valueOf((i+1)*12);
				product.setPrice(price);
				product.setOldPrice(price.multiply(BigDecimal.valueOf(1.5)));
				product.setQuantity(i);
				product.setTitle("Ürün "+i);
				product.setImageUrl("Urun"+i+".png");
				productService.save(product);
			}
		};
	}

}
