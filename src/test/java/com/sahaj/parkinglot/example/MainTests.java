package com.sahaj.parkinglot.example;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.sahaj.parkinglot.model.DataStore;
import com.sahaj.parkinglot.model.VehicleType;

public class MainTests extends TestUtil {
  @Test
  public void initializeDataStore() {
    DataStore.initializeDataStore();
    System.out.println(DataStore.getInstance().toString());
  }

  @Test
  public void displayLocation() {
    miscellaneousService.displayParkingStatus();
  }

  @Test
  public void parkUser() {
    VehicleType vehicleType = VehicleType.TWO_WHEELER;
    String vehicleName = "Scooter";
    int slotNumber = 1;
    LocalDateTime entryDate = LocalDateTime.now();
    parkUser(LOCAL, vehicleType, vehicleName, slotNumber, entryDate);
  }

  @Test
  public void unParkUser() {
    VehicleType vehicleType = VehicleType.TWO_WHEELER;
    int slotNo = 1;
    LocalDateTime exitDate = LocalDateTime.now();
    unParkUser(LOCAL, vehicleType, slotNo, exitDate);
  }
}
