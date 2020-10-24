package com.qinxiu.rpsgameproject.common;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpUtils {

  public static final int PORT = 8080;
  public static final String HTTP_PATH_PLAYER = "/v1/playerConnect";
  public static final String HTTP_PATH_CHOICE = "/v1/choiceConnect";

  public static final String PROMPT_CHOICE = "choice";
  public static final String PROMPT_PLAYER = "player";

  /**
   * validate the map content regard to the given paramPrompt.
   *
   * @param paramsMap   {@link Map} map of query's params.
   * @param paramPrompt {@code String} param's prompt.
   * @return {@code boolean} boolean value.
   */
  public static boolean validateParamsMap(Map<String, String> paramsMap, String paramPrompt) {
    return paramsMap.containsKey(paramPrompt) && !paramsMap.get(paramPrompt).isEmpty()
        && paramsMap.size() == 1;
  }

  /**
   * Set http response for the given httpExchange.
   *
   * @param httpExchange {@link HttpExchange} httpExchange.
   * @param httpCode     {@code int}http response standard code.
   * @param status       {@link ResponseStatus} costume response status.
   * @throws IOException exception.
   */
  public static String setResponse(HttpExchange httpExchange, int httpCode, ResponseStatus status)
      throws IOException {
    httpExchange.sendResponseHeaders(httpCode, status.toString().getBytes(
        StandardCharsets.UTF_8).length);
    OutputStream responseBody = httpExchange.getResponseBody();
    OutputStreamWriter writer = new OutputStreamWriter(responseBody, StandardCharsets.UTF_8);
    writer.write(status.toString());
    writer.close();
    responseBody.close();
    return status.toString();
  }


  /**
   * Send HTTP call.
   *
   * @param urlDir {@code String} url of the HTTP call.
   * @param method {@code String} REST method.
   */
  // TODO: return response version
  public static void sendHttpCall(String urlDir, String method) {
    try {
      URL url = new URL(urlDir);
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod(method);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
