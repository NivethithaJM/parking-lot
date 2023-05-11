package com.sahaj.parkinglot.model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DataStore implements Serializable {
  private static final String dataStoreFileName = "src/main/resources/data-store.json";
  private static DataStore dataStore = null;
  private final HashMap<String, Location> nameVsLocationMap;
  private int idGenerator = 0;

  private DataStore() {
    nameVsLocationMap = new HashMap<>();
  }

  private static DataStore readDataStore() {
    JSONParser jsonParser = new JSONParser();
    File file = new File(dataStoreFileName);
    FileReader fileReader;
    try {
      fileReader = new FileReader(file);
      JSONObject unParsedDataStore = (JSONObject) jsonParser.parse(fileReader);
      return (new ObjectMapper()).convertValue(unParsedDataStore, DataStore.class);
    } catch (Exception ignored) {
    }
    return null;
  }

  public static DataStore getInstance() {
    if (dataStore == null) {
      dataStore = readDataStore();
    }
    if (dataStore == null) {
      dataStore = new DataStore();
    }
    return dataStore;
  }

  public static void saveDataStore() {
    try {
      FileWriter fileOut = new FileWriter(dataStoreFileName);
      String y = new ObjectMapper().writeValueAsString(DataStore.getInstance());
      fileOut.write(y);
      fileOut.close();
    } catch (Exception ignored) {
    }
  }

  public static void initializeDataStore() {
    JSONParser jsonParser = new JSONParser();
    File file = new File("src/main/resources/initialize-application.json");
    FileReader fileReader;
    try {
      fileReader = new FileReader(file);
      JSONObject unParsedDataStore = (JSONObject) jsonParser.parse(fileReader);
      DataStore.dataStore = (new ObjectMapper()).convertValue(unParsedDataStore, DataStore.class);
      DataStore.saveDataStore();
    } catch (Exception ignored) {
    }
  }

  public synchronized int generateId() {
    idGenerator++;
    return idGenerator;
  }

}
