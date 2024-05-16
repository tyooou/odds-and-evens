package nz.ac.auckland.se281;

import java.util.List;

/**
 * For the first three rounds, the HARD AI will use the RANDOM strategy, and then for the fourth
 * round onwards the HARD AI will switch strategy if the current strategy lost in the previous
 * round, otherwise, the current strategy is kept. The TOP strategy will consider the whole history
 * from round one.
 */
public class HardArtificialIntelligence implements ArtificialIntelligence {

  // Initialise AI variables.
  private TypeArtificialIntelligence type;
  private final TypeArtificialIntelligence random = new RandomArtificialIntelligence();
  private final TypeArtificialIntelligence top = new TopArtificialIntelligence();

  /** {@inheritDoc} */
  @Override
  public int getFingers(GameObject gameObject) {

    // Get game's current round.
    int rounds = gameObject.getRounds();

    // If the rounds is less than 3, use RandomAI. Otherwise, swap AI if AI lost last round.
    if (rounds <= 3) {
      setStrategy(random);
    } else {

      // Fetch the outcome history to see if the previous round was won or lost.
      List<String> outcomeHistory = gameObject.getOutcomeHistory();
      boolean swap = outcomeHistory.get(outcomeHistory.size() - 1) == "WIN" ? true : false;

      // Swap AIs.
      if (swap) {
        if (type == random) {
          setStrategy(top);
        } else {
          setStrategy(random);
        }
      }
    }

    // Generate value based on AI.
    return type.generateValue(gameObject);
  }

  /** {@inheritDoc} */
  @Override
  public void setStrategy(TypeArtificialIntelligence type) {
    this.type = type;
  }
}
