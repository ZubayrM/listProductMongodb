package com.zubayr.listProduct.controller;

import com.zubayr.listProduct.dto.ListDto;
import com.zubayr.listProduct.dto.ProductDto;
import com.zubayr.listProduct.exception.MaxCountToListException;
import com.zubayr.listProduct.service.MainService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/")
public class MainControllerImpl implements MainController{

    private final MainService mainService;

    @Autowired
    public MainControllerImpl(MainService mainService) {
        this.mainService = mainService;

    }

    @PostMapping("/save_list")
    @Override
    @ApiOperation("Save List")
    public ResponseEntity<?> saveList(@RequestBody ListDto dto) {
        try {
            return mainService.saveList(dto);
        } catch (MaxCountToListException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/save_product")
    @Override
    @ApiOperation("Save product")
    public ResponseEntity<ProductDto> saveProduct(@RequestBody ProductDto dto) {
        return mainService.saveProduct(dto);
    }

    @PutMapping("/{product_id}/add/{list_id}")
    @Override
    @ApiOperation("Add Product to List")
    public ResponseEntity<?> addProductToList(@PathVariable("product_id") String productId,@PathVariable("list_id") String listId) {
        try {
            return mainService.addProductToList(productId, listId);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all_product")
    @Override
    @ApiOperation("Get all Products")
    public ResponseEntity<Set<ProductDto>> allProduct() {
        return mainService.getAllProducts();
    }

    @GetMapping("/list/{id}")
    @Override
    @ApiOperation("Get list of Products")
    public ResponseEntity<?> getById(@PathVariable String id) {
        try {
            return mainService.getListOfProduct(id);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
