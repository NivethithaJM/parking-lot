package com.sahaj.parkinglot.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
public class ConfigProps {
  public static String dataStoreFileName;

  @Value("${sahaj.data-source.file}")
  public void setDataStoreFileName(final String dataStoreFileName) {
    ConfigProps.dataStoreFileName = dataStoreFileName;
  }

}
