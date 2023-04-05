package com.copybox.demo.service;

import com.copybox.demo.collection.Referral;
import com.copybox.demo.collection.WithdrawTnx;
import com.copybox.demo.dto.User;
import com.copybox.demo.repository.ReferralRepository;
import com.copybox.demo.repository.UserRepository;
import com.copybox.demo.repository.WithdrawTnxRepository;
import com.copybox.demo.utils.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class AffiliateService {

//    private final MongoDatabase mongodb;


    //commission rate is fixed amount, not percentage
    final double COMMISSION_RATE_1 = 10.0;
    final double COMMISSION_RATE_2 = 5.0;
    private final UserRepository userRepository;
    private final ReferralRepository referralRepository;
    private final WithdrawTnxRepository withdrawTnxRepository;
    private final JwtUtils jwtUtils;

    private String getUserId(){
        return jwtUtils.getUserIdFromJwtToken()
    }
    //triggered when user register by refer link
    public Referral addReferral(String referredUserId, String refLink) throws Exception {
        User rootUser = userRepository.findByRefLink(refLink).orElseThrow(() -> new Exception("can not find user"));
        User referredUser = userRepository.findById(referredUserId).orElseThrow(() -> new Exception("can not find user"));
        Referral referral = Referral.builder()
                .rootUserId(rootUser.getId())
                .referredUserId(referredUser.getId())
                .payAt(null)
                .commissionRate(COMMISSION_RATE_1)
                .build();
        return referralRepository.save(referral);
    }

    //triggered when a user register by ref link/ make payment
    public void addCommissionToBalance(String userId) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new Exception("can not find user"));
            user.setBalance(user.getBalance() + COMMISSION_RATE_1);
            userRepository.save(user);
        } catch (Exception ex){
            log.error(ex.getMessage());
        }

    }

    //triggered when user withdraw money
    public void subtractBalance(String userId, double subtractAmount) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new Exception("can not find user"));
            user.setBalance(user.getBalance() - subtractAmount);
            userRepository.save(user);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

    }

    //triggered when user withdraw money
    public void addWithdrawTnx(String userId, double subtractAmount) {
        try {
            WithdrawTnx withdrawTnx = WithdrawTnx.builder()
                                                .amount(subtractAmount)
                                                .userId(userId)
                                                .time(LocalDateTime.now())
                                                .isSuccess(false)
                                                .build();
            withdrawTnxRepository.save(withdrawTnx);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    public double getBalance(String userId){
        try {
            User user =  userRepository.findById(userId).orElseThrow(() -> new Exception("can not find user"));
            return user.getBalance();
        } catch (Exception ex){
            log.error(ex.getMessage());
        }
        return 0.0;
    }

    public double getTotalEarning(String adminId) {
        try {
            User admin =  userRepository.findById(adminId).orElseThrow(() -> new Exception("can not find user"));
            return admin.getTotalEarning();
        } catch (Exception ex){
            log.error(ex.getMessage());
        }
        return 0.0;
    }

//    public long getNumberOfTier1Referral(String userId, LocalDateTime startTime, LocalDateTime endTime) {
//        return referralRepository.countByRootUserId(userId);
//    }
//
//    public int getNumberOfTier2Referral(String userId, LocalDateTime startTime, LocalDateTime endTime) {
//        List<String> tier1List = referralRepository.
//    }

//    public Page<Referral> getReferralPage(Pageable pageable, String userId) {
//        return referralRepository.findAll(pageable, userId);
//    }
}
