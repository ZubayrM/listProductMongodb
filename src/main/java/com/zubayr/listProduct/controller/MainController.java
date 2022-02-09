package com.zubayr.listProduct.controller;

import com.zubayr.listProduct.dto.ListDto;
import com.zubayr.listProduct.dto.ProductDto;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface MainController {

    ResponseEntity<?> saveList(ListDto dto);

    ResponseEntity<ProductDto> saveProduct(ProductDto dto);

    ResponseEntity<?> addProductToList(String productId, String listId);

    ResponseEntity<Set<ProductDto>> allProduct();

    ResponseEntity<?> getById(String id);

}
