package nz.ac.auckland.se281;

public class MediumAI implements AI {

  TypeAI type;
  TypeAI RandomAI = new RandomAI();
  TypeAI TopAI = new TopAI();

  @Override
  public int pickFingers(int rounds, String majority, String userChoice) {
    if (rounds <= 3) {
      type = RandomAI;
    } else {
      type = TopAI;
    }

    return type.generateValue(majority, userChoice);
  }
}
