package com.sahaj.parkinglot.example;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sahaj.parkinglot.model.DataStore;
import com.sahaj.parkinglot.constants.VehicleType;
import com.sahaj.parkinglot.model.response.ParkingReceipt;
import com.sahaj.parkinglot.model.response.ParkingTicket;
import com.sahaj.parkinglot.service.LocationService;

@SpringBootTest
public class ExampleTests extends TestUtil {

  @Autowired
  LocationService locationService;

  @BeforeEach
  public void setup() {
    DataStore.initializeDataStore();
  }

  @Test
  public void case_one() {

    //Park motorcycle
    LocalDateTime dateTime = LocalDateTime.now();
    ParkingTicket ticket = parkUser(LOCAL, VehicleType.TWO_WHEELER, "MotorCycle", 1, dateTime);
    Assertions.assertNotNull(ticket);

    //Park Scooter
    dateTime = dateTime.plusMinutes(40);
    ticket = parkUser(LOCAL, VehicleType.TWO_WHEELER, "Scooter", 2, dateTime);
    Assertions.assertNotNull(ticket);

    //Park Scooter
    try {
      parkUser(LOCAL, VehicleType.TWO_WHEELER, "Scooter", 2, dateTime);
    } catch (RuntimeException e) {
      Assertions.assertEquals("Slot not available", e.getMessage());
    }

    //Unpark Scooter
    dateTime = dateTime.plusMinutes(56);
    ParkingReceipt receipt = unParkUser(LOCAL, VehicleType.TWO_WHEELER, 2, dateTime);
    Assertions.assertEquals(10d, receipt.getTotalFee());

    //Park MotorCycle
    dateTime = dateTime.plusMinutes(19);
    ticket = parkUser(LOCAL, VehicleType.TWO_WHEELER, "MotorCycle", 2, dateTime);
    Assertions.assertNotNull(ticket);

    //Unpark MotorCycle
    dateTime = dateTime.plusMinutes(105);
    receipt = unParkUser(LOCAL, VehicleType.TWO_WHEELER, 1, dateTime);
    Assertions.assertEquals(40d, receipt.getTotalFee());
  }

  @Test
  public void case_two() {
    LocalDateTime dateTime = LocalDateTime.now();

    //Motorcycle parked for 3 hours and 30 mins. Fees: 40
    ParkingTicket ticket = parkUser(MALL, VehicleType.TWO_WHEELER, "MotorCycle", 1, dateTime);
    Assertions.assertNotNull(ticket);

    ParkingReceipt receipt = unParkUser(MALL, VehicleType.TWO_WHEELER, 1, dateTime.plusMinutes(3 * 60 + 30));
    Assertions.assertEquals(40d, receipt.getTotalFee());

    //Car parked for 6 hours and 1 min. Fees: 140
    ticket = parkUser(MALL, VehicleType.LMV, "Car", 2, dateTime);
    Assertions.assertNotNull(ticket);

    receipt = unParkUser(MALL, VehicleType.LMV, 2, dateTime.plusMinutes(6 * 60 + 1));
    Assertions.assertEquals(140d, receipt.getTotalFee());

    //Truck parked for 1 hour and 59 mins. Fees: 100
    ticket = parkUser(MALL, VehicleType.HMV, "Truck", 1, dateTime);
    Assertions.assertNotNull(ticket);

    receipt = unParkUser(MALL, VehicleType.HMV, 1, dateTime.plusMinutes(60 + 59));
    Assertions.assertEquals(100d, receipt.getTotalFee());
  }

