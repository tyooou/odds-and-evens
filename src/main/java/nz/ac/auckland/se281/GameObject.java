package nz.ac.auckland.se281;

import java.util.*;
import nz.ac.auckland.se281.Main.Choice;
import nz.ac.auckland.se281.Main.Difficulty;

public class GameObject {

  // Initialise game variables.
  private int rounds;
  private int userWins;
  private String userName;
  private String userChoice;
  private String opponentName;
  private AI opponent;

  // Initialise game history.
  private List<String> choiceHistory = new ArrayList<String>();
  private List<String> outcomeHistory = new ArrayList<String>();
  private String majority;

  public GameObject(Difficulty difficulty, Choice choice, String[] options) {
    // Intialise new game variables.
    this.rounds = 0;
    this.userWins = 0;
    this.choiceHistory.clear();
    this.outcomeHistory.clear();

    // Intialise user variables.
    this.userName = options[0];
    this.userChoice = choice == Choice.EVEN ? "EVEN" : "ODD";
    this.opponent = AIFactory.createAI(difficulty);
    this.opponentName = "HAL-9000";
  }

  public String getUserName() {
    return userName;
  }

  public AI getAI() {
    return opponent;
  }

  public String getAIName() {
    return opponentName;
  }

  public int getRounds() {
    return rounds;
  }

  public void setRounds(int rounds) {
    this.rounds = rounds;
  }

  public String getMajority() {
    return majority;
  }

  public void setMajority(String majority) {
    this.majority = majority;
  }

  public String getChoice() {
    return userChoice;
  }

  public List<String> getChoiceHistory() {
    return choiceHistory;
  }

  public void addChoiceHistory(String polarity) {
    choiceHistory.add(polarity);
  }

  public int fetchWins() {
    userWins = (int) outcomeHistory.stream().filter(item -> item.equals("WIN")).count();
    return userWins;
  }

  public int fetchLosses() {
    return getOutcomeHistory().size() - fetchWins();
  }

  public List<String> getOutcomeHistory() {
    return outcomeHistory;
  }

  public void addOutcomeHistory(String outcome) {
    outcomeHistory.add(outcome);
  }
}
