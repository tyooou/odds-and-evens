package nz.ac.auckland.se281;

/**
 * Interface for all artificial intelligence types, such as RandomArtificialIntelligence and
 * TopArtificialIntelligence. They all must generate a value, representing the number of fingers
 * chosen by the opponent.
 */
public interface TypeArtificialIntelligence {

  /**
   * Generate the number of fingers chosen by the opponent based on its artificial intelligence and
   * information from the active game.
   *
   * @param gameObject object of active game.
   * @return the number of fingers chosen by the opponent based on its artificial intelligence and
   *     information from the active game.
   */
  public int generateValue(GameObject gameObject);
}
