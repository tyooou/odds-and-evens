package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Main.Choice;
import nz.ac.auckland.se281.Main.Difficulty;

/** This class represents the Game is the main entry point. */
public class Game {

  public int rounds;
  public String name;
  public Choice choice;
  public AI opponent;

  public void newGame(Difficulty difficulty, Choice choice, String[] options) {
    rounds = 0;
    this.choice = choice;
    name = options[0];

    opponent = AIFactory.createAI(difficulty);

    MessageCli.WELCOME_PLAYER.printMessage(name);
  }

  public void play() {
    rounds += 1;
    MessageCli.START_ROUND.printMessage(String.valueOf(rounds));

    String userInput, opponentInput;
    int sum;
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

    opponentInput = String.valueOf(opponent.pickFingers());
    MessageCli.PRINT_INFO_HAND.printMessage(AI.name, opponentInput);

    sum = Integer.parseInt(opponentInput) + Integer.parseInt(userInput);

    String winner = name;
    String polarity = Utils.isEven(Integer.parseInt(userInput)) ? "EVEN" : "ODD";

    if ((Utils.isEven(Integer.parseInt(userInput)) && choice != Choice.EVEN)
        || (Utils.isOdd(Integer.parseInt(userInput)) && choice != Choice.ODD)) {
      winner = AI.name;
    }
    ;

    MessageCli.PRINT_OUTCOME_ROUND.printMessage(String.valueOf(sum), polarity, winner);
  }

  public void endGame() {}

  public void showStats() {}
}
