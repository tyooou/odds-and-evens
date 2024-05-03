package nz.ac.auckland.se281;

import java.util.Scanner; // Import the Scanner class.
import nz.ac.auckland.se281.Main.Choice;
import nz.ac.auckland.se281.Main.Difficulty;

/** This class represents the Game is the main entry point. */
public class Game {

  public int rounds;

  public void newGame(Difficulty difficulty, Choice choice, String[] options) {
    rounds = 0;

    MessageCli.WELCOME_PLAYER.printMessage(options[0]);
  }

  public void play() {
    rounds += 1;
    MessageCli.START_ROUND.printMessage(String.valueOf(rounds));

    Scanner scanner = new Scanner(System.in);
    MessageCli.ASK_INPUT.printMessage();
  }

  public void endGame() {}

  public void showStats() {}
}
