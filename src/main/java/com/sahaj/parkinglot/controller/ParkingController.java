package com.sahaj.parkinglot.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sahaj.parkinglot.constants.VehicleType;
import com.sahaj.parkinglot.model.DataStore;
import com.sahaj.parkinglot.model.response.ParkingReceipt;
import com.sahaj.parkinglot.model.response.ParkingTicket;
import com.sahaj.parkinglot.service.ParkingService;

import io.swagger.annotations.ApiOperation;
import lombok.Getter;

@RestController
@RequestMapping(value = "/api/parking-lot")
public class ParkingController {

  @Autowired
  ParkingService parkingService;

  @PostMapping(value = {"/init"})
  @ApiOperation("Initialize datastore")
  public boolean init() {
    DataStore.initializeDataStore();
    return true;
  }

  @PostMapping(value = "/park", produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation("Park an Vehicle")
  public ParkingTicket parkVehicle(@RequestBody ParkVehicleRequest request) {
    return parkingService.enterParking(request.getLocationName(), request.getVehicleType(), request.getVehicleName(),
        request.getSlotNumber(), request.getEntryDate());
  }

  @PostMapping(value = "/un-park", produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation("Un-park an Vehicle")
  public ParkingReceipt unParkVehicle(@RequestBody UnParkVehicleRequest request) {
    return parkingService.exitParking(request.getLocationName(), request.getVehicleType(), request.getSlotNo(),
        request.getExitDate());
  }

  @Getter
  private static class ParkVehicleRequest {
    private String locationName;
    private VehicleType vehicleType;
    private String vehicleName;
    private int slotNumber;
    private LocalDateTime entryDate;
  }


  @Getter
  private static class UnParkVehicleRequest {
    private String locationName;
    private VehicleType vehicleType;
    private int slotNo;
    private LocalDateTime exitDate;
  }
}
