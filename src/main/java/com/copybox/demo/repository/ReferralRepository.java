package com.copybox.demo.repository;

import com.copybox.demo.collection.Referral;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferralRepository extends MongoRepository<Referral, String> {
    long countByRootUserId(String rootUserid);
    List<String> findByRootUserId(String rootUserid);
//    Page<Referral> findAll(Pageable pageable, String rootUserId);
}