package com.copybox.demo.collection;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "referral")
public class Referral {
    @Id
    private String id;
    private String rootUserId;
    private String targetUserId;

    // if null is not making payment
    private LocalDateTime payAt;
//    private double commissionRate;
//    private double commissionAmount;

}
