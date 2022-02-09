package com.zubayr.listProduct.model;

import com.zubayr.listProduct.dto.ListDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "lists")
@AllArgsConstructor
@NoArgsConstructor
public class List {

    @Id
    private String id;

    private String name;

    private Set<String> products = new HashSet<>();

    public void addProduct(String product){
        this.products.add(product);
    }

    public static List createList(ListDto dto){
        List list = new List();
        list.setName(dto.getName());
        return list;
    }

}
