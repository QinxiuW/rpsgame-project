package com.qinxiu.rpsgameproject.tests;


import com.qinxiu.rpsgameproject.Player;
import com.qinxiu.rpsgameproject.common.Choices;
import java.util.HashSet;
import org.junit.Assert;
import org.junit.Test;

public class PlayerTest {


  @Test
  public void getNameTest() {
    Player p = new Player("testName", false, true);
    Assert.assertEquals(p.getName(), "testName");
  }

  @Test
  public void winCounterTest() {
    Player p = new Player("testName", false, true);
    assert (p.getWinCounter() == 0);

    int number = 3;
    p.setWinCounter(number);
    Assert.assertEquals(p.getWinCounter(), number);
  }

  @Test
  public void drawCounterTest() {
    Player p = new Player("testName", false, true);
    assert (p.getDrawCounter() == 0);

    int number = 3;
    p.setDrawCounter(number);
    Assert.assertEquals(p.getDrawCounter(), number);
  }

  @Test
  public void getChoiceTest() {
    // random case
    Player p1 = new Player("testName1", false, true);
    HashSet<String> randChoiceSet = new HashSet<>();
    for (int x = 0; x < 20; x++) {
      randChoiceSet.add(p1.getChoice());
    }
    Assert.assertTrue(randChoiceSet.size() > 1);

    // only Rock case
    HashSet<String> rockChoiceSet = new HashSet<>();
    Player p2 = new Player("testName2", false, false);
    for (int x = 0; x < 10; x++) {
      rockChoiceSet.add(p2.getChoice());
    }
    Assert.assertEquals(1, rockChoiceSet.size());
    for (String s : rockChoiceSet) {
      Assert.assertEquals(s, Choices.ROCK);
    }
  }
}
