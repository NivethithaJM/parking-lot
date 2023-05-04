package com.sahaj.parkinglot.service;

import java.time.LocalDateTime;

import com.sahaj.parkinglot.model.Location;
import com.sahaj.parkinglot.model.ParkingLotUser;
import com.sahaj.parkinglot.model.VehicleType;

public interface LocationService {
  /**
   * get location
   *
   * @param locationName locationName
   * @return Location object
   */
  Location getLocation(String locationName);

  /**
   * add parking lot user to the selected location
   *
   * @param locationName locationName
   * @param user         ParkingLotUser
   */
  void addParkingLotUser(String locationName, ParkingLotUser user);

  /**
   * remove a parking lot user from selected location
   *
   * @param locationName locationName
   * @param vehicleType  vehicleType
   * @param slotNumber   slotNumber
   * @param exitDate     exitDate
   */
  void removeParkingLotUser(String locationName, VehicleType vehicleType, int slotNumber, LocalDateTime exitDate);
}
