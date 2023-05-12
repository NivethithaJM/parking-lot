package com.sahaj.parkinglot.serviceimpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahaj.parkinglot.model.DataStore;
import com.sahaj.parkinglot.model.Location;
import com.sahaj.parkinglot.model.ParkingLotUser;
import com.sahaj.parkinglot.constants.VehicleType;
import com.sahaj.parkinglot.model.VehicleTypeParkingSlotInfo;
import com.sahaj.parkinglot.model.response.ParkingReceipt;
import com.sahaj.parkinglot.model.response.ParkingTicket;
import com.sahaj.parkinglot.service.FeeCalculationService;
import com.sahaj.parkinglot.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {
  @Autowired
  FeeCalculationService feeCalculationService;

  @Override
  public Location getLocation(String locationName) {
    if (!DataStore.getInstance().getNameVsLocationMap().containsKey(locationName)) {
      throw new RuntimeException("Required location not found");
    }
    return DataStore.getInstance().getNameVsLocationMap().get(locationName);
  }

  @Override
  public ParkingTicket addParkingLotUser(String locationName, ParkingLotUser user) {
    Location location = this.getLocation(locationName);
    VehicleTypeParkingSlotInfo parkingSlotInfo = location.getVehicleTypeVsParkingSlotInfo().get(user.getVehicleType());
    checkIfCanAccommodateVehicle(parkingSlotInfo);
    checkIfRequestedSlotAvailable(location, user.getSlotNo());
    location.getParkingLotUsers().add(user);
    parkingSlotInfo.setAvailableSlots(parkingSlotInfo.getAvailableSlots() - 1);
    ParkingTicket ticket =
        ParkingTicket.builder().id(user.getId()).slotNo(user.getSlotNo()).entryDate(user.getEntryDate()).build();
    ticket.prettyPrint();
    DataStore.saveDataStore();
    return ticket;
  }

  @Override
  public ParkingReceipt removeParkingLotUser(String locationName, VehicleType vehicleType, int slotNumber,
      LocalDateTime exitDate) {
    Location location = this.getLocation(locationName);
    Optional<ParkingLotUser> userOptional =
        location.getParkingLotUsers().stream().filter(x -> slotNumber == x.getSlotNo() && !x.isHasExitedParking())
            .findFirst();
    if (userOptional.isPresent()) {
      ParkingLotUser user = userOptional.get();
      VehicleTypeParkingSlotInfo parkingSlotInfo = location.getVehicleTypeVsParkingSlotInfo().get(vehicleType);
      parkingSlotInfo.setAvailableSlots(parkingSlotInfo.getAvailableSlots() + 1);
      user.setHasExitedParking(true);
      user.setExitDate(exitDate);
      user.setTotalFee(feeCalculationService.calculateFee(user, parkingSlotInfo.getFeeModel()));
      DataStore.saveDataStore();
      ParkingReceipt receipt =
          ParkingReceipt.builder().id(user.getId()).slotNo(user.getSlotNo()).entryDate(user.getEntryDate())
              .exitDate(user.getExitDate()).totalFee(user.getTotalFee()).build();
      receipt.prettyPrint();
      return receipt;
    }
    throw new RuntimeException("Slot is vacant!!");
  }

  private void checkIfCanAccommodateVehicle(VehicleTypeParkingSlotInfo parkingSlotInfo) {
    if (parkingSlotInfo.getAvailableSlots() < 1) {
      throw new RuntimeException("Slot not available");
    }
  }

  private void checkIfRequestedSlotAvailable(Location location, int slotNo) {
    Optional<ParkingLotUser> user =
        location.getParkingLotUsers().stream().filter(x -> slotNo == x.getSlotNo() && !x.isHasExitedParking())
            .findFirst();
    user.ifPresent(x -> {
      throw new RuntimeException("Slot not available");
    });
  }
}
