package com.zubayr.listProduct.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ListOfProductDto" ,description = "List of product, response model")
public class ListOfProductsDto {

    @ApiParam("List name")
    private String listName;

    @ApiParam("List productions")
    private Set<ProductDto> products;

    @ApiParam("Size kcal")
    private Integer kcal;

}
