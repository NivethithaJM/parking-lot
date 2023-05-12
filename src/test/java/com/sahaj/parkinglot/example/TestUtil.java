package com.sahaj.parkinglot.example;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import com.sahaj.parkinglot.constants.VehicleType;
import com.sahaj.parkinglot.model.response.ParkingReceipt;
import com.sahaj.parkinglot.model.response.ParkingTicket;
import com.sahaj.parkinglot.service.ParkingService;
import com.sahaj.parkinglot.serviceimpl.MiscellaneousServiceImpl;

@TestPropertySource(properties = {"sahaj.data-source.file=src/main/resources/data-store-test.json"})

public class TestUtil {

  static final String LOCAL = "LOCAL";
  static final String MALL = "MALL";
  static final String AIRPORT = "AIRPORT";
  static final String STADIUM = "STADIUM";
  @Autowired
  ParkingService parkingService;
  @Autowired
  MiscellaneousServiceImpl miscellaneousService;

  ParkingTicket parkUser(String locationName, VehicleType vehicleType, String vehicleName, int slotNumber,
      LocalDateTime entryDate) {
    return parkingService.enterParking(locationName, vehicleType, vehicleName, slotNumber, entryDate);
  }

  ParkingReceipt unParkUser(String locationName, VehicleType vehicleType, int slotNo, LocalDateTime exitDate) {
    return parkingService.exitParking(locationName, vehicleType, slotNo, exitDate);
  }

}
