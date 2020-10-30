package com.lambdaschool.piggybank.controllers;

import com.lambdaschool.piggybank.models.PiggyBank;
import com.lambdaschool.piggybank.repositories.PiggyBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PiggyBankController {

    @Autowired
    private PiggyBankRepository piggyrepo;

    private List<PiggyBank> findCoins(List<PiggyBank> piggyBankList, CheckPiggyBank tester)
    {
        List<PiggyBank> tempList = new ArrayList<>();

        for (PiggyBank p : piggyBankList)
        {
            if (tester.test(p))
            {
                tempList.add(p);
            }
        }

        return tempList;
    }

    @GetMapping(value = "/total", produces = {"application/json"})
    public ResponseEntity<?> addTotal()
    {
        List<PiggyBank> myList = new ArrayList<>();

        piggyrepo.findAll().iterator().forEachRemaining(myList::add);

        double total =0;
        int quantity=0;
        String coin = " ";
        for (PiggyBank p : myList)
        {
            quantity=p.getQuantity();
            total+=p.getValue()*p.getQuantity();
            if (quantity == 1) {
                System.out.println(quantity + " " + p.getName());
            } else {
                System.out.println(quantity + " " + p.getNameplural());
            }
        }
//        System.out.println(quantity + coin);
        System.out.println("The piggy bank holds " + total);
        return new ResponseEntity<>("The piggy bank holds : $" + total, HttpStatus.OK);
    }
}
