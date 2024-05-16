package nz.ac.auckland.se281;

import java.util.List;
import nz.ac.auckland.se281.Main.Choice;
import nz.ac.auckland.se281.Main.Difficulty;

/** This class represents the Game is the main entry point. */
public class Game {

  // Initialise game variables.
  private boolean gameActive = false;
  private GameObject gameObject;
  private String userName;
  private String aiName;

  // Create new game.
  public void newGame(Difficulty difficulty, Choice choice, String[] options) {

    // Update state of game;
    gameActive = true;

    // Create new game
    gameObject = new GameObject(difficulty, choice, options);

    // Set global variables.
    this.userName = gameObject.getUserName();
    this.aiName = gameObject.getAIName();

    // Prompt user with welcome message.
    MessageCli.WELCOME_PLAYER.printMessage(userName);
  }

  // Play a round.
  public void play() {
    // If game is active.
    if (gameActive) {

      // Initialise round variables;
      int userInput;
      int aiInput;

      // Begin new round.
      int round = gameObject.getRounds() + 1;
      gameObject.setRounds(round);

      MessageCli.START_ROUND.printMessage(String.valueOf(round));

      // Find and update the majority BEFORE player chooses fingers (the AI should NOT know
      // the polarity of the user's choice in the current round).
      updateMajority();

      // Prompt user for a valid input.
      userInput = getUserFingers();
      String userInputPolarity = Utils.isEven(userInput) ? "EVEN" : "ODD";
      gameObject.addChoiceHistory(userInputPolarity);

      // Fetch AI input.
      aiInput = getAIFingers();

      // Find sum of inputs and its polarity.
      int sum = aiInput + userInput;
      String sumPolarity = (Utils.isEven(sum)) ? "EVEN" : "ODD";

      // Find winner.
      if (sumPolarity == gameObject.getChoice()) {
        MessageCli.PRINT_OUTCOME_ROUND.printMessage(String.valueOf(sum), sumPolarity, userName);
        gameObject.addOutcomeHistory("WIN");
      } else {
        MessageCli.PRINT_OUTCOME_ROUND.printMessage(String.valueOf(sum), sumPolarity, aiName);
        gameObject.addOutcomeHistory("LOSS");
      }

    } else {
      // Prompt user with game not started error.
      MessageCli.GAME_NOT_STARTED.printMessage();
    }
  }

  // Update the overall polarity of the user's finger selections.
  public void updateMajority() {
    // Initialise majority variables.
    List<String> choiceHistory = this.gameObject.getChoiceHistory();
    int evenCount = (int) choiceHistory.stream().filter(item -> item.equals("EVEN")).count();
    int length = choiceHistory.size();

    // Find majority.
    if (evenCount > (0.5 * length)) {
      gameObject.setMajority("EVEN");
    } else if (evenCount == (0.5 * length)) {
      gameObject.setMajority("EQUAL");
    } else {
      gameObject.setMajority("ODD");
    }
  }

  // Read and validate user's finger selection.
  public int getUserFingers() {
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

    // Return the valid user input.
    return Integer.parseInt(userInput);
  }

  // Fetch AI's finger selection.
  public int getAIFingers() {
    // Fetch AI input.
    int aiInput = gameObject.getAI().getFingers(gameObject);

    // Prompt user with success message.
    MessageCli.PRINT_INFO_HAND.printMessage(aiName, String.valueOf(aiInput));

    // Return AI fingers.
    return aiInput;
  }

  // End the game.
  public void endGame() {
    // Only allow if in current game.
    if (gameActive) {

      showStats();

      int userWins = gameObject.fetchWins();
      int userLosses = gameObject.fetchLosses();

      // Prompt with outcome.
      if (userWins > userLosses) {
        MessageCli.PRINT_END_GAME.printMessage(userName);
      } else if (userWins == userLosses) {
        MessageCli.PRINT_END_GAME_TIE.printMessage();
      } else {
        MessageCli.PRINT_END_GAME.printMessage(aiName);
      }

      // Set state of game to no longer game.
      gameActive = false;

    } else {
      MessageCli.GAME_NOT_STARTED.printMessage();
    }
  }

  // Show current wins and losses.
  public void showStats() {
    if (gameActive) {

      // Fetch current score;
      int userWins = gameObject.fetchWins();
      int userLosses = gameObject.fetchLosses();

      // Print user wins and losses;
      MessageCli.PRINT_PLAYER_WINS.printMessage(
          userName, String.valueOf(userWins), String.valueOf(userLosses));

      // Print opponent's wins and losses.
      MessageCli.PRINT_PLAYER_WINS.printMessage(
          aiName, String.valueOf(userLosses), String.valueOf(userWins));
    } else {
      MessageCli.GAME_NOT_STARTED.printMessage();
    }
  }
}
