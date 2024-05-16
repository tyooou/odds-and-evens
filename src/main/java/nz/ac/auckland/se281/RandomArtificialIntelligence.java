package nz.ac.auckland.se281;

/**
 * The RANDOM strategy generates a random integer between 0 and 5 inclusive, regardless of the
 * user's previous inputs.
 */
public class RandomArtificialIntelligence implements TypeArtificialIntelligence {

  /** {@inheritDoc} */
  @Override
  public int generateValue(GameObject gameObject) {
    // Return a random number regardless of user's past actions.
    return Utils.getRandomNumberRange(0, 5);
  }
}
