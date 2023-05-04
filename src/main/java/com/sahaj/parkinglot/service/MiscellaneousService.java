package com.sahaj.parkinglot.service;


import com.sahaj.parkinglot.model.DataStore;
import com.sahaj.parkinglot.model.Location;

public class MiscellaneousService {


  private static final String formatString = "%15s | %15s | %15s | %15s\n";
  private static final String dottedString = "---------------";

  public void displayParkingStatus() {
    System.out.format(formatString, "Location Name", "Vehicle type", "Total slot", "Available slot");
    System.out.format(formatString, dottedString, dottedString, dottedString, dottedString);
    DataStore.getInstance().getNameVsLocationMap().keySet().forEach(this::display);
  }

  private void display(String locationName) {
    Location location = LocationService.getInstance().getLocation(locationName);
    location.getVehicleTypeVsParkingSlotInfo().forEach(
        (key, value) -> System.out.format(formatString, locationName, key.name(), value.getTotalSlots(),
            value.getAvailableSlots()));

    System.out.format(formatString, dottedString, dottedString, dottedString, dottedString);
  }


}
