package nz.ac.auckland.se281;

/** The EASY AI will always use the RANDOM strategy until the game ends. */
public class EasyArtificialIntelligence implements ArtificialIntelligence {

  // Initialise AI variables.
  private TypeArtificialIntelligence type = new RandomArtificialIntelligence();

  /** {@inheritDoc} */
  @Override
  public int getFingers(GameObject gameObject) {
    // Generate value based on AI.
    return type.generateValue(gameObject);
  }

  /** {@inheritDoc} */
  @Override
  public void setStrategy(TypeArtificialIntelligence type) {
    this.type = type;
  }
}
