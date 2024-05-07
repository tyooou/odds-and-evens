package nz.ac.auckland.se281;

import java.util.*;
import nz.ac.auckland.se281.Main.Choice;
import nz.ac.auckland.se281.Main.Difficulty;

/** This class represents the Game is the main entry point. */
public class Game {

  public int rounds;
  public String name;
  public String userChoice;
  public AI opponent;

  // Initialise game history.
  public List<Integer> gameHistory = new ArrayList<Integer>();
  String majority = "EQUAL";

  public void newGame(Difficulty difficulty, Choice choice, String[] options) {
    rounds = 0;
    gameHistory.clear();
    userChoice = choice == Choice.EVEN ? "EVEN" : "ODD";
    name = options[0];

    opponent = AIFactory.createAI(difficulty);

    MessageCli.WELCOME_PLAYER.printMessage(name);
  }

  public void play() {
    String userInput, opponentInput;

    this.rounds += 1;
    MessageCli.START_ROUND.printMessage(String.valueOf(rounds));

    int sum;
    boolean flag = false;

    updateMajority();

    // Check if the user input is a valid input.
    do {
      MessageCli.ASK_INPUT.printMessage();
      userInput = Utils.scanner.nextLine();
      if (!Utils.isInteger(userInput)
          || ((Integer.parseInt(userInput) < 0) || (Integer.parseInt(userInput) > 5))) {
        MessageCli.INVALID_INPUT.printMessage();
      } else {
        MessageCli.PRINT_INFO_HAND.printMessage(name, userInput);
        flag = true;
      }
    } while (!flag);

    // Find the polarity of the user's amount of fingers (0: EVEN, 1: ODD).
    int userPolarity = Utils.isEven(Integer.parseInt(userInput)) ? 0 : 1;

    // Store the polarity of the user's amount of fingers to the game history.
    gameHistory.add(userPolarity);

    // Get and print the opponent's amount of fingers.
    opponentInput = String.valueOf(opponent.pickFingers(rounds, majority, userChoice));
    MessageCli.PRINT_INFO_HAND.printMessage(AI.name, opponentInput);

    // Find sum of the fingers and its polarity.
    sum = Integer.parseInt(opponentInput) + Integer.parseInt(userInput);
    String messagePolarity = Utils.isEven(sum) ? "EVEN" : "ODD";

    String winner = name;

    if ((Utils.isEven(sum) && userChoice != "EVEN") || (Utils.isOdd(sum) && userChoice != "ODD")) {
      winner = AI.name;
    }

    MessageCli.PRINT_OUTCOME_ROUND.printMessage(String.valueOf(sum), messagePolarity, winner);
  }

  public void updateMajority() {
    int evenCount = (int) gameHistory.stream().filter(item -> item.equals(0)).count();
    int length = gameHistory.size();

    if (evenCount > (0.5 * length)) {
      majority = "EVEN";
    } else if (evenCount == (0.5 * length)) {
      majority = "EQUAL";
    } else {
      majority = "ODD";
    }
  }

  public void endGame() {}

  public void showStats() {}
}
