package com.company.clinic.service;

import org.springframework.stereotype.Service;

@Service(PruebaService.NAME)
public class PruebaServiceBean implements PruebaService {

    public String printName(String name) {
        System.out.println(name);
        return name;
    }

}