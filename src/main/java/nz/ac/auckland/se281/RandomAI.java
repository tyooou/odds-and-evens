package nz.ac.auckland.se281;

public class RandomAI implements TypeAI {
  @Override
  public int generateValue(String majority, String userChoice) {
    return Utils.getRandomNumberRange(0, 5);
  }
}
