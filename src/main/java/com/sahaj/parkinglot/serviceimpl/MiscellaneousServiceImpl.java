package com.sahaj.parkinglot.serviceimpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahaj.parkinglot.model.DataStore;
import com.sahaj.parkinglot.model.Location;
import com.sahaj.parkinglot.service.LocationService;
import com.sahaj.parkinglot.service.MiscellaneousService;

@Service
public class MiscellaneousServiceImpl implements MiscellaneousService {

  @Autowired
  LocationService locationService;

  private static final String formatString = "%15s | %15s | %15s | %15s\n";
  private static final String dottedString = "---------------";

  @Override
  public void displayParkingStatus() {
    System.out.format(formatString, "Location Name", "Vehicle type", "Total slot", "Available slot");
    System.out.format(formatString, dottedString, dottedString, dottedString, dottedString);
    DataStore.getInstance().getNameVsLocationMap().keySet().forEach(this::display);
  }

  private void display(String locationName) {
    Location location = locationService.getLocation(locationName);
    location.getVehicleTypeVsParkingSlotInfo().forEach(
        (key, value) -> System.out.format(formatString, locationName, key.name(), value.getTotalSlots(),
            value.getAvailableSlots()));

    System.out.format(formatString, dottedString, dottedString, dottedString, dottedString);
  }


}
