package com.qinxiu.rpsgameproject.tests.common;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

import com.qinxiu.rpsgameproject.common.HttpUtils;
import com.qinxiu.rpsgameproject.common.ResponseStatus;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HttpUtilsTest {


  @Test
  public void validateParamsMapTest() {

    // true case
    Map<String, String> tempMap = new HashMap<>();
    tempMap.put(HttpUtils.PROMPT_PLAYER, "name");
    var result_1 = HttpUtils.validateParamsMap(tempMap, HttpUtils.PROMPT_PLAYER);
    // false case
    tempMap = new HashMap<>();
    tempMap.put("xxx", "name");
    tempMap.put("test", "name");
    var result_2 = HttpUtils.validateParamsMap(tempMap, HttpUtils.PROMPT_PLAYER);

    // Asserts
    Assert.assertTrue(result_1);
    Assert.assertFalse(result_2);
  }


  @Test
  @SuppressWarnings("unchecked")
  public void validateParamsMapMockTest() {

    // Arrange
    Map<String, String> mockMap = Mockito.mock(Map.class);
    Mockito.when(mockMap.containsKey(anyString())).thenReturn(true);
    Mockito.when(mockMap.get(anyString())).thenReturn("value");
    Mockito.when(mockMap.size()).thenReturn(1);

    // Act
    var result = HttpUtils.validateParamsMap(mockMap, anyString());

    // Asserts
    Mockito.verify(mockMap, times(1)).containsKey(anyString());
    Mockito.verify(mockMap, times(1)).get(anyString());
    Mockito.verify(mockMap, times(1)).size();
    Assert.assertTrue(result);
  }


  @Test
  public void setResponseMockTest() throws IOException {

    // Arrange
    HttpExchange mockHttpExchange = Mockito.mock(HttpExchange.class);
    OutputStream mockOutputStream = Mockito.mock(OutputStream.class);

    AtomicBoolean sendHeaderExecuted = new AtomicBoolean(false);
    Mockito.doAnswer(
        invocationOnMock -> {
          sendHeaderExecuted.set(true);
          return null;
        }
    ).when(mockHttpExchange).sendResponseHeaders(anyInt(), anyLong());
    Mockito.when(mockHttpExchange.getResponseBody()).thenReturn(mockOutputStream);

    AtomicBoolean responseBodyCloseExecuted = new AtomicBoolean(false);
    Mockito.doAnswer(
        invocationOnMock -> {
          responseBodyCloseExecuted.set(true);
          return null;
        }
    ).when(mockOutputStream).close();

    // Act
    var result = HttpUtils
        .setResponse(mockHttpExchange, HttpURLConnection.HTTP_OK, ResponseStatus.OK);

    // Asserts
    Assert.assertEquals(result, ResponseStatus.OK.toString());
    Assert.assertTrue(sendHeaderExecuted.get());
    Assert.assertTrue(responseBodyCloseExecuted.get());
  }
}
