package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** You cannot modify this class!. */
public class Main {

  /** Enum representing the difficulty levels of the game. */
  public enum Difficulty {
    EASY,
    MEDIUM,
    HARD;
  }

  /** Enum representing the choices (Odd or Even) in the game. */
  public enum Choice {
    ODD,
    EVEN;
  }

  /** Enum representing different commands available in the game. */
  public enum Command {
    NEW_GAME(
        2,
        "start a new game <DIFFICULTY_LEVEL> <ODD_OR_EVEN>\n"
            + "\t \t \t \t \t \tDIFFICULTY_LEVEL:  EASY \n"
            + "\t \t \t \t \t \t \t \t   MEDIUM \n"
            + "\t \t \t \t \t \t \t \t   HARD ",
        "What is your name?"),
    PLAY(0, "Play a round"),
    SHOW_STATS(0, "Show the current statistics"),
    END_GAME(0, "End the game"),
    HELP(0, "Print usage"),
    EXIT(0, "Exit the application");

    private final int numArgs;
    private final String message;
    private final String[] optionPrompts;

    private Command(final int numArgs, final String message) {
      this(numArgs, message, new String[] {});
    }

    private Command(final int numArgs, final String message, final String... optionPrompts) {
      this.numArgs = numArgs;
      this.message = message;
      this.optionPrompts = optionPrompts;
    }

    /**
     * Checks if the command requires arguments.
     *
     * @return true if the command requires arguments, false otherwise.
     */
    public boolean hasArguments() {
      return numArgs > 0;
    }

    /**
     * Gets the number of arguments required for the command.
     *
     * @return The number of arguments required.
     */
    public int getNumArgs() {
      return numArgs;
    }

    /**
     * Checks if the command has options.
     *
     * @return true if the command has options, false otherwise.
     */
    public boolean hasOptions() {
      return optionPrompts.length > 0;
    }

    /**
     * Gets the number of options for the command.
     *
     * @return The number of options.
     */
    public int getNumOptions() {
      return optionPrompts.length;
    }

    /**
     * Gets the prompt for the option at the specified index.
     *
     * @param index The index of the option.
     * @return The prompt for the option.
     */
    public String getOptionPrompt(final int index) {
      return optionPrompts[index];
    }
  }

  private static final String COMMAND_PREFIX = "281-odd-or-even> ";

  public static void main(final String[] args) {
    new Main(new Game()).start();
  }

  /**
   * Generates the help message containing information about available commands.
   *
   * @return The help message.
   */
  public static String help() {
    final StringBuilder sb = new StringBuilder();

    for (final Command command : Command.values()) {
      sb.append(command).append("\t");

      // Add extra padding to align the argument counts
      // if the command name is less than the tab width.
      if (command.toString().length() < 8) {
        sb.append("\t");
      }

      if (command.numArgs > 0) {
        sb.append("[")
            .append(command.numArgs)
            .append(" argument" + (command.numArgs > 1 ? "s" : "") + "]");
      } else {
        sb.append("[no args]");
      }

      sb.append("\t").append(command.message).append(System.lineSeparator());
    }

    return sb.toString();
  }

  private static String[] splitWithQuotes(String input) {
    List<String> items = new ArrayList<>();

    // Match based on spaces, while respecting words surrounded by single quotes
    Matcher matcher = Pattern.compile("('(?:[^']+|'')*'\\S*|\\S+)").matcher(input);

    while (matcher.find()) {
      String matched = matcher.group(1);

      // Remove the surrounding quotes
      if (matched.startsWith("'") && matched.endsWith("'")) {
        matched = matched.substring(1, matched.length() - 1);
      }

      items.add(matched);
    }

    return items.toArray(new String[0]);
  }

