package com.qinxiu.rpsgameproject;

import com.qinxiu.rpsgameproject.common.Choices;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

public class Play {

  private int id;
  private Player p1;
  private Player p2;
  private String p1Choice;
  private String p2Choice;
  private int result;
  private BlockingQueue<String> remoteChoiceQueue;


  /**
   * Play Constructor.
   *
   * @param id {@code int} identifier of Play.
   * @param p1 {@link Player} Player.
   * @param p2 {@link Player} Player.
   */
  public Play(int id, Player p1, Player p2) {
    this.id = id;
    this.p1 = p1;
    this.p2 = p2;
    start();
  }

  /**
   * Play Constructor with remote choice queue.
   *
   * @param id                {@code int} identifier of Play.
   * @param p1                {@link Player} Player.
   * @param p2                {@link Player} Player.
   * @param remoteChoiceQueue {@link BlockingQueue} queue for receive remote Player's choice.
   */
  public Play(int id, Player p1, Player p2, BlockingQueue<String> remoteChoiceQueue) {
    this.id = id;
    this.p1 = p1;
    this.p2 = p2;
    this.remoteChoiceQueue = remoteChoiceQueue;
    start();
  }

  private void start() {
    // both players show their choice
    showChoices();
    // compare both choices
    this.result = compareChoices(this.p1Choice, this.p2Choice);
    // update Players regarding to the result.
    updatePlayersCounter();
  }

  private void showChoices() {
    // remote: one of them is a remote player.
    if (this.p1.getIsRemote()) {
      this.p1Choice = getRemoteChoice();
      this.p2Choice = p2.getChoice();
    } else if (this.p2.getIsRemote()) {
      this.p1Choice = p1.getChoice();
      this.p2Choice = getRemoteChoice();
    } else {
      // standalone: both are machines.
      this.p1Choice = p1.getChoice();
      this.p2Choice = p2.getChoice();
    }
  }

  private String getRemoteChoice() {
    System.out.println("Game [" + this.id + "] waiting for remote player's choice...");
    var response = new AtomicReference<>("");
    while (response.get().isBlank()) {
      try {
        String value = this.remoteChoiceQueue.take();
        if (!value.isBlank() && !value.isEmpty()) {
          response.set(value);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      //  Thread.sleep(500);
    }
    return response.get();
  }

  private int compareChoices(String p1Choice, String p2Choice) {
    if (p1Choice.equals(Choices.ROCK) && p2Choice.equals(Choices.SCISSORS)
        || p1Choice.equals(Choices.SCISSORS) && p2Choice.equals(Choices.PAPER)
        || p1Choice.equals(Choices.PAPER) && p2Choice.equals(Choices.ROCK)) {
      return -1;
    } else if (p2Choice.equals(Choices.ROCK) && p1Choice.equals(Choices.SCISSORS)
        || p2Choice.equals(Choices.SCISSORS) && p1Choice.equals(Choices.PAPER)
        || p2Choice.equals(Choices.PAPER) && p1Choice.equals(Choices.ROCK)) {
      return 1;
    }
    return 0;
  }

  private void updatePlayersCounter() {
    switch (this.result) {
      case -1:
        this.p1.setWinCounter(this.p1.getWinCounter() + 1);
        break;
      case 0:
        this.p1.setDrawCounter(this.p1.getDrawCounter() + 1);
        this.p2.setDrawCounter(this.p2.getDrawCounter() + 1);
        break;
      case 1:
        this.p2.setWinCounter(this.p2.getWinCounter() + 1);
        break;
      default:
        break;
    }
  }

  @Override
  public String toString() {
    String msg = "\n\n===================\n Game" + this.id + "\n===================\n";
    msg = msg.concat("[" + this.p1.getName() + "] has chosen: [" + p1Choice + "]\n"
        + "[" + this.p2.getName() + "] has chosen: [" + p2Choice + "]\n");
    switch (this.result) {
      case -1:
        msg = msg.concat("the winner is: [" + this.p1.getName() + "]");
        break;
      case 0:
        msg = msg.concat("the game was a draw");
        break;
      case 1:
        msg = msg.concat("the winner is: [" + this.p2.getName() + "]");
        break;
      default:
        break;
    }
    return msg;
  }
}
