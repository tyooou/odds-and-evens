package nz.ac.auckland.se281;

import static nz.ac.auckland.se281.Main.Command.*;
import static nz.ac.auckland.se281.MessageCli.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
  MainTest.Task1.class,
  MainTest.Task2.class,
  MainTest.Task3.class,
  MainTest.Task4.class,
  MainTest.Task5.class
})
public class MainTest {

  private static int getPlay(int round, String player, String output) {

    try {

      String roundOut =
          output
              .split(START_ROUND.getMessage(String.valueOf(round)))[1]
              .split("Player " + player + ": fingers")[1];
      Pattern pattern = Pattern.compile("-?\\d+");
      Matcher matcher = pattern.matcher(roundOut);
      matcher.find();
      return Integer.parseInt(matcher.group());

    } catch (Exception e) {
      throw new RuntimeException(
          "Something is wrong in your code, your should print something like this after each round"
              + MessageCli.PRINT_INFO_HAND.toString());
    }
  }

  private static String getOutputByRound(int round, String output) {
    try {
      return output.split("Start Round")[round];

    } catch (Exception e) {
      throw new RuntimeException(
          "Something is wrong in your code, your should print something like this after each round"
              + MessageCli.START_ROUND.toString());
    }
  }

  @FixMethodOrder(MethodSorters.NAME_ASCENDING)
  public static class Task1 extends CliTest {

    public Task1() {
      super(Main.class);
    }

    @Test
    public void T1_01_welcome_message() throws Exception {
      runCommands(NEW_GAME + " EASY ODD", "Valerio");
      assertContains(WELCOME_PLAYER.getMessage("Valerio"));
    }

    @Test
    public void T1_02_play_start_round() throws Exception {
      runCommands(
          NEW_GAME + " EASY EVEN",
          "Valerio",
          //
          PLAY,
          "1");
      assertContains(START_ROUND.getMessage("1"));
      assertDoesNotContain(START_ROUND.getMessage("0"));
      assertDoesNotContain(START_ROUND.getMessage("2"));
    }

    @Test
    public void T1_03_play_ask_for_input() throws Exception {
      runCommands(
          NEW_GAME + " EASY EVEN",
          "Valerio",
          //
          PLAY,
          "1");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
    }

    @Test
    public void T1_04_play_ask_for_input_wrong() throws Exception {
      runCommands(
          NEW_GAME + " EASY ODD",
          "Valerio",
          //
          PLAY,
          "345",
          "1");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      assertContains(INVALID_INPUT.getMessage());
    }

    @Test
    public void T1_05_play_ask_for_input_wrong() throws Exception {
      runCommands(
          NEW_GAME + " EASY ODD",
          "Valerio",
          //
          PLAY,
          "-9",
          "5");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      assertContains(INVALID_INPUT.getMessage());
    }

    @Test
    public void T1_06_play_ask_for_input_ok() throws Exception {
      runCommands(
          NEW_GAME + " EASY EVEN",
          "Valerio",
          //
          PLAY,
          "3");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int result = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(3, result);
      assertDoesNotContain(INVALID_INPUT.getMessage());
    }

    @Test
    public void T1_07_play_ask_for_input_ok2() throws Exception {
      runCommands(
          NEW_GAME + " EASY ODD",
          "Valerio",
          //
          PLAY,
          "5");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(5, res);
    }

    @Test
    public void T1_08_play_ask_for_input_ok_not_valid_two_rounds() throws Exception {
      runCommands(
          NEW_GAME + " EASY ODD",
          "Valerio",
          //
          PLAY,
          "3",
          //
          PLAY,
          "5");
      assertContains(WELCOME_PLAYER.getMessage("Valerio"));

      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(3, res);

      res = MainTest.getPlay(2, "Valerio", getCaptureOut());
      assertEquals(5, res);
    }

    @Test
    public void T1_09_play_ask_for_input_two_rounds_second_wrong() throws Exception {
      runCommands(
          NEW_GAME + " EASY EVEN",
          "Valerio",
          //
          PLAY,
          "3",
          //
          PLAY,
          "-34",
          "5");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(3, res);

      res = MainTest.getPlay(2, "Valerio", getCaptureOut());
      assertEquals(5, res);
      assertContains(INVALID_INPUT.getMessage());
    }

