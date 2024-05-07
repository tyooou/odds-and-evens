package nz.ac.auckland.se281;

public class HardAI implements AI {

  TypeAI type;
  TypeAI RandomAI = new RandomAI();
  TypeAI TopAI = new TopAI();

  @Override
  public int pickFingers(int rounds, String majority, String userChoice, boolean swapAI) {
    if (rounds <= 3) {
      type = RandomAI;
    } else if (swapAI && type == RandomAI) {
      type = TopAI;
    } else if (swapAI && type == TopAI) {
      type = RandomAI;
    }

    return type.generateValue(majority, userChoice);
  }
}
