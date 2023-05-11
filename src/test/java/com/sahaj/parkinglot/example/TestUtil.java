package com.sahaj.parkinglot.example;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.sahaj.parkinglot.model.VehicleType;
import com.sahaj.parkinglot.service.ParkingService;
import com.sahaj.parkinglot.serviceimpl.MiscellaneousServiceImpl;

public class TestUtil {

  static final String LOCAL = "LOCAL";
  static final String MALL = "MALL";
  static final String AIRPORT = "AIRPORT";
  static final String STADIUM = "STADIUM";
  @Autowired
  ParkingService parkingService;
  @Autowired
  MiscellaneousServiceImpl miscellaneousService;

  void parkUser(String locationName, VehicleType vehicleType, String vehicleName, int slotNumber,
      LocalDateTime entryDate) {
    try {
      parkingService.enterParking(locationName, vehicleType, vehicleName, slotNumber, entryDate);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  void unParkUser(String locationName, VehicleType vehicleType, int slotNo, LocalDateTime exitDate) {
    parkingService.exitParking(locationName, vehicleType, slotNo, exitDate);
  }

}
