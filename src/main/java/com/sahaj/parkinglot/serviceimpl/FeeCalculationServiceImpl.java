package com.sahaj.parkinglot.serviceimpl;

import java.time.Duration;
import java.util.Iterator;

import org.springframework.stereotype.Service;

import com.sahaj.parkinglot.model.FeeModel;
import com.sahaj.parkinglot.model.ParkingLotUser;
import com.sahaj.parkinglot.model.SlotFeeInfo;
import com.sahaj.parkinglot.service.FeeCalculationService;

@Service
public class FeeCalculationServiceImpl implements FeeCalculationService {

  @Override
  public double calculateFee(ParkingLotUser user, FeeModel feeModel) {
    switch (feeModel.getType()) {
      case FLAT_RATE:
        return this.calculateFeeForFlatRate(user, feeModel);
      case FLAT_RATE_FOR_DAY:
        return this.calculateFeeForFlatRateForDay(user, feeModel);
      case FLAT_RATE_FOR_HOUR:
        return this.calculateFeeForFlatRateForHour(user, feeModel);
    }
    return this.calculateFeeForFlatRate(user, feeModel);
  }

  private double calculateFeeForFlatRate(ParkingLotUser user, FeeModel feeModel) {
    Duration duration = Duration.between(user.getEntryDate(), user.getExitDate());
    long durationMinutes = duration.toMinutes();
    long hour = duration.toHours();
    if (durationMinutes - hour * 60 > 0) {
      hour += 1;
    }
    double fee = feeModel.getFeeInfoList().first().getFee();
    return hour * fee;
  }

  private double calculateFeeForFlatRateForDay(ParkingLotUser user, FeeModel feeModel) {
    Duration duration = Duration.between(user.getEntryDate(), user.getExitDate());
    long durationMinutes = duration.toMinutes();
    Iterator<SlotFeeInfo> iterator = feeModel.getFeeInfoList().iterator();
    while (iterator.hasNext()) {
      SlotFeeInfo slotFeeInfo = iterator.next();
      //lastNode
      if (!iterator.hasNext()) {
        long days = duration.toDays();
        if (durationMinutes - days * 60 * 24 > 0) {
          days += 1;
        }
        return days * slotFeeInfo.getFee();
      } else {
        if (durationMinutes >= slotFeeInfo.getRangeStartAsMinutes()
            && durationMinutes < slotFeeInfo.getRangeEndAsMinutes()) {
          return slotFeeInfo.getFee();
        }
      }
    }
    return 0;
  }

  private double calculateFeeForFlatRateForHour(ParkingLotUser user, FeeModel feeModel) {
    Duration duration = Duration.between(user.getEntryDate(), user.getExitDate());
    long durationMinutes = duration.toMinutes();

    double fee = 0;
    Iterator<SlotFeeInfo> iterator = feeModel.getFeeInfoList().iterator();
    while (iterator.hasNext()) {
      SlotFeeInfo slotFeeInfo = iterator.next();
      //lastNode
      if (!iterator.hasNext()) {
        long hour = duration.toHours();
        if (durationMinutes - hour * 60 > 0) {
          hour += 1;
        }
        fee = fee + ((hour - slotFeeInfo.getStart()) * slotFeeInfo.getFee());
      } else {
        if (durationMinutes >= slotFeeInfo.getRangeStartAsMinutes()) {
          fee = fee + slotFeeInfo.getFee();
        } else {
          break;
        }
      }
    }
    return fee;
  }
}
