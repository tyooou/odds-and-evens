package nz.ac.auckland.se281;

public class RandomAI implements TypeArtificialIntelligence {

  /** {@inheritDoc} */
  @Override
  public int generateValue(GameObject gameObject) {
    // Return a random number regardless of user's past actions.
    return Utils.getRandomNumberRange(0, 5);
  }
}
