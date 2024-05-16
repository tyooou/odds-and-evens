package nz.ac.auckland.se281;

/**
 * Gets a number of fingers chosen by the opponent based on its artificial intelligence and
 * information from the active game.
 */
public interface ArtificialIntelligence {

  /**
   * Return the number of fingers chosen by the opponent based on its artificial intelligence and
   * information from the active game.
   *
   * @param gameObject object of active game.
   * @return the number of fingers chosen by the opponent based on its artificial intelligence and
   *     information from the active game.
   */
  public int getFingers(GameObject gameObject);
}
