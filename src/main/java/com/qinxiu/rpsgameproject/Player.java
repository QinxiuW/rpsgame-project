package com.qinxiu.rpsgameproject;

import com.qinxiu.rpsgameproject.common.Choices;

public class Player {

  public static final int NAME_MAX_LENGTH = 20;

  private final String name;

  private int winCounter;

  private int drawCounter;

  private final boolean isRemote;

  private final boolean randomChoice;

  /**
   * Player Constructor.
   *
   * @param name         {@code String} Player's name.
   * @param isRemote     {@code String} if is a remote Player.
   * @param randomChoice {@code String} if plays with random choices.
   */
  public Player(String name, boolean isRemote, boolean randomChoice) {
    this.name = name;
    this.winCounter = 0;
    this.drawCounter = 0;
    this.isRemote = isRemote;
    this.randomChoice = randomChoice;
  }

  /**
   * Return a choice regarding to Player.
   *
   * @return {@code String} Choice.
   */
  public String getChoice() {
    int randNumber = (int) (Math.random() * 10) % 3;

    if (!this.randomChoice) {
      return Choices.ROCK;
    }
    return Choices.COMPLETE_CHOICE_LIST[randNumber];
  }


  public String getName() {
    return this.name;
  }

  public int getWinCounter() {
    return winCounter;
  }

  public int getDrawCounter() {
    return drawCounter;
  }

  public boolean getIsRemote() {
    return this.isRemote;
  }

  public void setWinCounter(int winCounter) {
    this.winCounter = winCounter;
  }

  public void setDrawCounter(int drawCounter) {
    this.drawCounter = drawCounter;
  }

  @Override
  public String toString() {
    return "[" + this.name + "] wins:" + this.winCounter + "  draws:" + this.drawCounter;
  }
}
