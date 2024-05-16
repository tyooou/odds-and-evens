package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Main.Difficulty;

public class ArtificialIntelligenceFactory {

  /**
   * Return the artificial intelligence of the opponent based on the user's choice of difficulty for
   * the active game.
   *
   * @param difficulty difficulty of the opponent.
   * @return artificial intelligence of the opponent as a ArtificialIntelligence.
   */
  public static ArtificialIntelligence createAI(Difficulty difficulty) {

    // Return AI based on inputted difficulty.
    switch (difficulty) {
      case EASY:
        return new EasyAI();
      case MEDIUM:
        return new MediumAI();
      case HARD:
        return new HardAI();

        // Default to null if difficulty is invalid.
      default:
        return null;
    }
  }
}
