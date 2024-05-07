package nz.ac.auckland.se281;

import java.util.*;

public class RandomAI implements TypeAI {
  @Override
  public int generateValue(List<Integer> gameHistor) {
    return Utils.getRandomNumberRange(0, 5);
  }
}
