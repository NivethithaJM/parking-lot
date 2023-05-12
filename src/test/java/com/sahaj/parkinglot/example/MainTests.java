package com.sahaj.parkinglot.example;

import java.time.LocalDateTime;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.sahaj.parkinglot.model.DataStore;
import com.sahaj.parkinglot.constants.VehicleType;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainTests extends TestUtil {
  @Test
  @Order(0)
  public void initializeDataStore() {
    DataStore.initializeDataStore();
    System.out.println(DataStore.getInstance().toString());
  }

  @Test
  @Order(4)
  public void displayLocation() {
    miscellaneousService.displayParkingStatus();
  }

  @Test
  @Order(1)
  public void parkUser() {
    VehicleType vehicleType = VehicleType.TWO_WHEELER;
    String vehicleName = "Scooter";
    int slotNumber = 1;
    LocalDateTime entryDate = LocalDateTime.now();
    parkUser(LOCAL, vehicleType, vehicleName, slotNumber, entryDate);
  }

  @Test
  @Order(2)
  public void unParkUser() {
    VehicleType vehicleType = VehicleType.TWO_WHEELER;
    int slotNo = 1;
    LocalDateTime exitDate = LocalDateTime.now();
    unParkUser(LOCAL, vehicleType, slotNo, exitDate);
  }
}
