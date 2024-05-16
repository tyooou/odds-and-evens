package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;
import nz.ac.auckland.se281.Main.Choice;
import nz.ac.auckland.se281.Main.Difficulty;

public class GameObject {

  // Initialise game variables.
  private int rounds;
  private int userWins;
  private String userName;
  private String userChoice;
  private String opponentName;
  private ArtificialIntelligence opponent;

  // Initialise game history.
  private List<String> choiceHistory = new ArrayList<String>();
  private List<String> outcomeHistory = new ArrayList<String>();
  private String majority;

  /**
   * Constructor for class GameObject.
   *
   * @param difficulty difficulty of the opponent.
   * @param choice choice between EVEN or ODD to be.
   * @param options array of the user's inputted personal details, e.g. name.
   */
  public GameObject(Difficulty difficulty, Choice choice, String[] options) {
    // Intialise new game variables.
    this.rounds = 0;
    this.userWins = 0;

    // Intialise user variables.
    this.userName = options[0];
    this.userChoice = choice == Choice.EVEN ? "EVEN" : "ODD";
    this.opponent = ArtificialIntelligenceFactory.createArtificialIntelligence(difficulty);
    this.opponentName = "HAL-9000";
  }

  /**
   * @return user's name as a String.
   */
  public String getUserName() {
    return userName;
  }

  /**
   * @return intelligence of the opponent as an ArtificialIntelligence.
   */
  public ArtificialIntelligence getArtificialIntelligence() {
    return opponent;
  }

  /**
   * @return opponent's name as a String.
   */
  public String getOpponentName() {
    return opponentName;
  }

  /**
   * @return number of the current round as an int.
   */
  public int getRounds() {
    return rounds;
  }

  /**
   * @param rounds number of rounds to set.
   */
  public void setRounds(int rounds) {
    this.rounds = rounds;
  }

  /**
   * @return majority of the user's history of number of fingers inputs as a String.
   */
  public String getMajority() {
    return majority;
  }

  /**
   * @param majority majority of the user's history of number of fingers inputs to be set.
   */
  public void setMajority(String majority) {
    this.majority = majority;
  }

  /**
   * @return user's choice of EVEN or ODD at the start of a game as a String.
   */
  public String getChoice() {
    return userChoice;
  }

  /**
   * @return the history of the user's polarity of number of fingers inputs as a List<String> (i.e.
   *     EVEN or ODD).
   */
  public List<String> getChoiceHistory() {
    return choiceHistory;
  }

  /**
   * @param polarity polarity of the user's current number of fingers input to add to history.
   */
  public void addChoiceHistory(String polarity) {
    choiceHistory.add(polarity);
  }

  /**
   * @return user's total number of wins as an int.
   */
  public int fetchWins() {
    userWins = (int) outcomeHistory.stream().filter(item -> item.equals("WIN")).count();
    return userWins;
  }

  /**
   * @return user's total number of losses as a int.
   */
  public int fetchLosses() {
    return getOutcomeHistory().size() - fetchWins();
  }

  /**
   * @return the win and loss history of the user as a List<String> (i.e. WIN or LOSS).
   */
  public List<String> getOutcomeHistory() {
    return outcomeHistory;
  }

  /**
   * @param outcome outcome of the current round from the perspective of the user to add to history.
   */
  public void addOutcomeHistory(String outcome) {
    outcomeHistory.add(outcome);
  }
}
