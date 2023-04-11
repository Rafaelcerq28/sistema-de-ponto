package com.sistemadeponto.sistemadeponto.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sistemadeponto.sistemadeponto.model.Funcionario;
import com.sistemadeponto.sistemadeponto.repository.FuncionarioRepository;

@Service
public class FuncionarioService {
    
    private FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    //Adiciona o funcionario
    public Funcionario criarFuncionario(Funcionario funcionario){
        return funcionarioRepository.save(funcionario);
    }

    //Lista TODOS os funcionarios
    public List<Funcionario> listarFuncionarioS(){
        return funcionarioRepository.findAll();
    }

    //Pega o funcionario pelo ID
    public ResponseEntity<Funcionario> buscaFuncionarioPeloId(Long id){
        return funcionarioRepository.findById(id)
            .map(funcionario -> ResponseEntity.ok().body(funcionario))
                .orElse(ResponseEntity.notFound().build());
    }
}
