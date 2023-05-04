package com.sahaj.parkinglot.example;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sahaj.parkinglot.model.DataStore;
import com.sahaj.parkinglot.model.Location;
import com.sahaj.parkinglot.model.ParkingLotUser;
import com.sahaj.parkinglot.model.VehicleType;
import com.sahaj.parkinglot.service.LocationService;

@SpringBootTest
public class ExampleTests extends TestUtil {

  @Autowired
  LocationService locationService;

  @Test
  public void case_one()
  {
    DataStore.initializeDataStore();
    LocalDateTime dateTime = LocalDateTime.now();
    //Park motorcycle
    parkUser(LOCAL, VehicleType.TWO_WHEELER, "MotorCycle", 1, dateTime);
    dateTime = dateTime.plusMinutes(40);
    //Park Scooter
    parkUser(LOCAL, VehicleType.TWO_WHEELER, "Scooter", 2, dateTime);
    //Park Scooter
    parkUser(LOCAL, VehicleType.TWO_WHEELER, "Scooter", 2, dateTime);
    dateTime = dateTime.plusMinutes(56);
    //Unpark Scooter
    unParkUser(LOCAL, VehicleType.TWO_WHEELER, 2, dateTime);
    assertFee(LOCAL, VehicleType.TWO_WHEELER, 2,10d);
    dateTime = dateTime.plusMinutes(19);
    //Park MotorCycle
    parkUser(LOCAL, VehicleType.TWO_WHEELER, "MotorCycle", 2, dateTime);
    dateTime = dateTime.plusMinutes(105);
    //Unpark MotorCycle
    unParkUser(LOCAL, VehicleType.TWO_WHEELER, 1, dateTime);
    assertFee(LOCAL, VehicleType.TWO_WHEELER, 1,40d);
    DataStore.initializeDataStore();
  }

  @Test
  public void case_two()
  {
    DataStore.initializeDataStore();
    LocalDateTime dateTime = LocalDateTime.now();

    //Motorcycle parked for 3 hours and 30 mins. Fees: 40
    parkUser(MALL, VehicleType.TWO_WHEELER, "MotorCycle", 1, dateTime);
    unParkUser(MALL, VehicleType.TWO_WHEELER, 1, dateTime.plusMinutes(3*60+30));
    assertFee(MALL, VehicleType.TWO_WHEELER, 1,40d);

    //Car parked for 6 hours and 1 min. Fees: 140
    parkUser(MALL, VehicleType.LMV, "Car", 2, dateTime);
    unParkUser(MALL, VehicleType.LMV, 2, dateTime.plusMinutes(6*60+1));
    assertFee(MALL, VehicleType.LMV, 2,140d);

    //Truck parked for 1 hour and 59 mins. Fees: 100
    parkUser(MALL, VehicleType.HMV, "Truck", 1, dateTime);
    unParkUser(MALL, VehicleType.HMV, 1, dateTime.plusMinutes(60 +59));
    assertFee(MALL, VehicleType.HMV, 1,100d);

    DataStore.initializeDataStore();
  }
  @Test
  public void case_three()
  {
    DataStore.initializeDataStore();
    LocalDateTime dateTime = LocalDateTime.now();

    //Motorcycle parked for 3 hours and 40 mins. Fees: 30
    parkUser(STADIUM, VehicleType.TWO_WHEELER, "MotorCycle", 1, dateTime);
    unParkUser(STADIUM, VehicleType.TWO_WHEELER, 1, dateTime.plusMinutes(3*60+40));
    assertFee(STADIUM, VehicleType.TWO_WHEELER, 1,30d);

    //Motorcycle parked for 14 hours and 59 mins. Fees: 390.
    parkUser(STADIUM, VehicleType.TWO_WHEELER, "MotorCycle", 1, dateTime);
    unParkUser(STADIUM, VehicleType.TWO_WHEELER, 1, dateTime.plusMinutes(14*60+59));
    assertFee(STADIUM, VehicleType.TWO_WHEELER, 1,390d);

    //Electric SUV parked for 11 hours and 30 mins. Fees: 180.
    parkUser(STADIUM, VehicleType.LMV, "Suv", 1, dateTime);
    unParkUser(STADIUM, VehicleType.LMV, 1, dateTime.plusMinutes(11*60+30));
    assertFee(STADIUM, VehicleType.LMV, 1,180d);

    //SUV parked for 13 hours and 5 mins. Fees: 580.
    parkUser(STADIUM, VehicleType.LMV, "Suv", 1, dateTime);
    unParkUser(STADIUM, VehicleType.LMV, 1, dateTime.plusMinutes(13*60+5));
    assertFee(STADIUM, VehicleType.LMV, 1,580d);
    DataStore.initializeDataStore();
  }

