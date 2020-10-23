package com.qinxiu.rpsgameproject.common;

public class CommonMessage {

  /**
   * Print welcome text to the console.
   */
  public static void welcomeText() {
    System.out.println("Welcome to RPS game");
  }

  /**
   * Print mode choice text to the console.
   */
  public static void modeChoiceText() {
    System.out.println("Choice one mode please:");
    System.out.println(" *[1]fair mode");
    System.out.println(" *[2]unfair mode");
    System.out.println(" *[3]remote mode");
  }

  public static void goodByeText() {
    System.out.println("Thanks for visiting!");
  }

  /**
   * Print output choice to the console.
   */
  public static void outputChoiceText() {
    System.out.println("Choose one output option please:");
    System.out.println(" *[1]Console");
    System.out.println(" *[2]File");
  }
}
