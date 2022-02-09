package com.zubayr.listProduct.repository;

import com.zubayr.listProduct.dto.ProductDto;
import com.zubayr.listProduct.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    @Query(value = "{}", fields = "{name:1, description:1, kcal:1}")
    Set<ProductDto> findDto();
}
