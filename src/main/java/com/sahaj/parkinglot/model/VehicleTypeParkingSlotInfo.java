package com.sahaj.parkinglot.model;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class VehicleTypeParkingSlotInfo implements Serializable {
  int totalSlots;
  int availableSlots;
  FeeModel feeModel;
}
