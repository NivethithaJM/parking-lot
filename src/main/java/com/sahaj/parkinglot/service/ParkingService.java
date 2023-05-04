package com.sahaj.parkinglot.service;

import java.time.LocalDateTime;

import com.sahaj.parkinglot.model.ParkingLotUser;
import com.sahaj.parkinglot.model.VehicleType;

public class ParkingService {

  private static ParkingService parkingService;

  private ParkingService() {
  }

  public static ParkingService getInstance() {
    if (parkingService == null) {
      parkingService = new ParkingService();
    }
    return parkingService;
  }


  public void enterParking(String locationName, VehicleType vehicleType, String vehicleName, int slotNumber,
      LocalDateTime entryDate) {
    ParkingLotUser user = new ParkingLotUser();
    user.setVehicleType(vehicleType);
    user.setName(vehicleName);
    user.setSlotNo(slotNumber);
    user.setEntryDate(entryDate);
    LocationService.getInstance().addParkingLotUser(locationName, user);
  }

  public void exitParking(String locationName, VehicleType vehicleType, int slotNumber, LocalDateTime exitDate) {
    LocationService.getInstance().removeParkingLotUser(locationName, vehicleType, slotNumber, exitDate);
  }

}
