package com.sahaj.parkinglot.service;

import java.time.LocalDateTime;

import com.sahaj.parkinglot.constants.VehicleType;
import com.sahaj.parkinglot.model.response.ParkingReceipt;
import com.sahaj.parkinglot.model.response.ParkingTicket;

public interface ParkingService {
  ParkingTicket enterParking(String locationName, VehicleType vehicleType, String vehicleName, int slotNumber,
      LocalDateTime entryDate);
  ParkingReceipt exitParking(String locationName, VehicleType vehicleType, int slotNumber, LocalDateTime exitDate);
}
