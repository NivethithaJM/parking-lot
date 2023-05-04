package com.sahaj.parkinglot.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Vehicle {
    VehicleType vehicleType;
    String name;
    FeeModel feeModel;
}
