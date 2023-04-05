package com.sistemadeponto.sistemadeponto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemadeponto.sistemadeponto.model.Horas;

@Repository
public interface HorasRepository extends JpaRepository<Horas,Long> {
    
}
