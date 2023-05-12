package com.sahaj.parkinglot.model.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParkingTicket {
  private int id;
  private int slotNo;
  private LocalDateTime entryDate;

  public void prettyPrint(){
    System.out.println("Parking Ticket");
    System.out.format("%30s | %05d\n", "Ticket Number", this.getId());
    System.out.format("%30s | %05d\n", "Spot Number", this.getSlotNo());
    System.out.format("%30s | %s\n", "Entry Date Time", this.getEntryDate());
  }
}
