package com.copybox.demo.controller;

import com.copybox.demo.service.AffiliateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/ref")
@Slf4j
public class AffiliateController {
    @Autowired
    private AffiliateService affiliateService;
    @GetMapping("test")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test success");
    }

//    @GetMapping("user-id")
//    @ResponseStatus(HttpStatus.OK)
//    private String getUserId(){
//        return keycloakService.getUserId();
//    }
    @GetMapping("user-id")
    @PreAuthorize("hasRole('ADMIN')")
    public String getUserId(@RequestHeader(name = "Authorization") String token) {
       return affiliateService.getUserId(token.substring(7));
    }

    @PostMapping("add-commission/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void addCommissionToBalance(@PathVariable String userId){
        affiliateService.addCommissionToBalance(userId);
    }

    @PatchMapping("subtract-balance/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void subtractBalance(@PathVariable String userId, double subtractAmount) {
        affiliateService.subtractBalance(userId, subtractAmount);
    }

    @PostMapping("add-withdraw-transaction/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void addWithdrawTnx(@PathVariable String userId, double subtractAmount) {
        affiliateService.addWithdrawTnx(userId, subtractAmount);
    }

    @GetMapping("get-balance/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public double getBalance(@PathVariable String userId){
        return affiliateService.getBalance(userId);
    }

    @GetMapping("get-total-earning/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public double getTotalEarning(@PathVariable String userId){
        return affiliateService.getTotalEarning(userId);
    }
}
