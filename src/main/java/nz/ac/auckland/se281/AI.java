package nz.ac.auckland.se281;

import java.util.List;

public interface AI {
  public String name = "HAL-9000";

  public int pickFingers(int rounds, List<Integer> gameHistory);
}
