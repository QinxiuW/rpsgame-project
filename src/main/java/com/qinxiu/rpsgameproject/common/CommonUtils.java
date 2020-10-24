package com.qinxiu.rpsgameproject.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommonUtils {

  /**
   * Output the text to a file with a specific path name.
   *
   * @param pathName {@code String}
   * @param data     {@code String}
   */
  public static void outputFile(String pathName, String data) {
    try {
      File file = new File(pathName);
      //if file doesnt exists, then create it
      if (!file.exists()) {
        if (file.createNewFile()) {
          System.out.println("File create successful");
        }
      }

      FileWriter fileWriter = new FileWriter(file.getName());
      BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
      bufferWriter.write(data);
      bufferWriter.close();
      System.out.println("Output file action Done");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Get input content and verify until it's a number.
   *
   * @return {@code Int}
   */
  public static int getNumberInput() {
    Scanner input = new Scanner(System.in);
    while (!(input.hasNextInt())) {
      System.out.println("Your input is invalid, please try again");
      input.next();
    }
    return input.nextInt();
  }

  /**
   * Get input content, verify until it's a number with a max range.
   *
   * @param maxNumber {@code Int}
   * @return {@code Int}
   */
  public static int getNumberInputWithLimit(int maxNumber) {
    int number = getNumberInput();
    while (number > maxNumber || number < 0) {
      System.out.println("Your input is out of range, please try again");
      number = getNumberInput();
    }
    return number;
  }

  /**
   * Given an input with the POST params format and store the data inside a MAP structure.
   *
   * @param query {@code String}
   * @return {@code Map<String,String>}
   */
  public static Map<String, String> getQueryMap(String query) {
    String[] params = query.split("&");
    Map<String, String> map = new HashMap<>();

    for (String param : params) {
      String name = param.split("=")[0];
      String value = param.split("=")[1];
      map.put(name, value);
    }
    return map;
  }

}
