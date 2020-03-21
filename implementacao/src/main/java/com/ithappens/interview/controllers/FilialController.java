package com.ithappens.interview.controllers;

import com.ithappens.interview.dtos.FilialDTO;
import com.ithappens.interview.services.FilialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/filial")
public class FilialController {

    @Autowired
    FilialService filialService;

    @GetMapping("/{id}")
    public FilialDTO get(@PathVariable Integer id) {
        return filialService.asDTO(filialService.findById(id));
    }

}