  @Test
  public void case_four()
  {
    DataStore.initializeDataStore();
    LocalDateTime dateTime = LocalDateTime.now();

    //Motorcycle parked for 55 mins. Fees: 0
    parkUser(AIRPORT, VehicleType.TWO_WHEELER, "MotorCycle", 1, dateTime);
    unParkUser(AIRPORT, VehicleType.TWO_WHEELER, 1, dateTime.plusMinutes(55));
    assertFee(AIRPORT, VehicleType.TWO_WHEELER, 1,0d);

    //Motorcycle parked for 14 hours and 59 mins. Fees: 60
    parkUser(AIRPORT, VehicleType.TWO_WHEELER, "MotorCycle", 1, dateTime);
    unParkUser(AIRPORT, VehicleType.TWO_WHEELER, 1, dateTime.plusMinutes(14*60+59));
    assertFee(AIRPORT, VehicleType.TWO_WHEELER, 1,60d);

    //Motorcycle parked for 1 day and 12 hours. Fees: 160
    parkUser(AIRPORT, VehicleType.TWO_WHEELER, "MotorCycle", 1, dateTime);
    unParkUser(AIRPORT, VehicleType.TWO_WHEELER, 1, dateTime.plusMinutes(36*60));
    assertFee(AIRPORT, VehicleType.TWO_WHEELER, 1,160d);

    //Car parked for 50 mins. Fees: 60
    parkUser(AIRPORT, VehicleType.LMV, "Car", 1, dateTime);
    unParkUser(AIRPORT, VehicleType.LMV, 1, dateTime.plusMinutes(50));
    assertFee(AIRPORT, VehicleType.LMV, 1,60d);

    //SUV parked for 23 hours and 59 mins. Fees: 80
    parkUser(AIRPORT, VehicleType.LMV, "SUV", 1, dateTime);
    unParkUser(AIRPORT, VehicleType.LMV, 1, dateTime.plusMinutes(23*60+59));
    assertFee(AIRPORT, VehicleType.LMV, 1,80d);

    //Car parked for 3 days and 1 hour. Fees: 400
    parkUser(AIRPORT, VehicleType.LMV, "Car", 1, dateTime);
    unParkUser(AIRPORT, VehicleType.LMV, 1, dateTime.plusMinutes(3*24*60+60));
    assertFee(AIRPORT, VehicleType.LMV, 1,400d);
    DataStore.initializeDataStore();
  }

  private void assertFee(String locationName,VehicleType vehicleType,int slotNo,double expected)
  {
    Location location=locationService.getLocation(locationName);
    Optional<ParkingLotUser> parkingLotUserOptional
        = location.getParkingLotUsers().stream().filter(x->x.isHasExitedParking()
    && vehicleType == x.getVehicleType() && slotNo == x.getSlotNo()).reduce((first,second)-> second);
    Assertions.assertTrue(parkingLotUserOptional.isPresent());
    Assertions.assertEquals(expected,parkingLotUserOptional.get().getTotalFee());
  }
}
