package com.zubayr.listProduct.dto;

import com.zubayr.listProduct.model.List;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ListDto", description = "List, response and request model")
public class ListDto {

    @ApiParam("List name")
    private String name;

    public static ListDto createDto (List list){
        return new ListDto(list.getName());
    }
}
