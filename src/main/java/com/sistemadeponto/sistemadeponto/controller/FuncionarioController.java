package com.sistemadeponto.sistemadeponto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sistemadeponto.sistemadeponto.model.Funcionario;
import com.sistemadeponto.sistemadeponto.service.FuncionarioService;

@RestController
@RequestMapping("/api")
public class FuncionarioController {
    
    private FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    //Adiciona o funcionario
    @PostMapping("/funcionario")
    @ResponseStatus(HttpStatus.CREATED)
    public Funcionario criarFuncionario(@RequestBody Funcionario funcionario){
        return funcionarioService.criarFuncionario(funcionario);
    }

    //Lista TODOS os funcionarios
    @GetMapping("/funcionario")
    @ResponseStatus(HttpStatus.OK)
    public List<Funcionario> listarFuncionarioS(){
        return funcionarioService.listarFuncionarioS();
    }

    //Pega o funcionario pelo ID
    @GetMapping("/funcionario/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Funcionario> buscaFuncionarioPeloId(@PathVariable(value="id")Long id){
        return funcionarioService.buscaFuncionarioPeloId(id);
    }

}
