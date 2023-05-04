package com.sahaj.parkinglot.model;

import java.io.Serializable;
import java.util.SortedSet;

import com.sahaj.parkinglot.constants.FeeModelType;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FeeModel implements Serializable {
  FeeModelType type;
  SortedSet<SlotFeeInfo> feeInfoList;
}
