package com.sahaj.parkinglot.serviceimpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahaj.parkinglot.model.ParkingLotUser;
import com.sahaj.parkinglot.constants.VehicleType;
import com.sahaj.parkinglot.model.response.ParkingReceipt;
import com.sahaj.parkinglot.model.response.ParkingTicket;
import com.sahaj.parkinglot.service.LocationService;
import com.sahaj.parkinglot.service.ParkingService;

@Service
public class ParkingServiceImpl implements ParkingService {

  @Autowired
  LocationService locationService;

  @Override
  public ParkingTicket enterParking(String locationName, VehicleType vehicleType, String vehicleName, int slotNumber,
      LocalDateTime entryDate) {
    ParkingLotUser user = new ParkingLotUser();
    user.setVehicleType(vehicleType);
    user.setName(vehicleName);
    user.setSlotNo(slotNumber);
    user.setEntryDate(entryDate);
    return locationService.addParkingLotUser(locationName, user);
  }

  @Override
  public ParkingReceipt exitParking(String locationName, VehicleType vehicleType, int slotNumber,
      LocalDateTime exitDate) {
    return locationService.removeParkingLotUser(locationName, vehicleType, slotNumber, exitDate);
  }

}
