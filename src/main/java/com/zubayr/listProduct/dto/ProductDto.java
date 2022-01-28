package com.zubayr.listProduct.dto;

import com.zubayr.listProduct.model.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ProductDto", description = "Product, response and request model")
public class ProductDto {

    @NotBlank
    @ApiParam(value = "Product name")
    private String name;

    @ApiParam(value = "Product description")
    private String description;

    @ApiParam(value = "Product kilocalories")
    private Integer kcal;

    public static ProductDto createDto(Product product) {
        return new ProductDto(product.getName(),product.getDescription(), product.getKcal());
    }
}
