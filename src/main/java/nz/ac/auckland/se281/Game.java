package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Main.Choice;
import nz.ac.auckland.se281.Main.Difficulty;

/** This class represents the Game is the main entry point. */
public class Game {

  public int rounds;
  public String name;

  public void newGame(Difficulty difficulty, Choice choice, String[] options) {
    rounds = 0;
    name = "Valerio";
    MessageCli.WELCOME_PLAYER.printMessage(name);
  }

  public void play() {

    rounds += 1;
    MessageCli.START_ROUND.printMessage(String.valueOf(rounds));

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
  }

  public void endGame() {}

  public void showStats() {}
}
