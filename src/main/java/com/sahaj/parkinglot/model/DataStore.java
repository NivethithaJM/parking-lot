package com.sahaj.parkinglot.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
  private static final String dataStoreFileName = "src/main/resources/data-store.txt";
  private static DataStore dataStore = null;
  private final HashMap<String, Location> nameVsLocationMap;
  private int idGenerator =0;
  public int generateId()
  {
    idGenerator++;
    return idGenerator;
  }
  private DataStore() {
    nameVsLocationMap = new HashMap<>();
  }

  private static DataStore readDataStore() {
    try {
      FileInputStream fi = new FileInputStream(dataStoreFileName);
      ObjectInputStream oi = new ObjectInputStream(fi);
      return (DataStore) oi.readObject();
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
      FileOutputStream fileOut = new FileOutputStream(dataStoreFileName);
      ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
      objectOut.writeObject(DataStore.getInstance());
      objectOut.close();
    } catch (Exception ignored) {
    }
  }
  public static void initializeDataStore() {
    JSONParser jsonParser = new JSONParser();
    File file = new File("src/main/resources/initialize-application.json");
    FileReader fileReader = null;
    try {
      fileReader = new FileReader(file);
      JSONObject unParsedDataStore = (JSONObject) jsonParser.parse(fileReader);
      DataStore.dataStore = (new ObjectMapper()).convertValue(unParsedDataStore,DataStore.class);
      DataStore.saveDataStore();
    } catch (Exception ignored)
    {
      ignored.printStackTrace();
    }
  }

}
