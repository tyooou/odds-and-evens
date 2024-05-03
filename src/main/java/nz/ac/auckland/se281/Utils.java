package nz.ac.auckland.se281;

import java.util.Random;
import java.util.Scanner;

/**
 * Utility methods for common tasks.
 *
 * <p>CANNOT CHANGE EXISTING METHODS BUT YOU CAN ADD NEW ONES.
 */
public class Utils {

  /**
   * Random number generator with a fixed seed for reproducible results.
   *
   * <p>You are encouraged to use also other seeds to run your program, you code should work for any
   * seed
   */
  public static Random random = new Random(1);

  public static Scanner scanner = new Scanner(System.in);

  /**
   * Checks if the given string can be parsed into an integer.
   *
   * @param s The string to check.
   * @return true if the string can be parsed into an integer, false otherwise.
   */
  public static boolean isInteger(String s) {
    // check if null if, null return false
    if (s == null) {
      return false;
    }
    try {
      Integer.parseInt(s);
    } catch (NumberFormatException e) {
      // if exception is thrown then it is not a number
      return false;
    }
    return true;
  }

  /**
   * Checks if the given integer is even.
   *
   * @param n The integer to check.
   * @return true if the integer is even, false otherwise.
   */
  public static boolean isEven(int n) {
    return n % 2 == 0;
  }

  /**
   * Checks if the given integer is odd.
   *
   * @param n The integer to check.
   * @return true if the integer is odd, false otherwise.
   */
  public static boolean isOdd(int n) {
    return !isEven(n);
  }

  /**
   * Generates a random integer within the specified range [min, max].
   *
   * <p>Example:
   *
   * <p>min = 5 and max = 10, we return a random number from 5 to 10 inclusive
   *
   * @param min The minimum value of the range (inclusive).
   * @param max The maximum value of the range (inclusive).
   * @return A random integer within the specified range.
   */
  public static int getRandomNumberRange(int min, int max) {
    return random.nextInt(max - min + 1) + min;
  }

  /**
   * Generates a random even number from the set {0, 2, 4}.
   *
   * @return A random even number.
   */
  public static int getRandomEvenNumber() {
    return getRandomNumber(0, 2, 4);
  }

  /**
   * Generates a random odd number from the set {1, 3, 5}.
   *
   * @return A random odd number.
   */
  public static int getRandomOddNumber() {
    return getRandomNumber(1, 3, 5);
  }

  /**
   * Generates a random number from the specified set of numbers.
   *
   * @param num1 The first number in the set.
   * @param num2 The second number in the set.
   * @param num3 The third number in the set.
   * @return A random number from the specified set.
   */
  private static int getRandomNumber(int num1, int num2, int num3) {
    // Generate a random index between 0 and 2
    int randomIndex = random.nextInt(3);

    // Use switch case to return one of the input numbers based on the random index
    switch (randomIndex) {
      case 0:
        return num1;
      case 1:
        return num2;
      case 2:
        return num3;
      default:
        return 0;
    }
  }
}
