package com.example.trafficapi.service;

import com.example.trafficapi.dto.InfractionCreateDto;
import com.example.trafficapi.dto.InfractionUpdateDto;
import com.example.trafficapi.exception.ResourceNotFoundException;
import com.example.trafficapi.model.Infraction;
import com.example.trafficapi.model.InfractionStatus;
import com.example.trafficapi.model.InfractionTypes;
import com.example.trafficapi.repository.InfractionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InfractionService {

    private final InfractionRepository repo;

    public InfractionService(InfractionRepository repo) {
        this.repo = repo;
    }

    public List<Infraction> getAll() {
        return repo.findAll();
    }

    public List<Infraction> getByStatus(InfractionStatus status) {
        return repo.findByStatus(status);
    }

    public List<Infraction> findByType(InfractionTypes type){
        return repo.findByType(type);
    }
    public List<Infraction> getByDriverId(String driverId) {
        return repo.findByDriverId(driverId);
    }

    public Infraction create(InfractionCreateDto dto) {
        Infraction inf = new Infraction();
        inf.setDriverName(dto.getDriverName());
        inf.setDriverId(dto.getDriverId());
        inf.setAmount(dto.getAmount());
        inf.setDueDate(dto.getDueDate());
        inf.setOfficerName(dto.getOfficerName());
        inf.setNotes(dto.getNotes());

        InfractionStatus status = parseStatus(dto.getStatus()).orElse(InfractionStatus.PENDING);
        inf.setStatus(status);

        if (inf.getDueDate() != null && inf.getDueDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de vencimiento no puede ser anterior a la actual");
        }

        return repo.save(inf);
    }

    public Infraction update(Long id, InfractionUpdateDto dto) {
        Infraction existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Infracción no encontrada con id " + id));

        if (dto.getDriverName() != null) existing.setDriverName(dto.getDriverName());
        if (dto.getDriverId() != null) existing.setDriverId(dto.getDriverId());

        if (dto.getAmount() != null) existing.setAmount(dto.getAmount());
        if (dto.getDueDate() != null) existing.setDueDate(dto.getDueDate());
        if (dto.getOfficerName() != null) existing.setOfficerName(dto.getOfficerName());
        if (dto.getNotes() != null) existing.setNotes(dto.getNotes());

        if (dto.getStatus() != null) {
            existing.setStatus(parseStatus(dto.getStatus())
                    .orElseThrow(() -> new IllegalArgumentException("Estado inválido. Use PENDING, IN_PROCESS, PAID")));
        }

        return repo.save(existing);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Infracción no encontrada con id " + id);
        }
        repo.deleteById(id);
    }

    private Optional<InfractionStatus> parseStatus(String s) {
        if (s == null) return Optional.empty();
        try {
            return Optional.of(InfractionStatus.valueOf(s.trim().toUpperCase()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
