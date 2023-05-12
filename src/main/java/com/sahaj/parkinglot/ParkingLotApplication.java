package com.sahaj.parkinglot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sahaj.parkinglot.model.DataStore;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
public class ParkingLotApplication {



  public static void main(String[] args) {
    SpringApplication.run(ParkingLotApplication.class, args);
  }

}
