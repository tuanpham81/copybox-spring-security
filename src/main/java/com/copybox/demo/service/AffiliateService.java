package com.copybox.demo.service;

import com.copybox.demo.collection.Referral;
import com.copybox.demo.collection.WithdrawTnx;
import com.copybox.demo.dto.User;
import com.copybox.demo.repository.ReferralRepository;
import com.copybox.demo.repository.UserRepository;
import com.copybox.demo.repository.WithdrawTnxRepository;
import com.copybox.demo.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AffiliateService {

    //commission rate is fixed amount, not percentage
    final double COMMISSION_RATE_1 = 10.0;
    final double COMMISSION_RATE_2 = 5.0;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReferralRepository referralRepository;
    @Autowired
    private WithdrawTnxRepository withdrawTnxRepository;
    @Autowired
    private JwtUtils jwtUtils;

    public String getUserId(String token){
        return jwtUtils.getUserIdFromJwtToken(token);
    }
    //triggered when user register by refer link
    public void addReferral(String referredUserId, String refLink) {
        try {
            User rootUser = userRepository.findByRefLink(refLink).orElseThrow(() -> new Exception("can not find user"));
            User referredUser = userRepository.findById(referredUserId).orElseThrow(() -> new Exception("can not find user"));
            Referral referral = Referral.builder()
                    .rootUserId(rootUser.getId())
                    .targetUserId(referredUser.getId())
                    .payAt(null)
//                    .commissionRate(COMMISSION_RATE_1)
                    .build();
            referralRepository.save(referral);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    //triggered when a user registering by ref link make payment
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

    public long getNumberOfTier1Referral(String userId, Date startTime, Date endTime) {
        return referralRepository.countByRootUserIdAndCreatedAtBetween(userId, startTime, endTime);
    }

    public long getNumberOfTier2Referral(String userId, Date startTime, Date endTime) {
        List<Referral> tier1List = referralRepository.findByRootUserIdAndCreatedAtBetween(userId, startTime, endTime);
        if (tier1List.size() != 0) {
            long number = tier1List.stream().reduce(ref
                    -> referralRepository.countByRootUserIdAndCreatedAtBetween(ref.getId(), null, null).)
            return 0;
        }
        return 0;
    }

//    public Page<Referral> getReferralPage(Pageable pageable, String userId) {
//        return referralRepository.findAll(pageable, userId);
//    }
}
