package com.sahaj.parkinglot.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SlotFeeInfo implements Serializable, Comparable<SlotFeeInfo> {
  private long start;
  private long end;
  private double fee;

  public long getRangeStartAsMinutes() {
    return start * 60;
  }

  public long getRangeEndAsMinutes() {
    if (end == -1) {
      return Long.MAX_VALUE;
    }
    return end * 60;
  }

  @Override
  public int compareTo(SlotFeeInfo that) {
    if (this.end == that.end && this.start == that.start)
      return 0;
    if (this.start < that.start && this.end < that.end)
      return -1;
    if ((this.end == -1) || this.start > that.start && (this.end > that.end))
      return 1;
    return -1;
  }
}
