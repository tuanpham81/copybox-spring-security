package com.copybox.demo.controller;

import com.copybox.demo.service.AffiliateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private String getUserId(@RequestHeader(name = "Authorization") String token) {
       return affiliateService.getUserId(token);
    }

    @PostMapping("add-commission/{userId}")
    public void addCommissionToBalance(@PathVariable String userId){
        affiliateService.addCommissionToBalance(userId);
    }

    @PatchMapping("subtract-balance/{userId}")
    public void subtractBalance(@PathVariable String userId, double subtractAmount) {
        affiliateService.subtractBalance(userId, subtractAmount);
    }

    @PostMapping("add-withdraw-transaction/{userId}")
    public void addWithdrawTnx(@PathVariable String userId, double subtractAmount) {
        affiliateService.addWithdrawTnx(userId, subtractAmount);
    }

    @GetMapping("get-balance/{userId}")
    public double getBalance(@PathVariable String userId){
        return affiliateService.getBalance(userId);
    }

    @GetMapping("get-total-earning/{userId}")
    public double getTotalEarning(@PathVariable String userId){
        return affiliateService.getTotalEarning(userId);
    }
}
