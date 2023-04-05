package com.copybox.demo.controller;

import com.copybox.demo.service.AffiliateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/ref")
@Slf4j
public class AffiliateController {
    private final AffiliateService affiliateService;

    @GetMapping("test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test success");
    }

//    @GetMapping("user-id")
//    @ResponseStatus(HttpStatus.OK)
//    private String getUserId(){
//        return keycloakService.getUserId();
//    }
    private String getUserId(@RequestHeader(name = "Authorization") String token){
        return
    }

    @PostMapping("add-commission")
    public void addCommissionToBalance(){
        String userId = getUserId();
        affiliateService.addCommissionToBalance(userId);
    }

    @PatchMapping("subtract-balance")
    public void subtractBalance(double subtractAmount) {
        String userId = getUserId();
        affiliateService.subtractBalance(userId, subtractAmount);
    }

    @PostMapping("add-withdraw-transaction")
    public void addWithdrawTnx(double subtractAmount) {
        String userId = getUserId();
        affiliateService.addWithdrawTnx(userId, subtractAmount);
    }

    @GetMapping("get-balance")
    public double getBalance(){
        String userId = getUserId();
        return affiliateService.getBalance(userId);
    }

    @GetMapping("get-total-earning")
    public double getTotalEarning(){
        String userId = getUserId();
        return affiliateService.getTotalEarning(userId);
    }
}
