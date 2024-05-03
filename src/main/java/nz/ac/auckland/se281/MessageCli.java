package nz.ac.auckland.se281;

/** You cannot modify this class!. */
public enum MessageCli {
  COMMAND_NOT_FOUND(
      "Error! Command not found! (run 'help' for the list of available commands): \"%s\""),
  WRONG_ARGUMENT_COUNT(
      "Error! Incorrect number of arguments provided. Expected %s argument%s for the \"%s\""
          + " command"),
  NO_COMMAND("Error! You did not give any command :)"),
  END("You closed the terminal. Goodbye."),
  INVALID_DIFFICULTY(
      "Error! Incorrect difficutly level. The possible difficulty leves are EASY,"
          + " MEDIUM, and H̉ARD"),
  INVALID_CHOICE("Error! You should choose between ODD or EVEN"),
  ASK_INPUT("Give <fingers> and press enter"),
  INVALID_INPUT(
      "Error! Invalid input, you should give one integer number between 0 and 5 (inclusive),"
          + " please try again"),
  GAME_NOT_STARTED("Error! Game not started yet. Please start a new game first"),
  WELCOME_PLAYER("Welcome %s!"),
  START_ROUND("Start Round #%s:"),
  PRINT_INFO_HAND("Player %s: fingers: %s"),
  PRINT_OUTCOME_ROUND("The sum is : %s, is %s, player %s wins!"),
  PRINT_PLAYER_WINS("%s won %s rounds and lost %s rounds"),
  PRINT_END_GAME("Player %s won the game!"),
  PRINT_END_GAME_TIE("Tie!");
  private final String msg;

  private MessageCli(final String msg) {
    this.msg = msg;
  }

  /**
   * Generates a formatted message using the provided arguments.
   *
   * @param args The arguments to replace placeholders in the message.
   * @return The formatted message.
   */
  public String getMessage(final String... args) {
    String tmpMessage = msg;

    for (final String arg : args) {
      tmpMessage = tmpMessage.replaceFirst("%s", arg);
    }

    return tmpMessage;
  }

  public void printMessage(final String... args) {
    System.out.println(getMessage(args));
  }

  @Override
  public String toString() {
    return msg;
  }
}
