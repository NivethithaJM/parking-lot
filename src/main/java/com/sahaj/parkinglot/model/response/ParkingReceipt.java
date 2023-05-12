package com.sahaj.parkinglot.model.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParkingReceipt {
  private int id;
  private int slotNo;
  private LocalDateTime entryDate;
  private LocalDateTime exitDate;
  double totalFee;

  public void prettyPrint()
  {
    System.out.println("Parking Receipt");
    System.out.format("%30s | R-%05d\n", "Receipt Number", this.getId());
    System.out.format("%30s | %05d\n", "Spot Number", this.getSlotNo());
    System.out.format("%30s | %s\n", "Entry Date Time", this.getEntryDate());
    System.out.format("%30s | %s\n", "Exit Date Time", this.getExitDate());
    System.out.format("%30s | %s\n", "Fees", this.getTotalFee());
  }
}
