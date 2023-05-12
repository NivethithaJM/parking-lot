package com.sahaj.parkinglot.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.sahaj.parkinglot.constants.VehicleType;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ParkingLotUser implements Serializable {
  private static int idGenerator = 0;
  int id;
  String name;
  LocalDateTime entryDate;
  LocalDateTime exitDate;
  int slotNo;
  boolean hasExitedParking;
  VehicleType vehicleType;
  double totalFee;
}
