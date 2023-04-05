package com.copybox.demo.collection;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "withdraw-tnx")
public class WithdrawTnx {
    @Id
    private String id;
    private String userId;
    private LocalDateTime time;
    private boolean isSuccess;
    private double amount;
}
