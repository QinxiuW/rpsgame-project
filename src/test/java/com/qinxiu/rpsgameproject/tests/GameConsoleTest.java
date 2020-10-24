package com.qinxiu.rpsgameproject.tests;


import com.qinxiu.rpsgameproject.GameConsole;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.junit.Assert;
import org.junit.Test;

public class GameConsoleTest {

  private final PrintStream standardOut = System.out;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  @Test
  public void startTestAllModes() {
    GameConsole console = new GameConsole();
    startModeSelectedTest(console, "1", "you have chosen fair mode");
    startModeSelectedTest(console, "2", "you have chosen unfair mode");
    remoteModeStartTest(console);
  }

  @Test
  public void endTestAllModes() {
    GameConsole console = new GameConsole();
    endModeSelectedTest(console, "1", "you have chosen [1]Console");
    endModeSelectedTest(console, "2", "you have chosen [2]File");
    File f = new File(GameConsole.OUTPUT_FILE_PATH);
    if (f.exists()) {
      var result = f.delete();
      Assert.assertTrue(result);
    }
  }

  private void endModeSelectedTest(GameConsole console, String option, String expectedText) {
    // Arrange
    System.setOut(new PrintStream(outputStreamCaptor));
    ByteArrayInputStream in = new ByteArrayInputStream(option.getBytes());
    System.setIn(in);

    // Act
    console.end();

    //Asserts
    var outputMsg = outputStreamCaptor.toString().trim();
    Assert.assertTrue(outputMsg.contains(expectedText));
    System.setOut(standardOut);
  }

  private void sendHttpCall(String urlDir, String method) throws IOException {
    URL url = new URL(urlDir);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod(method);
  }

  //remote mode needs http call so I apply 2 threads
  private void remoteModeStartTest(GameConsole console) {
    new Thread(() -> startModeSelectedTest(console, "3", "you have chosen remote mode")).start();
    new Thread(() -> {
      try {
        sendHttpCall("http://localhost:8081/myserver?player=Pep", "POST");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }).start();
  }

  private void startModeSelectedTest(GameConsole console, String option, String expectedText) {
    // Arrange
    System.setOut(new PrintStream(outputStreamCaptor));
    ByteArrayInputStream in = new ByteArrayInputStream(option.getBytes());
    System.setIn(in);

    // Act
    console.start();

    //Asserts
    var outputMsg = outputStreamCaptor.toString().trim();
    Assert.assertTrue(outputMsg.contains(expectedText));
    System.setOut(standardOut);
  }

}
