package nz.ac.auckland.se281;

import java.util.*;

public class TopAI implements TypeAI {

  List<Integer> gameHistory;

  @Override
  public int generateValue(List<Integer> gameHistory) {

    int evenCount = (int) gameHistory.stream().filter(item -> item.equals(0)).count();
    int length = gameHistory.size();

    if (evenCount > (0.5 * length)) {
      return Utils.getRandomEvenNumber();
    } else if (evenCount == (0.5 * length)) {
      return Utils.getRandomNumberRange(0, 5);
    } else {
      return Utils.getRandomOddNumber();
    }
  }
}
