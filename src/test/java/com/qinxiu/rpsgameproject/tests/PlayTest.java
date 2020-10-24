package com.qinxiu.rpsgameproject.tests;


import static org.mockito.Mockito.times;

import com.qinxiu.rpsgameproject.Play;
import com.qinxiu.rpsgameproject.Player;
import com.qinxiu.rpsgameproject.common.Choices;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PlayTest {


  @Test
  public void singlePlayTest() {

    // Arrange
    Player p1 = new Player("p1", false, true);
    Player p2 = new Player("p2", false, false);

    // Act
    Play play = new Play(1, p1, p2, null);

    //Asserts
    assertPlay(play, p1, p2, 1);
  }

  @Test
  public void multiPlayTest() {

    // Arrange
    Player p1 = new Player("p1", false, true);
    Player p2 = new Player("p2", false, true);
    int iteration = 10;

    // Act
    Play[] playArray = new Play[iteration];
    for (int x = 0; x < iteration; x++) {
      playArray[x] = new Play(x + 1, p1, p2, null);
    }

    // Asserts
    for (Play play : playArray) {
      assertPlay(play, p1, p2, iteration);
    }
  }

  @Test
  @SuppressWarnings("unchecked")
  public void remotePlayMockTest() throws InterruptedException {

    // Arrange
    BlockingQueue<String> mockQueue =  Mockito.mock(LinkedBlockingQueue.class);
    Player p1 = new Player("p1", false, true);
    Player p2 = new Player("p2", true, true);
    Mockito.when(mockQueue.take()).thenReturn("").thenReturn(Choices.ROCK);

    // Act
    Play play = new Play(1, p1, p2, mockQueue);

    // Asserts
    assertPlay(play, p1, p2, 1);
    Mockito.verify(mockQueue, times(2)).take();
  }

  private void assertPlay(Play play, Player p1, Player p2, int iteration) {
    // result not empty
    Assert.assertFalse(play.toString().isEmpty());

    // one of them has to win or is a draw game.
    Assert.assertTrue(
        (p1.getWinCounter() > 0) || (p2.getWinCounter() > 0) || (p1.getDrawCounter() > 0
            && p2.getDrawCounter() > 0));

    // the sum of p1 win's counter, p2 win's counter and drawCounter of one of them must be 1.
    Assert.assertEquals(p1.getWinCounter() + p2.getWinCounter() + p1.getDrawCounter(), iteration);
    Assert.assertEquals(p1.getWinCounter() + p2.getWinCounter() + p2.getDrawCounter(), iteration);
  }

//  Tests with real server execution.
//  @Test
//  public void remotePlayTest() {
//
//    String url = "http://localhost:8081/myserver?choice=Rock";
//
//    new Thread(() -> {
//      try {
//        remoteProcess();
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//    }).start();
//    new Thread(() -> HttpUtils.sendHttpCall(url, "POST")).start();
//  }
//
//  private void remoteProcess() throws IOException {
//
//    // Server set up
//    BlockingQueue<String> choiceQueue = new LinkedBlockingQueue<>(1);
//    MyHttpHandler choiceHandler = new MyHttpHandler(choiceQueue, HttpUtils.PROMPT_CHOICE);
//    MyHttpServer httpServer = new MyHttpServer(PORT, null, choiceHandler);
//    httpServer.start();
//
//    Player p1 = new Player("p1", false, true);
//    Player p2 = new Player("p2", true, true);
//    Play play = new Play(1, p1, p2, choiceQueue);
//    assertPlay(play, p1, p2, 1);
//
//    httpServer.close();
//    choiceHandler.close();
//  }


}