  @Test
  public void case_three() {
    LocalDateTime dateTime = LocalDateTime.now();

    //Motorcycle parked for 3 hours and 40 mins. Fees: 30
    ParkingTicket ticket = parkUser(STADIUM, VehicleType.TWO_WHEELER, "MotorCycle", 1, dateTime);
    Assertions.assertNotNull(ticket);

    ParkingReceipt receipt = unParkUser(STADIUM, VehicleType.TWO_WHEELER, 1, dateTime.plusMinutes(3 * 60 + 40));
    Assertions.assertEquals(30d, receipt.getTotalFee());

    //Motorcycle parked for 14 hours and 59 mins. Fees: 390.
    ticket = parkUser(STADIUM, VehicleType.TWO_WHEELER, "MotorCycle", 1, dateTime);
    Assertions.assertNotNull(ticket);

    receipt = unParkUser(STADIUM, VehicleType.TWO_WHEELER, 1, dateTime.plusMinutes(14 * 60 + 59));
    Assertions.assertEquals(390d, receipt.getTotalFee());

    //Electric SUV parked for 11 hours and 30 mins. Fees: 180.
    ticket = parkUser(STADIUM, VehicleType.LMV, "Suv", 1, dateTime);
    Assertions.assertNotNull(ticket);

    receipt = unParkUser(STADIUM, VehicleType.LMV, 1, dateTime.plusMinutes(11 * 60 + 30));
    Assertions.assertEquals(180d, receipt.getTotalFee());

    //SUV parked for 13 hours and 5 mins. Fees: 580.
    ticket = parkUser(STADIUM, VehicleType.LMV, "Suv", 1, dateTime);
    Assertions.assertNotNull(ticket);

    receipt = unParkUser(STADIUM, VehicleType.LMV, 1, dateTime.plusMinutes(13 * 60 + 5));
    Assertions.assertEquals(580d, receipt.getTotalFee());
  }

  @Test
  public void case_four() {
    LocalDateTime dateTime = LocalDateTime.now();

    //Motorcycle parked for 55 mins. Fees: 0
    ParkingTicket ticket = parkUser(AIRPORT, VehicleType.TWO_WHEELER, "MotorCycle", 1, dateTime);
    Assertions.assertNotNull(ticket);

    ParkingReceipt receipt = unParkUser(AIRPORT, VehicleType.TWO_WHEELER, 1, dateTime.plusMinutes(55));
    Assertions.assertEquals(0d, receipt.getTotalFee());

    //Motorcycle parked for 14 hours and 59 mins. Fees: 60
    ticket = parkUser(AIRPORT, VehicleType.TWO_WHEELER, "MotorCycle", 1, dateTime);
    Assertions.assertNotNull(ticket);

    receipt = unParkUser(AIRPORT, VehicleType.TWO_WHEELER, 1, dateTime.plusMinutes(14 * 60 + 59));
    Assertions.assertEquals(60d, receipt.getTotalFee());

    //Motorcycle parked for 1 day and 12 hours. Fees: 160
    ticket = parkUser(AIRPORT, VehicleType.TWO_WHEELER, "MotorCycle", 1, dateTime);
    Assertions.assertNotNull(ticket);

    receipt = unParkUser(AIRPORT, VehicleType.TWO_WHEELER, 1, dateTime.plusMinutes(36 * 60));
    Assertions.assertEquals(160d, receipt.getTotalFee());

    //Car parked for 50 mins. Fees: 60
    ticket = parkUser(AIRPORT, VehicleType.LMV, "Car", 1, dateTime);
    Assertions.assertNotNull(ticket);

    receipt = unParkUser(AIRPORT, VehicleType.LMV, 1, dateTime.plusMinutes(50));
    Assertions.assertEquals(60d, receipt.getTotalFee());

    //SUV parked for 23 hours and 59 mins. Fees: 80
    ticket = parkUser(AIRPORT, VehicleType.LMV, "SUV", 1, dateTime);
    Assertions.assertNotNull(ticket);

    receipt = unParkUser(AIRPORT, VehicleType.LMV, 1, dateTime.plusMinutes(23 * 60 + 59));
    Assertions.assertEquals(80d, receipt.getTotalFee());

    //Car parked for 3 days and 1 hour. Fees: 400
    ticket = parkUser(AIRPORT, VehicleType.LMV, "Car", 1, dateTime);
    Assertions.assertNotNull(ticket);

    receipt = unParkUser(AIRPORT, VehicleType.LMV, 1, dateTime.plusMinutes(3 * 24 * 60 + 60));
    Assertions.assertEquals(400d, receipt.getTotalFee());
  }

}