  private static void printBanner() {
    // https://patorjk.com/software/taag/
    // https://www.freeformatter.com/java-dotnet-escape.html#before-output

    System.out.println(
        "\n"
            + "  /$$$$$$  /$$$$$$$  /$$$$$$$         /$$$$$$  /$$$$$$$        /$$$$$$$$ /$$    /$$"
            + " /$$$$$$$$ /$$   /$$\n"
            + " /$$__  $$| $$__  $$| $$__  $$       /$$__  $$| $$__  $$      | $$_____/| $$   | $$|"
            + " $$_____/| $$$ | $$\n"
            + "| $$  \\ $$| $$  \\ $$| $$  \\ $$      | $$  \\ $$| $$  \\ $$      | $$      | $$  "
            + " | $$| $$      | $$$$| $$\n"
            + "| $$  | $$| $$  | $$| $$  | $$      | $$  | $$| $$$$$$$/      | $$$$$   |  $$ / $$/|"
            + " $$$$$   | $$ $$ $$\n"
            + "| $$  | $$| $$  | $$| $$  | $$      | $$  | $$| $$__  $$      | $$__/    \\  $$ $$/"
            + " | $$__/   | $$  $$$$\n"
            + "| $$  | $$| $$  | $$| $$  | $$      | $$  | $$| $$  \\ $$      | $$        \\  $$$/ "
            + " | $$      | $$\\  $$$\n"
            + "|  $$$$$$/| $$$$$$$/| $$$$$$$/      |  $$$$$$/| $$  | $$      | $$$$$$$$   \\  $/  "
            + " | $$$$$$$$| $$ \\  $$\n"
            + " \\______/ |_______/ |_______/        \\______/ |__/  |__/      |________/    \\_/  "
            + "  |________/|__/  \\__/\n"
            + "                                                                                    "
            + "                   \n"
            + "                                                                                    "
            + "                   \n"
            + "                                                                                    "
            + "                   \n");
  }

  private final Game game;

  public Main(final Game game) {
    this.game = game;
  }

  /** start the game of odds and even. */
  public void start() {
    printBanner();
    System.out.println(help());

    String command;

    // Prompt and process commands until the exit command.
    do {
      System.out.print(COMMAND_PREFIX);
      command = Utils.scanner.nextLine().trim();
    } while (processCommand(command));
  }

  private boolean processCommand(String input) {
    // Remove whitespace at the beginning and end of the input.
    input = input.trim();

    final String[] args = splitWithQuotes(input);

    // In case the user pressed "Enter" without typing anything
    if (args.length == 0) {
      return true;
    }

    // Allow any case, and dashes to be used instead of underscores.
    final String commandStr = args[0].toUpperCase().replaceAll("-", "_");

    final Command command;

    try {
      // Command names correspond to the enum names.
      command = Command.valueOf(commandStr);
    } catch (final Exception e) {
      MessageCli.COMMAND_NOT_FOUND.printMessage(commandStr);
      return true;
    }

    // Check if the number of arguments provided don't match up as required for that command
    if (!checkArgs(command, args)) {
      MessageCli.WRONG_ARGUMENT_COUNT.printMessage(
          String.valueOf(command.getNumArgs()), command.getNumArgs() != 1 ? "s" : "", commandStr);
      return true;
    }

    switch (command) {
      case NEW_GAME:
        String difficulty = args[1].toUpperCase();

        try {
          // Command names correspond to the enum names.
          Difficulty.valueOf(difficulty);
        } catch (final Exception e) {
          MessageCli.INVALID_DIFFICULTY.printMessage();
          break;
        }

        String choice = args[2].toUpperCase();

        try {
          // Command names correspond to the enum names.
          Choice.valueOf(choice);
        } catch (final Exception e) {
          MessageCli.INVALID_CHOICE.printMessage();
          break;
        }
        // only if the input is fine, start a new game
        game.newGame(
            Difficulty.valueOf(difficulty), Choice.valueOf(choice), processOptions(command));
        break;
      case PLAY:
        game.play();
        break;
      case SHOW_STATS:
        game.showStats();
        break;
      case END_GAME:
        game.endGame();
        break;
      case HELP:
        System.out.println(help());
        break;
      case EXIT:
        MessageCli.END.printMessage();
        return false;
    }

    // Signal that another command is expected.
    return true;
  }

  private String[] processOptions(final Command command) {
    final String[] options = new String[command.getNumOptions()];

    // Prompt the user for each option
    for (int i = 0; i < command.getNumOptions(); i++) {
      System.out.print("\t" + command.getOptionPrompt(i) + ": ");
      options[i] = Utils.scanner.nextLine().trim();
    }
    return options;
  }

  private boolean checkArgs(final Command command, final String[] args) {
    return command.getNumArgs() == args.length - 1;
  }
}
