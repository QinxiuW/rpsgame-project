package com.qinxiu.rpsgameproject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class ServerTest {

  @Test
  public void test(){
    Map<String, String> params = new HashMap<>();
    params.put("code","123");
    params.put("message","blabla");

    ObjectMapper mapper =  new ObjectMapper();
    String jsonString = null;
    try {
      jsonString = mapper.writeValueAsString(params);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    System.out.print(jsonString);
  }
}
