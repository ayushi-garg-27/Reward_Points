package com.ayushi.CustomerRewardPointsApplication.controller;

import com.ayushi.CustomerRewardPointsApplication.service.RewardPointsCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rewardpoints")
public class RewardPointsController {

    @Autowired
    private RewardPointsCalculator rewardCalculator;

    @PostMapping("/calculate")
    public int calculateTotalPoints(@RequestParam String customerId) {
        return rewardCalculator.calculateTotalPoints(customerId);
    }

    @PostMapping("/calculate-month")
    public int calculateMonthlyPoints(@RequestParam String customerId, @RequestParam String month) {
        return rewardCalculator.calculateMonthlyPoints(customerId, month);
    }
}

