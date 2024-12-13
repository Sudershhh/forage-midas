package com.jpmc.midascore.controller;


import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public Balance getBalance(@RequestParam Long userId)
    {

        try
        {

            Optional<UserRecord> user = userRepository.findById(userId);

            if(user.isPresent())
            {
                return new Balance(user.get().getBalance());

            }

        }catch (Exception e)
        {
            System.out.println("Exception Occurred :" + e.getMessage());
        }


        return new Balance(0);
    }
}
