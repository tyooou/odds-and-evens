package nz.ac.auckland.se281;

public class EasyAI implements AI {

  TypeAI type = new RandomAI();

  @Override
  public int pickFingers() {
    return type.generateValue();
  }
}
