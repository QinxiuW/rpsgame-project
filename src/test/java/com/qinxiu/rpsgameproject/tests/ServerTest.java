package com.qinxiu.rpsgameproject.tests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

import com.qinxiu.rpsgameproject.common.HttpUtils;
import com.qinxiu.rpsgameproject.common.ResponseStatus;
import com.qinxiu.rpsgameproject.server.MyHttpHandler;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ServerTest {


  @Test
  public void playerHandlerSuccessMockTest()
      throws IOException, URISyntaxException, InterruptedException {

    // Arrange
    String address = "http://localhost:8081/v1/playerConnect?player=Pep";

    // Act
    var handler = handlerTestCommonProcess(address, HttpUtils.PROMPT_PLAYER, true);

    // Asserts
    Assert.assertEquals(handler.getResponse(), ResponseStatus.OK.toString());
  }

  @Test
  public void playerHandlerInvalidPlayerMockTest()
      throws InterruptedException, IOException, URISyntaxException {

    // Arrange
    String player = "sadsadasdasdsadasdasdasdsadsadsadsadsadsadsadsadsdasdsadsadsadasdasdasdsadasdasdasd";
    String address = "http://localhost:8081/v1/playerConnect?player=" + player;

    // Act
    var handler = handlerTestCommonProcess(address, HttpUtils.PROMPT_PLAYER, false);

    // Asserts
    Assert.assertEquals(handler.getResponse(), ResponseStatus.INVALID_PLAYER.toString());
  }

  @Test
  public void choiceHandlerSuccessMockTest()
      throws InterruptedException, IOException, URISyntaxException {

    // Arrange
    String address = "http://localhost:8081/v1/choiceConnect?choice=Scissors";

    // Act
    var handler = handlerTestCommonProcess(address, HttpUtils.PROMPT_CHOICE, true);

    // Asserts
    Assert.assertEquals(handler.getResponse(), ResponseStatus.OK.toString());
  }

  @Test
  public void choiceHandlerInvalidChoiceMockTest()
      throws InterruptedException, IOException, URISyntaxException {
    // Arrange
    String address = "http://localhost:8081/v1/choiceConnect?choice=xxxxx";

    // Act
    var handler = handlerTestCommonProcess(address, HttpUtils.PROMPT_CHOICE, false);

    // Asserts
    Assert.assertEquals(handler.getResponse(), ResponseStatus.INVALID_CHOICE.toString());
  }

  @Test
  public void badRequestMockTest() throws InterruptedException, IOException, URISyntaxException {
    // Arrange
    String address = "http://localhost:8081/v1/choiceConnect?";

    // Act
    var handler = handlerTestCommonProcess(address, HttpUtils.PROMPT_CHOICE, false);

    // Asserts
    Assert.assertEquals(handler.getResponse(), ResponseStatus.INVALID_PARAMS.toString());
  }

  @SuppressWarnings("unchecked")
  private MyHttpHandler handlerTestCommonProcess(String address, String paramPrompt,
      boolean success)
      throws URISyntaxException, InterruptedException, IOException {
    HttpExchange mockHttpExchange = Mockito.mock(HttpExchange.class);
    Headers mockHeaders = Mockito.mock(Headers.class);
    Mockito.when(mockHttpExchange.getRequestMethod()).thenReturn("POST");
    Mockito.when(mockHttpExchange.getResponseHeaders()).thenReturn(mockHeaders);
    AtomicBoolean headerSetExecuted = new AtomicBoolean(false);
    Mockito.doAnswer(
        invocationOnMock -> {
          headerSetExecuted.set(true);
          return null;
        }
    ).when(mockHeaders).set(anyString(), anyString());
    BlockingQueue<String> mockQueue = Mockito.mock(LinkedBlockingQueue.class);
    URI mockURI = new URI(address);
    Mockito.when(mockHttpExchange.getRequestURI()).thenReturn(mockURI);
    AtomicBoolean queuePutExecuted = new AtomicBoolean(false);
    Mockito.doAnswer(
        invocationOnMock -> {
          queuePutExecuted.set(true);
          return null;
        }
    ).when(mockQueue).put(anyString());
    Mockito.when(mockHttpExchange.getResponseBody()).thenReturn(Mockito.mock(OutputStream.class));

    // Act
    MyHttpHandler handler = new MyHttpHandler(mockQueue, paramPrompt);
    handler.handle(mockHttpExchange);

    // Asserts
    Assertions.assertTrue(headerSetExecuted.get());
    if (success) {
      Assertions.assertTrue(queuePutExecuted.get());
    }
    Mockito.verify(mockHttpExchange, times(1)).getRequestURI();
    return handler;
  }
}

