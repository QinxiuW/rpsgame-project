package com.qinxiu.rpsgameproject.server;

import com.qinxiu.rpsgameproject.Player;
import com.qinxiu.rpsgameproject.common.Choices;
import com.qinxiu.rpsgameproject.common.CommonUtils;
import com.qinxiu.rpsgameproject.common.HttpUtils;
import com.qinxiu.rpsgameproject.common.ResponseStatus;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

public class MyHttpHandler implements HttpHandler {

  private BlockingQueue<String> queue;

  private HttpExchange httpExchange;

  private String paramPrompt;

  private String response;

  /**
   * httpHandler constructor.
   *
   * @param queue       {@link BlockingQueue} blocking queue.
   * @param paramPrompt {@code String} prompt for http query params.
   */
  public MyHttpHandler(BlockingQueue<String> queue, String paramPrompt) {
    this.queue = queue;
    this.paramPrompt = paramPrompt;
    this.response = "";
  }

  public String getResponse() {
    return this.response;
  }

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {
    this.httpExchange = httpExchange;
    String requestMethod = httpExchange.getRequestMethod();
    if (requestMethod.equalsIgnoreCase("POST")) {
      //Set the encoding format of the server response,
      // otherwise the received on the client may be garbled
      Headers responseHeaders = httpExchange.getResponseHeaders();
      responseHeaders.set("Content-Type", "text/html;charset=utf-8");

      String query = httpExchange.getRequestURI().getQuery();
      // check if query is empty
      if (query != null && !query.isEmpty()) {
        var paramsMap = CommonUtils.getQueryMap(query);
        // check if map's keys match with the given paramPrompt
        if (HttpUtils.validateParamsMap(paramsMap, this.paramPrompt)) {
          // check the value of paramsMap
          validateParamsValue(paramsMap.get(this.paramPrompt));
        }
      } else {
        this.response = HttpUtils.setResponse(httpExchange, HttpURLConnection.HTTP_BAD_REQUEST,
            ResponseStatus.INVALID_PARAMS);
      }
    }
  }

  private void validateParamsValue(String value) throws IOException {
    if (this.paramPrompt.equals(HttpUtils.PROMPT_CHOICE)) {
      validateChoice(value);
    } else if (this.paramPrompt.equals(HttpUtils.PROMPT_PLAYER)) {
      validatePlayer(value);
    }
  }

  private void validateChoice(String choice) throws IOException {
    if (Arrays.asList(Choices.COMPLETE_CHOICE_LIST).contains(choice)) {
      updateQueue(choice);
      this.response = HttpUtils
          .setResponse(httpExchange, HttpURLConnection.HTTP_OK, ResponseStatus.OK);
    } else {
      this.response = HttpUtils
          .setResponse(httpExchange, HttpURLConnection.HTTP_OK, ResponseStatus.INVALID_CHOICE);
    }
  }

  private void validatePlayer(String player) throws IOException {
    if (player.length() <= Player.NAME_MAX_LENGTH) {
      updateQueue(player);
      this.response = HttpUtils
          .setResponse(httpExchange, HttpURLConnection.HTTP_OK, ResponseStatus.OK);
    } else {
      this.response = HttpUtils
          .setResponse(httpExchange, HttpURLConnection.HTTP_OK, ResponseStatus.INVALID_PLAYER);
    }
  }

  private void updateQueue(String content) {
    try {
      this.queue.put(content);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
