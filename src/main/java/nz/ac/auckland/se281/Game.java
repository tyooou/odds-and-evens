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
  public boolean newGameMade = false;
  public boolean playing = false;

  // Initialise game history.
  public List<Integer> choiceHistory = new ArrayList<Integer>();
  public List<Integer> outcomeHistory = new ArrayList<Integer>();
  String majority;

  public void newGame(Difficulty difficulty, Choice choice, String[] options) {
    newGameMade = true;
    playing = false;
    rounds = 0;
    choiceHistory.clear();
    outcomeHistory.clear();
    userChoice = choice == Choice.EVEN ? "EVEN" : "ODD";
    name = options[0];

    opponent = AIFactory.createAI(difficulty);

    MessageCli.WELCOME_PLAYER.printMessage(name);
  }

  public void play() {
    if (newGameMade) {
      playing = true;

      // Initialise round variables;
      int userInput, opponentInput, sum;
      String winner = name;
      boolean swapAI = false;

      // Begin new round.
      this.rounds += 1;
      MessageCli.START_ROUND.printMessage(String.valueOf(rounds));

      // Find the majority BEFORE player chooses fingers (as the opponent should not know what the
      // user has chosen).
      updateMajority();

      // Check if the user input is a valid input.
      userInput = userPickFingers();

      // Find the polarity of the user's amount of fingers (0: EVEN, 1: ODD).
      int userPolarity = Utils.isEven(userInput) ? 0 : 1;

      // Store the polarity of the user's amount of fingers to the game history.
      choiceHistory.add(userPolarity);

      // Get and print the opponent's amount of fingers.
      if (outcomeHistory.size() > 0) {
        swapAI = outcomeHistory.get(outcomeHistory.size() - 1) == 1 ? true : false;
      }

      opponentInput = opponent.pickFingers(rounds, majority, userChoice, swapAI);
      MessageCli.PRINT_INFO_HAND.printMessage(AI.name, String.valueOf(opponentInput));

      // Find sum of the fingers and its polarity.
      sum = opponentInput + userInput;
      String messagePolarity = Utils.isEven(sum) ? "EVEN" : "ODD";

      // Find and print result.
      if ((Utils.isEven(sum) && userChoice != "EVEN")
          || (Utils.isOdd(sum) && userChoice != "ODD")) {
        winner = AI.name;
      }

      int outcome = winner == name ? 1 : 0;
      outcomeHistory.add(outcome);

      MessageCli.PRINT_OUTCOME_ROUND.printMessage(String.valueOf(sum), messagePolarity, winner);
    } else {
      MessageCli.GAME_NOT_STARTED.printMessage();
    }
  }

  // Update the polarity majority of the user's finger selections.
  public void updateMajority() {
    int evenCount = (int) choiceHistory.stream().filter(item -> item.equals(0)).count();
    int length = choiceHistory.size();

    if (evenCount > (0.5 * length)) {
      majority = "EVEN";
    } else if (evenCount == (0.5 * length)) {
      majority = "EQUAL";
    } else {
      majority = "ODD";
    }
  }

  // Read and validate user's finger selection.
  public int userPickFingers() {
    String userInput;
    boolean flag = false;

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

    return Integer.parseInt(userInput);
  }

  public void endGame() {
    if (newGameMade) {
      newGameMade = false;
      int userWins = (int) outcomeHistory.stream().filter(item -> item.equals(1)).count();
      int userLosses = outcomeHistory.size() - userWins;

      showStats();

      if (userWins > userLosses) {
        MessageCli.PRINT_END_GAME.printMessage(name);
      } else if (userWins == userLosses) {
        MessageCli.PRINT_END_GAME_TIE.printMessage();
      } else {
        MessageCli.PRINT_END_GAME.printMessage(AI.name);
      }
    } else {
      MessageCli.GAME_NOT_STARTED.printMessage();
    }
  }

  public void showStats() {
    if (playing) {
      int userWins = (int) outcomeHistory.stream().filter(item -> item.equals(1)).count();
      int userLosses = outcomeHistory.size() - userWins;

      MessageCli.PRINT_PLAYER_WINS.printMessage(
          name, String.valueOf(userWins), String.valueOf(userLosses));
      MessageCli.PRINT_PLAYER_WINS.printMessage(
          AI.name, String.valueOf(userLosses), String.valueOf(userWins));
    } else {
      MessageCli.GAME_NOT_STARTED.printMessage();
    }
  }
}
