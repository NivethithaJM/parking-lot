package com.sahaj.parkinglot.service;

import com.sahaj.parkinglot.model.FeeModel;
import com.sahaj.parkinglot.model.ParkingLotUser;

public interface FeeCalculationService {
  /**
   * Calculates fee for parking lot user using given fee model
   *
   * @param user     ParkingLotUser
   * @param feeModel FeeModel
   * @return total fee
   */
  double calculateFee(ParkingLotUser user, FeeModel feeModel);
}
