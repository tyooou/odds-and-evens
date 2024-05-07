package nz.ac.auckland.se281;

import java.util.*;

public class EasyAI implements AI {

  TypeAI type = new RandomAI();
  int rounds = 0;

  @Override
  public int pickFingers(int rounds, List<Integer> gameHistory) {
    return type.generateValue(gameHistory);
  }
}
