package com.sahaj.parkinglot.example;

import java.time.LocalDateTime;

import com.sahaj.parkinglot.model.VehicleType;
import com.sahaj.parkinglot.service.MiscellaneousService;
import com.sahaj.parkinglot.service.ParkingService;

public class TestUtil {

  static final String LOCAL = "LOCAL";
  static final String MALL = "MALL";
  static final String AIRPORT = "AIRPORT";
  static final String STADIUM = "STADIUM";
  MiscellaneousService miscellaneousService = new MiscellaneousService();

  void parkUser(String locationName, VehicleType vehicleType, String vehicleName, int slotNumber,
      LocalDateTime entryDate) {
    try {
      ParkingService.getInstance().enterParking(locationName, vehicleType, vehicleName, slotNumber, entryDate);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  void unParkUser(String locationName, VehicleType vehicleType, int slotNo, LocalDateTime exitDate) {
    ParkingService.getInstance().exitParking(locationName, vehicleType, slotNo, exitDate);
  }

}
