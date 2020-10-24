package com.qinxiu.rpsgameproject.server;

import com.qinxiu.rpsgameproject.common.HttpUtils;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Value;

public class MyHttpServer {

  private com.sun.net.httpserver.HttpServer httpServer;
  @Value("${server.port}")
  private int port;

  /**
   * MyHttpServer Constructor.
   *
   * @param playerHandler {@link HttpHandler} http handler for remote player.
   * @param choiceHandler {@link HttpHandler} http handler for remote player's choice.
   * @throws IOException exception.
   */
  public MyHttpServer(HttpHandler playerHandler, HttpHandler choiceHandler)
      throws IOException {

    //Create an HttpServer instance and bind it to the specified IP address and port number
    this.httpServer = com.sun.net.httpserver.HttpServer
        .create(new InetSocketAddress(port), 0);

    //Create an HttpContext
    if (playerHandler != null) {
      this.httpServer.createContext(HttpUtils.HTTP_PATH_PLAYER, playerHandler);
    }
    if (choiceHandler != null) {
      this.httpServer.createContext(HttpUtils.HTTP_PATH_CHOICE, choiceHandler);
    }

    //Set the server's thread pool object
    this.httpServer.setExecutor(Executors.newFixedThreadPool(1));
  }


  /**
   * start the server.
   */
  public void start() {
    //Start the server
    this.httpServer.start();
    System.out.println("server started...");
  }

  /**
   * close the server.
   */
  public void close() {
    this.httpServer.stop(0);
  }
}
