package nz.ac.auckland.se281;

import java.util.List;

public class MediumAI implements AI {

  TypeAI type;

  @Override
  public int pickFingers(int rounds, List<Integer> gameHistory) {
    if (rounds <= 3) {
      type = new RandomAI();
    } else {
      type = new TopAI();
    }

    return type.generateValue(gameHistory);
  }
}
