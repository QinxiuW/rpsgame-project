package com.qinxiu.rpsgameproject.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

public enum ResponseStatus {

  OK(200000, "Request successful"),
  INVALID_PARAMS(400001, "Invalid params"),
  INVALID_CHOICE(400002, "Invalid choice"),
  INVALID_PLAYER(400003, "Player's name is too long(max. 20 chars). ");

  private final Integer code;
  private final String message;

  ResponseStatus(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  /**
   * Get exception info by code.
   *
   * @param code {@code int}
   * @return {@code String}
   */
  public static String getMessage(int code) {
    for (ResponseStatus status : values()) {
      if (status.getCode().equals(code)) {
        return status.getMessage();
      }
    }
    return null;
  }

  @Override
  // TODO: need be jason format
  public String toString() {
    Map<String, String> params = new HashMap<>();
    params.put("code",this.code.toString());
    params.put("message",this.message);
    ObjectMapper mapper =  new ObjectMapper();
    String jsonString = null;
    try {
      jsonString = mapper.writeValueAsString(params);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return jsonString;
  }
}
