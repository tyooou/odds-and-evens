package nz.ac.auckland.se281;

public class EasyAI implements AI {

  // Initialise AI variables.
  private TypeAI type = new RandomAI();

  @Override
  public int getFingers(GameObject gameObject) {
    // Generate value based on AI.
    return type.generateValue(gameObject);
  }
}