    @Test
    public void T1_10_play_ask_for_input_two_rounds_second_wrong() throws Exception {
      runCommands(
          NEW_GAME + " EASY ODD",
          "Valerio",
          //
          PLAY,
          "10000",
          "4",
          //
          PLAY,
          "5");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      assertContains(INVALID_INPUT.getMessage());
      int res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(4, res);
    }
  }

  @FixMethodOrder(MethodSorters.NAME_ASCENDING)
  public static class Task2 extends CliTest {

    public Task2() {
      super(Main.class);
    }

    @Test
    public void T2_01_play_ask_for_input_HAL() throws Exception {
      runCommands(
          NEW_GAME + " EASY ODD",
          "Valerio",
          //
          PLAY,
          "1");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(1, res);
      res = MainTest.getPlay(1, "HAL-9000", getCaptureOut());
      assertTrue(res > 0);
    }

    @Test
    public void T2_02_play_ask_for_input_HAL_random_seed() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " EASY ODD",
          "Valerio",
          //
          PLAY,
          "1");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(1, res);
      res = MainTest.getPlay(1, "HAL-9000", getCaptureOut());
      assertEquals(3, res);
    }

    @Test
    public void T2_03_play_ask_for_input_HAL9000_random_seed_two_rounds() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " EASY EVEN",
          "Valerio",
          //
          PLAY,
          "4"
          //
          ,
          PLAY,
          "0");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int res = MainTest.getPlay(2, "Valerio", getCaptureOut());
      assertEquals(0, res);
      res = MainTest.getPlay(2, "HAL-9000", getCaptureOut());
      assertEquals(4, res);
    }

    @Test
    public void T2_04_play_ask_for_input_HAL9000_random_seed_human_wins() throws Exception {
      Utils.random = new java.util.Random(0);
      runCommands(
          NEW_GAME + " EASY ODD",
          "Valerio",
          //
          PLAY,
          "5");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int res = MainTest.getPlay(1, "HAL-9000", getCaptureOut());
      assertEquals(0, res);
      assertContains(PRINT_OUTCOME_ROUND.getMessage("5", "ODD", "Valerio"));
    }

    @Test
    public void T2_05_play_ask_for_input_HAL9000_random_seed_HAL9000_wins() throws Exception {
      Utils.random = new java.util.Random(0);
      runCommands(
          NEW_GAME + " EASY ODD",
          "Valerio",
          //
          PLAY,
          "4");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int res = MainTest.getPlay(1, "HAL-9000", getCaptureOut());
      assertEquals(0, res);
      assertContains(PRINT_OUTCOME_ROUND.getMessage("4", "EVEN", "HAL-9000"));
    }
  }

  @FixMethodOrder(MethodSorters.NAME_ASCENDING)
  public static class Task3 extends CliTest {

    public Task3() {
      super(Main.class);
    }

    @Test
    public void T3_01_play_ask_for_input_HAL() throws Exception {
      runCommands(
          NEW_GAME + " MEDIUM ODD",
          "Valerio",
          //
          PLAY,
          "1",
          //
          PLAY,
          "1",
          //
          PLAY,
          "1",
          //
          PLAY,
          "1");

      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(1, res);
      res = MainTest.getPlay(1, "HAL-9000", getCaptureOut());
      assertTrue(res > 0);
    }

    @Test
    public void T3_02_play_ask_for_input_HAL_random_seed_even() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " MEDIUM ODD",
          "Valerio",
          //
          PLAY,
          "1",
          //
          PLAY,
          "3",
          //
          PLAY,
          "5",
          //
          PLAY,
          "1");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(1, res);
      res = MainTest.getPlay(4, "HAL-9000", getCaptureOut());
      assertTrue(Utils.isOdd(res));
    }

    @Test
    public void T3_03_play_ask_for_input_HAL_random_seed_odd() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " MEDIUM EVEN",
          "Valerio",
          //
          PLAY,
          "1",
          //
          PLAY,
          "3",
          //
          PLAY,
          "5",
          //
          PLAY,
          "4");
      assertContains(START_ROUND.getMessage("4"));
      assertContains(ASK_INPUT.getMessage());
      int res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(1, res);
      res = MainTest.getPlay(4, "HAL-9000", getCaptureOut());
      assertTrue(Utils.isEven(res));
    }

    @Test
    public void T3_04_play_ask_for_input_HAL_random_seed_even() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " MEDIUM ODD",
          "Valerio",
          //
          PLAY,
          "0",
          //
          PLAY,
          "2",
          //
          PLAY,
          "5",
          //
          PLAY,
          "2",
          //
          PLAY,
          "2");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(0, res);
      res = MainTest.getPlay(5, "HAL-9000", getCaptureOut());
      assertTrue(Utils.isEven(res));
    }

    @Test
    public void T3_05_play_ask_for_input_HAL_random_seed_odd() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " MEDIUM EVEN",
          "Valerio",
          //
          PLAY,
          "2",
          //
          PLAY,
          "0",
          //
          PLAY,
          "4",
          //
          PLAY,
          "4",
          //
          PLAY,
          "1");
      assertContains(START_ROUND.getMessage("5"));
      assertContains(ASK_INPUT.getMessage());
      int res = MainTest.getPlay(2, "Valerio", getCaptureOut());
      assertEquals(0, res);
      res = MainTest.getPlay(3, "Valerio", getCaptureOut());
      assertEquals(4, res);
      res = MainTest.getPlay(5, "HAL-9000", getCaptureOut());
      assertTrue(Utils.isOdd(res));
    }
  }

  @FixMethodOrder(MethodSorters.NAME_ASCENDING)
  public static class Task4 extends CliTest {

    public Task4() {
      super(Main.class);
    }

    @Test
    public void T4_01_play_ask_for_input_HAL() throws Exception {
      runCommands(
          NEW_GAME + " HARD ODD",
          "Valerio",
          //
          PLAY,
          "1",
          //
          PLAY,
          "1",
          //
          PLAY,
          "1",
          //
          PLAY,
          "1",
          //
          PLAY,
          "1",
          //
          PLAY,
          "1",
          //
          PLAY,
          "1");

      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(1, res);
      res = MainTest.getPlay(1, "HAL-9000", getCaptureOut());
      assertTrue(res > 0);
    }

    @Test
    public void T4_02_play_ask_for_input_HAL_random_seed_even() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " HARD ODD",
          "Valerio",
          //
          PLAY,
          "1",
          //
          PLAY,
          "3",
          //
          PLAY,
          "5",
          //
          PLAY,
          "3",
          //
          PLAY,
          "5",
          //
          PLAY,
          "5");
      assertContains(START_ROUND.getMessage("6"));
      assertContains(ASK_INPUT.getMessage());
      int res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(1, res);
      res = MainTest.getPlay(6, "HAL-9000", getCaptureOut());
      assertTrue(Utils.isOdd(res));
    }

    @Test
    public void T4_03_play_ask_for_input_HAL_random_seed_odd() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " HARD ODD",
          "Valerio",
          //
          PLAY,
          "1",
          //
          PLAY,
          "3",
          //
          PLAY,
          "5",
          //
          PLAY,
          "3",
          //
          PLAY,
          "5",
          //
          PLAY,
          "5");
      assertContains(START_ROUND.getMessage("6"));
      assertContains(ASK_INPUT.getMessage());
      int res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(1, res);
      res = MainTest.getPlay(6, "HAL-9000", getCaptureOut());
      assertTrue(Utils.isOdd(res));
    }

    @Test
    public void T4_04_play_ask_for_input_HAL_random_seed_even() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " HARD EVEN",
          "Valerio",
          //
          PLAY,
          "0",
          //
          PLAY,
          "2",
          //
          PLAY,
          "5",
          //
          PLAY,
          "3",
          //
          PLAY,
          "3",
          //
          PLAY,
          "3");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(0, res);
      res = MainTest.getPlay(6, "HAL-9000", getCaptureOut());
      assertTrue(Utils.isEven(res));
    }

    @Test
    public void T4_05_play_ask_for_input_HAL_random_seed_odd() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " HARD ODD",
          "Valerio",
          //
          PLAY,
          "2",
          //
          PLAY,
          "0",
          //
          PLAY,
          "4",
          //
          PLAY,
          "4",
          //
          PLAY,
          "1",
          //
          PLAY,
          "1");
      assertContains(START_ROUND.getMessage("5"));
      assertContains(ASK_INPUT.getMessage());
      int res = MainTest.getPlay(2, "Valerio", getCaptureOut());
      assertEquals(0, res);
      res = MainTest.getPlay(3, "Valerio", getCaptureOut());
      assertEquals(4, res);
      res = MainTest.getPlay(6, "HAL-9000", getCaptureOut());
      assertTrue(Utils.isEven(res));
    }
  }

  @FixMethodOrder(MethodSorters.NAME_ASCENDING)
  public static class Task5 extends CliTest {

    public Task5() {
      super(Main.class);
    }

    @Test
    public void T5_01_play_before_start() throws Exception {
      runCommands(PLAY);
      assertContains(MessageCli.GAME_NOT_STARTED.getMessage());
    }

    @Test
    public void T5_02_start_game_twice() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " EASY ODD",
          "Valerio",
          //
          PLAY,
          "4",
          NEW_GAME + " EASY ODD",
          "Valerio",
          //
          PLAY,
          "3");
      assertContains(WELCOME_PLAYER.getMessage("Valerio"));
      assertContains(START_ROUND.getMessage("1"));
      assertDoesNotContain(START_ROUND.getMessage("2"));
      assertDoesNotContain(MessageCli.GAME_NOT_STARTED.getMessage());
    }

    @Test
    public void T5_03_hal_wins_one_round() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " EASY EVEN",
          "Valerio",
          //
          PLAY,
          "0", //
          END_GAME);
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      assertTrue(
          MainTest.getOutputByRound(1, getOutput())
              .contains(PRINT_OUTCOME_ROUND.getMessage("3", "ODD", "HAL-9000")));
      assertContains(PRINT_END_GAME.getMessage("HAL-9000"));
    }

    @Test
    public void T5_04_human_wins_one_round() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " EASY ODD",
          "Valerio",
          //
          PLAY,
          "2",
          END_GAME);
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      assertTrue(
          MainTest.getOutputByRound(1, getOutput())
              .contains(PRINT_OUTCOME_ROUND.getMessage("5", "ODD", "Valerio")));
      assertContains(PRINT_END_GAME.getMessage("Valerio"));
      assertDoesNotContain(PRINT_END_GAME.getMessage("HAL-9000"));
    }

    @Test
    public void T5_05_cannot_play_game_not_start() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " EASY EVEN",
          "Valerio",
          //
          PLAY,
          "1"
          //
          ,
          END_GAME,
          PLAY);
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      assertContains(GAME_NOT_STARTED.getMessage());
    }

    @Test
    public void T5_06_human_wins_two_rounds() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " EASY ODD",
          "Valerio",
          //
          PLAY,
          "3"
          //
          ,
          PLAY,
          "1",
          END_GAME);
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      assertContains(PRINT_END_GAME_TIE.getMessage());
    }

    @Test
    public void T5_07_show_stats_fail() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(SHOW_STATS);
      assertContains(GAME_NOT_STARTED.getMessage());
    }

    @Test
    public void T5_08_show_stats_ok() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " EASY EVEN",
          "Valerio",
          //
          PLAY,
          "0",
          SHOW_STATS
          //
          ,
          PLAY,
          "3",
          SHOW_STATS);
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      assertTrue(
          MainTest.getOutputByRound(1, getOutput())
              .contains(PRINT_OUTCOME_ROUND.getMessage("3", "ODD", "HAL-9000")));
      assertTrue(
          MainTest.getOutputByRound(1, getOutput())
              .contains(PRINT_PLAYER_WINS.getMessage("Valerio", "0", "1")));
      assertTrue(
          MainTest.getOutputByRound(1, getOutput())
              .contains(PRINT_PLAYER_WINS.getMessage("HAL-9000", "1", "0")));
      MainTest.getOutputByRound(2, getOutput())
          .contains(PRINT_OUTCOME_ROUND.getMessage("7", "ODD", "HAL-9000"));
      assertTrue(
          MainTest.getOutputByRound(2, getOutput())
              .contains(PRINT_PLAYER_WINS.getMessage("Valerio", "0", "2")));
      assertTrue(
          MainTest.getOutputByRound(2, getOutput())
              .contains(PRINT_PLAYER_WINS.getMessage("HAL-9000", "2", "0")));
    }
  }

  @FixMethodOrder(MethodSorters.NAME_ASCENDING)
  public static class YourTests extends CliTest {

    public YourTests() {
      super(Main.class);
    }

    @Test
    public void yourtest() throws Exception {}
  }
}
