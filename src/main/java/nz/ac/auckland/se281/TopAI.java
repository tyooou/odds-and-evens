package nz.ac.auckland.se281;

/*
    If the user has equal even and odd inputs, all numbers has equal chance of winning, therefore
    return a random number.

    If the user has majority inputs same as their choice, return an odd number (user needs X to
    win, X + ODD = !X).
      Choice: EVEN, Input: EVEN ---- EVEN + ODD = ODD.
      Choice: ODD, Input: ODD ---- ODD + ODD = EVEN.

    If the user has majority inputs different as their choice, return an even number (user needs !X
    to win, X + EVEN = X).
      Choice: EVEN, Input: ODD ---- ODD + EVEN = ODD.
      Choice: ODD, Input: EVEN ---- EVEN + EVEN = EVEN.
*/

public class TopAI implements TypeAI {

  @Override
  public int generateValue(GameObject gameObject) {

    // Get game's majority and user's choice.
    String majority = gameObject.getMajority();
    String userChoice = gameObject.getChoice();

    // Return a number that increases likelihood of winning based on the user's past inputs.
    if (majority == "EQUAL") {
      return Utils.getRandomNumberRange(0, 5);
    } else if (majority == userChoice) {
      return Utils.getRandomOddNumber();
    } else {
      return Utils.getRandomEvenNumber();
    }
  }
}
