package com.example.trafficapi.repository;

import com.example.trafficapi.model.Infraction;
import com.example.trafficapi.model.InfractionStatus;

import java.util.List;

import com.example.trafficapi.model.InfractionTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfractionRepository extends JpaRepository<Infraction, Long> {
    List<Infraction> findByStatus(InfractionStatus status);
    List<Infraction> findByType(InfractionTypes types);
}
