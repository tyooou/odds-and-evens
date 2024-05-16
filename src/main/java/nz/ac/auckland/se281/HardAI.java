package nz.ac.auckland.se281;

import java.util.List;

public class HardAI implements ArtificialIntelligence {

  // Initialise AI variables.
  private TypeArtificialIntelligence type;
  private final TypeArtificialIntelligence RANDOM_AI = new RandomAI();
  private final TypeArtificialIntelligence TOP_AI = new TopAI();

  /** {@inheritDoc} */
  @Override
  public int getFingers(GameObject gameObject) {

    // Get game's current round.
    int rounds = gameObject.getRounds();

    // If the rounds is less than 3, use RandomAI. Otherwise, swap AI if AI lost last round.
    if (rounds <= 3) {
      type = RANDOM_AI;
    } else {

      List<String> outcomeHistory = gameObject.getOutcomeHistory();
      boolean swapAI = outcomeHistory.get(outcomeHistory.size() - 1) == "WIN" ? true : false;

      if (swapAI) {
        if (type == RANDOM_AI) {
          type = TOP_AI;
        } else {
          type = RANDOM_AI;
        }
      }
    }

    // Generate value based on AI.
    return type.generateValue(gameObject);
  }
}
