
import java.util.List;
import java.util.Scanner;

/**
 * Utility class holding repetitive, simple operations shared across the
 * application: reading and validating console input, and printing items in a
 * table format.
 *
 * Convention: every input is first read as a raw String, then validated, and
 * only parsed into its target type (int/double) once it is known to be valid.
 */
public class Utils {

    private Utils() {
        // Prevent instantiation; this is a static utility class.
    }

    /**
     * Reads a line of input and keeps re-prompting until it is non-empty.
     */
    public static String readNonEmptyString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().replaceAll("\\s+", " ");
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    /**
     * Reads a String and keeps re-prompting until it matches (case-insensitive)
     * one of the given valid options. Returns the value exactly as it appears
     * in validOptions (normalized casing), not what the user typed.
     */
    public static String readChoiceFromOptions(Scanner scanner, String prompt, String... validOptions) {
        while (true) {
            String input = readNonEmptyString(scanner, prompt);
            for (String option : validOptions) {
                if (option.equalsIgnoreCase(input)) {
                    return option;
                }
            }
            System.out.println("Invalid input. Please enter one of the following: " + String.join(" / ", validOptions));
        }
    }

    /**
     * Reads a String, validates it parses to a non-negative whole number, and
     * returns it as an int. Re-prompts on invalid input.
     */
    public static int readValidInt(Scanner scanner, String prompt) {
        while (true) {
            String input = readNonEmptyString(scanner, prompt);
            try {
                int value = Integer.parseInt(input);
                if (value < 0) {
                    System.out.println("Value cannot be negative. Please try again.");
                    continue;
                } else if (value == 0) {
                    System.out.println("Value cannot be zero. Please try again.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid whole number.");
            }
        }
    }

    /**
     * Reads a String, validates it parses to a non-negative decimal number, and
     * returns it as a double. Re-prompts on invalid input.
     */
    public static double readValidDouble(Scanner scanner, String prompt) {
        while (true) {
            String input = readNonEmptyString(scanner, prompt);
            try {
                double value = Double.parseDouble(input);
                if (value < 0) {
                    System.out.println("Value cannot be negative. Please try again.");
                    continue;
                } 
                else if (value > 1000000000) {
                    System.out.println("Value cannot be greater than 1 billion. Please try again.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Reads a String, validates it parses to a whole number within [min, max],
     * and returns it as an int. Re-prompts on invalid input.
     */
    public static int readMenuChoice(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            String input = readNonEmptyString(scanner, prompt);
            try {
                int value = Integer.parseInt(input);
                if (value < min || value > max) {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Prints a list of items in a table format.
     *
     * @param items the items to print
     * @param includeCategory whether to include a Category column
     */
    public static void printItemsTable(List<Item> items, boolean includeCategory) {
        if (items.isEmpty()) {
            System.out.println("No items to display.");
            return;
        }

        if (includeCategory) {
            String headerFormat = "%-10s %-20s %-10s %-10s %-15s%n";
            String rowFormat = "%-10s %-20s %-10d %-10.2f %-15s%n";
            System.out.printf(headerFormat, "ID", "Name", "Quantity", "Price", "Category");
            System.out.println("-".repeat(68));
            for (Item item : items) {
                System.out.printf(rowFormat, item.getId(), item.getName(), item.getQuantity(),
                        item.getPrice(), item.getCategory().getDisplayName());
            }
        } else {
            String headerFormat = "%-10s %-20s %-10s %-10s%n";
            String rowFormat = "%-10s %-20s %-10d %-10.2f%n";
            System.out.printf(headerFormat, "ID", "Name", "Quantity", "Price");
            System.out.println("-".repeat(53));
            for (Item item : items) {
                System.out.printf(rowFormat, item.getId(), item.getName(), item.getQuantity(), item.getPrice());
            }
        }
    }
}
