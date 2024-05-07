package nz.ac.auckland.se281;

public class EasyAI implements AI {

  TypeAI type = new RandomAI();
  int rounds = 0;

  @Override
  public int pickFingers(int rounds, String majority, String userChoice) {
    return type.generateValue(majority, userChoice);
  }
}
