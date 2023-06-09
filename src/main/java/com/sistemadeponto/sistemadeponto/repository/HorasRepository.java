package com.sistemadeponto.sistemadeponto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sistemadeponto.sistemadeponto.model.Funcionario;
import com.sistemadeponto.sistemadeponto.model.Horas;

@Repository
public interface HorasRepository extends JpaRepository<Horas,Long> {
    
    //Query para fazer um select no banco para retornar apenas o que eu preciso.
    @Query(nativeQuery = true, value = "SELECT HORAS_TRABALHADAS FROM HORAS WHERE FUNCIONARIO_ID = :id")
    List<String> pegaHorasTrabalhadas(@Param("id") Long id);

}
