package com.sahaj.parkinglot.service;

import java.time.LocalDateTime;

import com.sahaj.parkinglot.model.VehicleType;

public interface ParkingService {
  void enterParking(String locationName, VehicleType vehicleType, String vehicleName, int slotNumber,
      LocalDateTime entryDate);
  void exitParking(String locationName, VehicleType vehicleType, int slotNumber, LocalDateTime exitDate);
}
