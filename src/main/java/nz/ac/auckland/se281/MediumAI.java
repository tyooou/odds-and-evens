package nz.ac.auckland.se281;

public class MediumAI implements AI {

  // Initialise AI variables.
  private TypeAI type;
  private final TypeAI RANDOM_AI = new RandomAI();
  private final TypeAI TOP_AI = new TopAI();

  @Override
  public int getFingers(GameObject gameObject) {

    // Get game's current round.
    int rounds = gameObject.getRounds();

    // If the rounds is less than or equal to 3, use RandomAI. Otherwise, use TopAI.
    if (rounds <= 3) {
      type = RANDOM_AI;
    } else {
      type = TOP_AI;
    }

    // Generate value based on AI.
    return type.generateValue(gameObject);
  }
}
