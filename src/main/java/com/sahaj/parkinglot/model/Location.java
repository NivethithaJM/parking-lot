package com.sahaj.parkinglot.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Location implements Serializable {
  String name;
  HashMap<VehicleType, VehicleTypeParkingSlotInfo> vehicleTypeVsParkingSlotInfo;
  List<ParkingLotUser> parkingLotUsers;

  public Location() {
    parkingLotUsers = new ArrayList<>();
    vehicleTypeVsParkingSlotInfo = new HashMap<>();
  }
}
