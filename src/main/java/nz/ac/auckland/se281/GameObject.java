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
   * Constructor for class GameObject. Ensures that all appropriate variables are reset (e.g. the
   * previous game, if any, does not affect the current game).
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
   * Get the name of the user.
   *
   * @return user's name as a String.
   */
  public String getUserName() {
    return userName;
  }

  /**
   * Get the artificial intelligence of the opponent.
   *
   * @return intelligence of the opponent as an ArtificialIntelligence.
   */
  public ArtificialIntelligence getArtificialIntelligence() {
    return opponent;
  }

  /**
   * Get the name of the opponent.
   *
   * @return opponent's name as a String.
   */
  public String getOpponentName() {
    return opponentName;
  }

  /**
   * Get current number of rounds.
   *
   * @return number of the current round as an int.
   */
  public int getRounds() {
    return rounds;
  }

  /**
   * Registers the number of round the game object is in. This number is displayed at the start of
   * every new round creation.
   *
   * @param rounds number of rounds to set.
   */
  public void setRounds(int rounds) {
    this.rounds = rounds;
  }

  /**
   * Get the majority of the user's history of number of fingers inputs.
   *
   * @return majority of the user's history of number of fingers inputs as a String.
   */
  public String getMajority() {
    return majority;
  }

  /**
   * Registers the majority of the user's history of number of fingers inputs. This is used by the
   * HARD difficulty opponent to generate "smarter" numbers of fingers to increase its likelihood of
   * winning against the user.
   *
   * @param majority majority of the user's history of number of fingers inputs to be set.
   */
  public void setMajority(String majority) {
    this.majority = majority;
  }

  /**
   * Get the user's choice of EVEN or ODD, which was set the start of a game.
   *
   * @return user's choice of EVEN or ODD (set at the start of a game) as a String.
   */
  public String getChoice() {
    return userChoice;
  }

  /**
   * Get the history of the user's polarity of number of fingers inputs (i.e. EVEN or ODD).
   *
   * @return the history of the user's polarity of number of fingers inputs as a List<String>.
   */
  public List<String> getChoiceHistory() {
    return choiceHistory;
  }

  /**
   * Registers the polarity of the user's current number of fingers input to the history. This is
   * used to find the polarity majority of the user's history of numbers of fingers inputs.
   *
   * @param polarity polarity of the user's current number of fingers input to add to history.
   */
  public void addChoiceHistory(String polarity) {
    choiceHistory.add(polarity);
  }

  /**
   * Get the user's total number of wins.
   *
   * @return user's total number of wins as an int.
   */
  public int fetchWins() {
    userWins = (int) outcomeHistory.stream().filter(item -> item.equals("WIN")).count();
    return userWins;
  }

  /**
   * Get the user's total number of losses.
   *
   * @return user's total number of losses as a int.
   */
  public int fetchLosses() {
    return getOutcomeHistory().size() - fetchWins();
  }

  /**
   * Get the win and loss history of the user (i.e. WIN or LOSS).
   *
   * @return the win and loss history of the user as a List<String>.
   */
  public List<String> getOutcomeHistory() {
    return outcomeHistory;
  }

  /**
   * Register the outcome of the current round from the perspective of the user. This is used to
   * find the user's (and subsequently, the opponent's) number of wins and losses.
   *
   * @param outcome outcome of the current round from the perspective of the user to add to history.
   */
  public void addOutcomeHistory(String outcome) {
    outcomeHistory.add(outcome);
  }
}
