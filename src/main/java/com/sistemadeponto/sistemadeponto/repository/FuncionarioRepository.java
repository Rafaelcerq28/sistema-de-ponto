package com.sistemadeponto.sistemadeponto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemadeponto.sistemadeponto.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{
    
}
