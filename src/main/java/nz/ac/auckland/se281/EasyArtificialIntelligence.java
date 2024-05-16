package nz.ac.auckland.se281;

public class EasyArtificialIntelligence implements ArtificialIntelligence {

  // Initialise AI variables.
  private TypeArtificialIntelligence type = new RandomArtificialIntelligence();

  /** {@inheritDoc} */
  @Override
  public int getFingers(GameObject gameObject) {
    // Generate value based on AI.
    return type.generateValue(gameObject);
  }
}
