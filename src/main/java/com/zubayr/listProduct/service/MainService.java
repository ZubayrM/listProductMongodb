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
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@PropertySource("classpath:application.yml")
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

    public ResponseEntity<ListOfProductsDto> addProductToList(String productId, String listId) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<List> optionalList = listRepository.findById(listId);
        if (optionalProduct.isPresent() && optionalList.isPresent()) {
            optionalList.get().addProduct(optionalProduct.get().getId());
            listRepository.save(optionalList.get());
            return ResponseEntity.ok(getListOfProductsDto(listId));
        } else {
            throw new ClassNotFoundException(String.format("c id %s|%s ничего не найдено", productId, listId));
        }
    }

    public ResponseEntity<Set<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productRepository.findDto());
    }

    public ResponseEntity<ListOfProductsDto> getListOfProduct(String listId) throws Exception {
        return ResponseEntity.ok(getListOfProductsDto(listId));
    }

    private ListOfProductsDto getListOfProductsDto(String listId) throws Exception {
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
