package com.copybox.demo.repository;

import com.copybox.demo.collection.Referral;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReferralRepository extends MongoRepository<Referral, String> {
    long countByRootUserIdAndCreatedAtBetween(String rootUserId, Date startTime, Date endTime);
    List<Referral> findByRootUserIdAndCreatedAtBetween(String rootUserId, Date startTime, Date endTime);
    List<String> findByRootUserId(String rootUserid);
//    Page<Referral> findAll(Pageable pageable, String rootUserId);
}