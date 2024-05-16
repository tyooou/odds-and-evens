package nz.ac.auckland.se281;

import java.util.List;

public class HardArtificialIntelligence implements ArtificialIntelligence {

  // Initialise AI variables.
  private TypeArtificialIntelligence type;
  private final TypeArtificialIntelligence RANDOM = new RandomArtificialIntelligence();
  private final TypeArtificialIntelligence TOP = new TopArtificialIntelligence();

  /** {@inheritDoc} */
  @Override
  public int getFingers(GameObject gameObject) {

    // Get game's current round.
    int rounds = gameObject.getRounds();

    // If the rounds is less than 3, use RandomAI. Otherwise, swap AI if AI lost last round.
    if (rounds <= 3) {
      type = RANDOM;
    } else {

      List<String> outcomeHistory = gameObject.getOutcomeHistory();
      boolean swap = outcomeHistory.get(outcomeHistory.size() - 1) == "WIN" ? true : false;

      if (swap) {
        if (type == RANDOM) {
          type = TOP;
        } else {
          type = RANDOM;
        }
      }
    }

    // Generate value based on AI.
    return type.generateValue(gameObject);
  }
}
