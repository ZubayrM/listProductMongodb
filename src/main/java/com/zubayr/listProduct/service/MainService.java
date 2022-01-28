package com.zubayr.listProduct.service;

import com.zubayr.listProduct.dto.ListDto;
import com.zubayr.listProduct.dto.ListOfProductsDto;
import com.zubayr.listProduct.dto.ProductDto;
import com.zubayr.listProduct.exception.MaxCountToListException;
import com.zubayr.listProduct.model.List;
import com.zubayr.listProduct.model.Product;
import com.zubayr.listProduct.repository.ListRepository;
import com.zubayr.listProduct.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MainService {

    private final ListRepository listRepository;
    private final ProductRepository productRepository;

    @Value("${max_count}")
    private Long maxCount;

    @Autowired
    public MainService(ListRepository listRepository, ProductRepository productRepository) {
        this.listRepository = listRepository;
        this.productRepository = productRepository;
    }

    public ResponseEntity<ListDto> saveList(ListDto dto) {
        if (listRepository.count() < maxCount)
            return  ResponseEntity.ok(ListDto.createDto(listRepository.save(List.createList(dto))));
        else
            throw new MaxCountToListException();
    }

    public ResponseEntity<ProductDto> saveProduct(ProductDto dto) {
        return ResponseEntity.ok(ProductDto.createDto(productRepository.save(Product.createProduct(dto))));
    }

    public ResponseEntity<ListOfProductsDto> addProductToList(String productId, String listId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<List> optionalList = listRepository.findById(listId);
        optionalList.ifPresent(list -> {
            list.addProduct(optionalProduct.orElseThrow().getId());
            listRepository.save(list);
        });
        return ResponseEntity.ok(getListOfProductsDto(listId));
    }

    public ResponseEntity<Set<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productRepository.findAll()
                .stream()
                .map(ProductDto::createDto)
                .collect(Collectors.toSet()));
    }

    public ResponseEntity<ListOfProductsDto> getListOfProduct(String listId) {
        return ResponseEntity.ok(getListOfProductsDto(listId));
    }

    private ListOfProductsDto getListOfProductsDto(String listId) {
        return listRepository.findById(listId)
                .map(list -> {
                            ListOfProductsDto dto = new ListOfProductsDto();
                            dto.setListName(list.getName());
                            dto.setProducts(list.getProducts().stream()
                                    .map(productRepository::findById)
                                    .map(Optional::orElseThrow)
                                    .map(ProductDto::createDto).collect(Collectors.toSet()));
                            dto.setKcal(dto.getProducts().stream()
                                    .map(ProductDto::getKcal)
                                    .flatMapToInt(IntStream::of)
                                    .sum());
                            return dto;
                        }
                ).orElseThrow();
    }
}
