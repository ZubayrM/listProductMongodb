package com.zubayr.listProduct.repository;

import com.zubayr.listProduct.model.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListRepository extends MongoRepository<List, String> {

}
