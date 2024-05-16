package nz.ac.auckland.se281;

/**
 * For the first three rounds, the MEDIUM AI will use the RANDOM strategy, and then for the fourth
 * round onwards the HARD AI will switch to the TOP strategy until the current game ends.
 */
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
