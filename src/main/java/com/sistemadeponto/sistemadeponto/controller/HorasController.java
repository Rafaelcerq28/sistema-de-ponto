package com.sistemadeponto.sistemadeponto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sistemadeponto.sistemadeponto.model.Horas;
import com.sistemadeponto.sistemadeponto.service.HorasService;

@RestController
@RequestMapping("/api")
public class HorasController {

    public HorasController(HorasService horasService) {
        this.horasService = horasService;
    }

    HorasService horasService;

    //Adicionando horas e listando
    @PostMapping("/horas")
    @ResponseStatus(HttpStatus.CREATED)
    public Horas criarHoras(@RequestBody Horas horas){
        return horasService.criarHoras(horas);
    }

    @GetMapping("/horas")
    @ResponseStatus(HttpStatus.OK)
    public List<Horas> teste(){
        return horasService.listarHoras();
    }

    @GetMapping("/horastrabalhadas")
    @ResponseStatus(HttpStatus.OK)
    public String horastrabalhadas(){
        return horasService.somarHorasTrabalhadas();
    }
}
