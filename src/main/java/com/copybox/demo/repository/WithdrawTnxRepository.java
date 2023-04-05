package com.copybox.demo.repository;


import com.copybox.demo.collection.WithdrawTnx;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawTnxRepository extends MongoRepository<WithdrawTnx, String> {
}
