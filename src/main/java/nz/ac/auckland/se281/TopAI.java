package nz.ac.auckland.se281;

import java.util.*;

public class TopAI implements TypeAI {

  List<Integer> gameHistory;

  @Override
  public int generateValue(String majority, String userChoice) {
    if (majority == "EQUAL") {
      return Utils.getRandomNumberRange(0, 5);
    } else if (majority == userChoice) {
      return Utils.getRandomOddNumber();
    } else {
      return Utils.getRandomEvenNumber();
    }
  }
}
