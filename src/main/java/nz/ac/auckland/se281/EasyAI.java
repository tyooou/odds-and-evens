package nz.ac.auckland.se281;

public class EasyAI implements ArtificialIntelligence {

  // Initialise AI variables.
  private TypeArtificialIntelligence type = new RandomAI();

  /** {@inheritDoc} */
  @Override
  public int getFingers(GameObject gameObject) {
    // Generate value based on AI.
    return type.generateValue(gameObject);
  }
}
