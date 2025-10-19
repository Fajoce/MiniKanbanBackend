package com.example.trafficapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class InfractionUpdateDto {

    @NotBlank(message = "El nombre del conductor es obligatorio")
    @Size(min = 10, message = "El nombre debe tener al menos 10 caracteres")
    private String driverName;
    @NotBlank(message = "El número de licencia es obligatorio")
    private String driverId;
    private String type;

    @Min(value = 1, message = "El valor debe ser mayor a 0")
    private Double amount;

    private String status;
    private LocalDateTime dueDate;
    private String officerName;
    private String notes;

    // getters & setters

    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }

    public String getDriverId() { return driverId; }
    public void setDriverId(String driverId) { this.driverId = driverId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public java.time.LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(java.time.LocalDateTime dueDate) { this.dueDate = dueDate; }

    public String getOfficerName() { return officerName; }
    public void setOfficerName(String officerName) { this.officerName = officerName; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
