package com.example.trafficapi.controller;

import com.example.trafficapi.dto.InfractionCreateDto;
import com.example.trafficapi.dto.InfractionUpdateDto;
import com.example.trafficapi.model.Infraction;
import com.example.trafficapi.model.InfractionStatus;
import com.example.trafficapi.model.InfractionTypes;
import com.example.trafficapi.service.InfractionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/infractions")
@CrossOrigin(origins = "http://localhost:4200,https://kanbas.netlify.app")
public class InfractionController {

    private final InfractionService service;

    public InfractionController(InfractionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Infraction>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{status}")
    public ResponseEntity<List<Infraction>> getByStatus(@PathVariable InfractionStatus status) {
        List<Infraction> infractions = service.getByStatus(status);
        return ResponseEntity.ok(infractions);
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<Infraction>> getByType(@PathVariable InfractionTypes type) {
        List<Infraction> infractions = service.findByType(type);
        return ResponseEntity.ok(infractions);
    }

    @PostMapping
    public ResponseEntity<Infraction> create(@Valid @RequestBody InfractionCreateDto dto) {
        Infraction created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Infraction> update(@PathVariable Long id, @Valid @RequestBody InfractionUpdateDto dto) {
        Infraction updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
