package com.qinxiu.rpsgameproject;

import java.util.concurrent.BlockingQueue;

public class Game {

  private final Player p1;

  private final Player p2;

  private final Play[] plays;


  /**
   * Constructor.
   *
   * @param p1                {@link Player} first player.
   * @param p2                {@link Player} second player.
   * @param iteration         {@code int} number of iterations.
   * @param remoteChoiceQueue {@link BlockingQueue} queue for receive remote Player's choice.
   */
  public Game(Player p1, Player p2, int iteration, BlockingQueue<String> remoteChoiceQueue) {
    this.p1 = p1;
    this.p2 = p2;
    this.plays = new Play[iteration];
    startPlays(iteration, remoteChoiceQueue);
  }

  public Play[] getPlays() {
    return this.plays;
  }


  @Override
  public String toString() {
    String msg = "";
    for (Play play : this.plays) {
      msg = msg.concat(play.toString());
    }
    msg = msg.concat(finalResultInfo(this.p1, this.p2));
    return msg;
  }

  private void startPlays(int iteration, BlockingQueue<String> queue) {
    for (int x = 0; x < iteration; x++) {
      Play play = new Play(x + 1, this.p1, this.p2, queue);
      this.plays[x] = play;
    }
  }

  private String finalResultInfo(Player p1, Player p2) {
    return "\n\n===================\n FINAL RESULT \n===================\n"
        + p1.toString() + "\n" + p2.toString();
  }

}


