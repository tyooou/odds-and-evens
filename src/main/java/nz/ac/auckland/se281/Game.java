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

  /**
   * Creates a new game object, replacing the current game object (if any) with the new game object.
   * Of 16/05/2024, the previous game object is cleared. The status of the program is updated to
   * reflect that a game is active and the user is welcomed with a message.
   *
   * @param difficulty The difficulty of artificial intelligence opponent.
   * @param choice The user's choice between being EVEN or ODD.
   * @param options An array of the user's inputted personal details, e.g. name.
   */
  public void newGame(Difficulty difficulty, Choice choice, String[] options) {

    // Update state of game;
    gameActive = true;

    // Create new game
    gameObject = new GameObject(difficulty, choice, options);

    // Set global variables.
    this.userName = gameObject.getUserName();
    this.aiName = gameObject.getOpponentName();

    // Prompt user with welcome message.
    MessageCli.WELCOME_PLAYER.printMessage(userName);
  }

  /**
   * If a new game is active, start a new round. In a round, the user and opponent is asked to
   * select a number of fingers (between 0 and 5 inclusive). If the sum of the user and opponent's
   * fingers reflects the user's chosen polarity of EVEN or ODD, the user wins, otherwise, they
   * lose. Note that variables used to generate the opponent's number of fingers (e.g. majority,
   * wins/losses) is generated BEFORE the user inputs their choice of fingers, as the opponent
   * should NOT make a decisions based on information including the current round.
   */
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
      aiInput = getArtificialIntelligenceFingers();

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

  /**
   * Update the overall polarity of the user's history of number of fingers inputs. For example, if
   * the user has primarily picked even numbers, their majority is EVEN, and vice versa. If the user
   * has picked a equal ratio of even and odd numbers, their majority is EQUAL.
   */
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

  /**
   * Ask the user to input a number of fingers between 0 and 5 inclusive. If a invalid input is
   * entered, prompt the user with an error message and ask for another input (this is repeated
   * until a valid input is entered). After a valid input is entered, a success message is printed.
   *
   * @return user's number of fingers input as an integer.
   */
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

  /**
   * Fetch the opponent's input based based on the chosen difficulty of artificial intelligence.
   * After a valid input is fetched, a success message is printed.
   *
   * @return The opponent's number of fingers as an integer.
   */
  public int getArtificialIntelligenceFingers() {
    // Fetch AI input.
    int aiInput = gameObject.getArtificialIntelligence().getFingers(gameObject);

    // Prompt user with success message.
    MessageCli.PRINT_INFO_HAND.printMessage(aiName, String.valueOf(aiInput));

    // Return AI fingers.
    return aiInput;
  }

  /**
   * End the current game, only if a game is active. Show stats, find and print the outcome. Update
   * the status of the program to reflect that a new game is NOT active.
   */
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

      // Set state of game to no longer in-game.
      gameActive = false;

    } else {
      MessageCli.GAME_NOT_STARTED.printMessage();
    }
  }

  /**
   * Fetch and show current wins and losses of the user and opponent only if a game is active via a
   * message.
   */
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
