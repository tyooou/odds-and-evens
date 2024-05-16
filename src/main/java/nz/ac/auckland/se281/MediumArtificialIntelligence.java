package nz.ac.auckland.se281;

public class MediumArtificialIntelligence implements ArtificialIntelligence {

  // Initialise AI variables.
  private TypeArtificialIntelligence type;
  private final TypeArtificialIntelligence RANDOM = new RandomArtificialIntelligence();
  private final TypeArtificialIntelligence TOP = new TopArtificialIntelligence();

  /** {@inheritDoc} */
  @Override
  public int getFingers(GameObject gameObject) {

    // Get game's current round.
    int rounds = gameObject.getRounds();

    // If the rounds is less than or equal to 3, use RandomAI. Otherwise, use TopAI.
    if (rounds <= 3) {
      type = RANDOM;
    } else {
      type = TOP;
    }

    // Generate value based on AI.
    return type.generateValue(gameObject);
  }
}
