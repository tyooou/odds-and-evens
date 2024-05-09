package nz.ac.auckland.se281;

import java.util.*;
import nz.ac.auckland.se281.Main.Choice;
import nz.ac.auckland.se281.Main.Difficulty;

/** This class represents the Game is the main entry point. */
public class Game {

  public int rounds;
  public int userWins, userLosses;
  public String userName;
  public String userChoice;
  public String opponentName;
  public AI opponent;
  public boolean gameActive = false;
  public boolean playing = false;

  // Initialise game history.
  private List<String> choiceHistory = new ArrayList<String>();
  private List<String> outcomeHistory = new ArrayList<String>();
  private String majority;

  public void newGame(Difficulty difficulty, Choice choice, String[] options) {

    // Update state of game.
    this.gameActive = true;
    this.playing = false;

    // Reset game variables.
    this.rounds = 0;
    this.choiceHistory.clear();
    this.outcomeHistory.clear();

    // Update user variables.
    this.userName = options[0];
    this.userChoice = choice == Choice.EVEN ? "EVEN" : "ODD";
    this.opponent = AIFactory.createAI(difficulty);
    this.opponentName = AI.name;

    // Prompt user with welcome message.
    MessageCli.WELCOME_PLAYER.printMessage(userName);
  }

  public void play() {
    if (gameActive) {

      // Update state of game.
      this.playing = true;

      // Initialise round variables;
      int userInput, opponentInput;

      // Begin new round.
      this.rounds += 1;
      MessageCli.START_ROUND.printMessage(String.valueOf(rounds));

      // Find and update the majority BEFORE player chooses fingers (the opponent should NOT know
      // the polarity of the user's choice in the current round).
      updateMajority();

      // Prompt user for a valid input.
      userInput = userPickFingers();
      updateChoice(userInput);

      opponentInput = opponentPickFingers();

      int sum = opponentInput + userInput;
      endRound(sum);

    } else {
      MessageCli.GAME_NOT_STARTED.printMessage();
    }
  }

  // Update the overall polarity of the user's finger selections.
  public void updateMajority() {
    int evenCount = (int) this.choiceHistory.stream().filter(item -> item.equals("EVEN")).count();
    int length = this.choiceHistory.size();

    if (evenCount > (0.5 * length)) {
      this.majority = "EVEN";
    } else if (evenCount == (0.5 * length)) {
      this.majority = "EQUAL";
    } else {
      this.majority = "ODD";
    }
  }

  // Update the users wins and losses based on current game.
  public void updateScore() {
    this.userWins = (int) outcomeHistory.stream().filter(item -> item.equals("WIN")).count();
    this.userLosses = outcomeHistory.size() - userWins;
  }

  // Update the choice history of the user's finger selection.(0: EVEN, 1: ODD)
  public void updateChoice(int userInput) {
    String userPolarity = Utils.isEven(userInput) ? "EVEN" : "ODD";
    this.choiceHistory.add(userPolarity);
  }

  // Read and validate user's finger selection.
  public int userPickFingers() {
    String userInput;
    boolean flag = false;

    do {
      // Prompt user for input.
      MessageCli.ASK_INPUT.printMessage();
      userInput = Utils.scanner.nextLine();

      // Validate the user input if it is an integer between 0 and 5.
      if (!Utils.isInteger(userInput)
          || ((Integer.parseInt(userInput) < 0) || (Integer.parseInt(userInput) > 5))) {
        // Prompt user with error message.
        MessageCli.INVALID_INPUT.printMessage();
      } else {
        // Prompt user with success message.
        MessageCli.PRINT_INFO_HAND.printMessage(userName, userInput);
        flag = true;
      }
    } while (!flag); // Repeat until user enters valid input.

    // Return the user input.
    return Integer.parseInt(userInput);
  }

  public int opponentPickFingers() {
    boolean swapAI = false;

    // Check the outcome the previous round and decide if the AI type should be swapped.
    if (this.outcomeHistory.size() > 3) {
      swapAI = this.outcomeHistory.get(this.outcomeHistory.size() - 1) == "WIN" ? true : false;
    }

    // Fetch opponent input.
    int opponentInput =
        this.opponent.pickFingers(this.rounds, this.majority, this.userChoice, swapAI);

    // Prompt user with success message.
    MessageCli.PRINT_INFO_HAND.printMessage(this.opponentName, String.valueOf(opponentInput));

    return opponentInput;
  }

  // Find and prompt with the winner of the found.
  public void endRound(int sum) {
    String winner;
    String outcome;

    String sumPolarity = (Utils.isEven(sum)) ? "EVEN" : "ODD";

    if (sumPolarity == this.userChoice) {
      winner = userName;
      outcome = "WIN";
    } else {
      winner = opponentName;
      outcome = "LOST";
    }

    this.outcomeHistory.add(outcome);
    MessageCli.PRINT_OUTCOME_ROUND.printMessage(String.valueOf(sum), sumPolarity, winner);
  }

  // End the game.
  public void endGame() {
    // Only allow if in current game.
    if (gameActive) {
      gameActive = false;

      // Show stats.
      showStats();

      // Prompt with outcome.
      if (userWins > userLosses) {
        MessageCli.PRINT_END_GAME.printMessage(userName);
      } else if (userWins == userLosses) {
        MessageCli.PRINT_END_GAME_TIE.printMessage();
      } else {
        MessageCli.PRINT_END_GAME.printMessage(opponentName);
      }
    } else {
      MessageCli.GAME_NOT_STARTED.printMessage();
    }
  }

  // Show current wins and losses.
  public void showStats() {
    if (playing) {

      // Fetch current score;
      updateScore();

      // Print user wins and losses;
      MessageCli.PRINT_PLAYER_WINS.printMessage(
          userName, String.valueOf(userWins), String.valueOf(userLosses));

      // Print opponent's wins and losses.
      MessageCli.PRINT_PLAYER_WINS.printMessage(
          opponentName, String.valueOf(userLosses), String.valueOf(userWins));
    } else {
      MessageCli.GAME_NOT_STARTED.printMessage();
    }
  }
}
