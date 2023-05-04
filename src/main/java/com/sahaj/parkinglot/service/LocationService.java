package com.sahaj.parkinglot.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.sahaj.parkinglot.model.DataStore;
import com.sahaj.parkinglot.model.Location;
import com.sahaj.parkinglot.model.ParkingLotUser;
import com.sahaj.parkinglot.model.VehicleType;
import com.sahaj.parkinglot.model.VehicleTypeParkingSlotInfo;

public class LocationService {

  private static LocationService locationService;
  FeeCalculationService feeCalculationService = FeeCalculationService.getInstance();

  private LocationService() {
  }

  public static LocationService getInstance() {
    if (locationService == null) {
      locationService = new LocationService();
    }
    return locationService;
  }

  /**
   * get location
   * @param locationName locationName
   * @return Location object
   */
  public Location getLocation(String locationName) {
    if (!DataStore.getInstance().getNameVsLocationMap().containsKey(locationName)) {
      throw new RuntimeException("Required location not found");
    }
    return DataStore.getInstance().getNameVsLocationMap().get(locationName);
  }

  /**
   * add parking lot user to the selected location
   * @param locationName locationName
   * @param user ParkingLotUser
   */
  public void addParkingLotUser(String locationName, ParkingLotUser user) {
    Location location = this.getLocation(locationName);
    VehicleTypeParkingSlotInfo parkingSlotInfo = location.getVehicleTypeVsParkingSlotInfo().get(user.getVehicleType());
    checkIfCanAccommodateVehicle(parkingSlotInfo);
    checkIfRequestedSlotAvailable(location, user.getSlotNo());
    location.getParkingLotUsers().add(user);
    parkingSlotInfo.setAvailableSlots(parkingSlotInfo.getAvailableSlots() - 1);
    this.generateReceipt(user);
    DataStore.saveDataStore();
  }

  /**
   * remove a parking lot user from selected location
   * @param locationName locationName
   * @param vehicleType vehicleType
   * @param slotNumber slotNumber
   * @param exitDate exitDate
   */
  public void removeParkingLotUser(String locationName, VehicleType vehicleType, int slotNumber,
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
      this.generateReceipt(user);
      return;
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





  private void generateReceipt(ParkingLotUser user) {
    if (user.isHasExitedParking()) {
      System.out.println("Parking Receipt");
      System.out.format("%30s | R-%05d\n", "Receipt Number", user.getId());
      System.out.format("%30s | %05d\n", "Spot Number", user.getSlotNo());
      System.out.format("%30s | %s\n", "Entry Date Time", user.getEntryDate());
      System.out.format("%30s | %s\n", "Exit Date Time", user.getExitDate());
      System.out.format("%30s | %s\n", "Fees", user.getTotalFee());
    } else {
      System.out.println("Parking Ticket");
      System.out.format("%30s | %05d\n", "Ticket Number", user.getId());
      System.out.format("%30s | %05d\n", "Spot Number", user.getSlotNo());
      System.out.format("%30s | %s\n", "Entry Date Time", user.getEntryDate());
    }

  }
}
