
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
        String input = "";
        boolean isValid = false;
        while (!isValid) {
            System.out.print(prompt);
            input = scanner.nextLine().trim().replaceAll("\\s+", " ");
            if (!input.isEmpty()) {
                isValid = true;
            } else {
                System.out.println("Input cannot be empty. Please try again.");
            }
        }
        return input;
    }

    /**
     * Reads a line of input and keeps re-prompting until it is non-empty and within the maximum length.
     */
    public static String readNonEmptyString(Scanner scanner, String prompt, int maxLength, String maxLengthErrorMessage) {
        String input = "";
        boolean isValid = false;
        while (!isValid) {
            System.out.print(prompt);
            input = scanner.nextLine().trim().replaceAll("\\s+", " ");
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
                continue;
            }

            if (input.length() > maxLength) {
                System.out.println(maxLengthErrorMessage);
                continue;
            }

            isValid = true;
        }
        return input;
    }

    /**
     * Reads a String and keeps re-prompting until it matches (case-insensitive)
     * one of the given valid options. Returns the value exactly as it appears
     * in validOptions (normalized casing), not what the user typed.
     */
    public static String readChoiceFromOptions(Scanner scanner, String prompt, String... validOptions) {
        String selectedOption = null;
        boolean isValid = false;
        while (!isValid) {
            String input = readNonEmptyString(scanner, prompt);
            for (String option : validOptions) {
                if (option.equalsIgnoreCase(input)) {
                    selectedOption = option;
                    isValid = true;
                    break;
                }
            }
            if (!isValid) {
                System.out.println("Invalid input. Please enter one of the following: " + String.join(" / ", validOptions));
            }
        }
        return selectedOption;
    }

    /**
     * Reads a String, validates it parses to a non-negative whole number, and
     * returns it as an int. Re-prompts on invalid input.
     */
    public static int readValidInt(Scanner scanner, String prompt) {
        int value = 0;
        boolean isValid = false;
        while (!isValid) {
            String input = readNonEmptyString(scanner, prompt);
            try {
                value = Integer.parseInt(input);
                if (value < 0) {
                    System.out.println("Value cannot be negative. Please try again.");
                    continue;
                } else if (value > 50000) {
                    System.out.println("Value cannot be greater than 50,000. Please try again.");
                    continue;
                } else if (value == 0) {
                    System.out.println("Value cannot be zero. Please try again.");
                    continue;
                }
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid whole number.");
            }
        }
        return value;
    }

    /**
     * Reads a String, validates it parses to a non-negative decimal number, and
     * returns it as a double. Re-prompts on invalid input.
     */
    public static double readValidDouble(Scanner scanner, String prompt) {
        double value = 0.0;
        boolean isValid = false;
        while (!isValid) {
            String input = readNonEmptyString(scanner, prompt);
            try {
                value = Double.parseDouble(input);
                if (value < 0) {
                    System.out.println("Value cannot be negative. Please try again.");
                    continue;
                } else if (value > 1000000) {
                    System.out.println("Value cannot be greater than 1 million. Please try again.");
                    continue;
                }
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return value;
    }

    /**
     * Reads a String, validates it parses to a whole number within [min, max],
     * and returns it as an int. Re-prompts on invalid input.
     */
    public static int readMenuChoice(Scanner scanner, String prompt, int min, int max) {
        int value = 0;
        boolean isValid = false;
        while (!isValid) {
            String input = readNonEmptyString(scanner, prompt);
            try {
                value = Integer.parseInt(input);
                if (value < min || value > max) {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                    continue;
                }
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return value;
    }

    /**
     * Prints a list of items in a table format with dynamic column widths
     * adapting to the longest value in each column.
     *
     * @param items the items to print
     * @param includeCategory whether to include a Category column
     */
    public static void printItemsTable(List<Item> items, boolean includeCategory) {
        if (items.isEmpty()) {
            System.out.println("No items to display.");
            return;
        }

        int idWidth = "ID".length();
        int nameWidth = "Name".length();
        int quantityWidth = "Quantity".length();
        int priceWidth = "Price".length();
        int categoryWidth = includeCategory ? "Category".length() : 0;

        for (Item item : items) {
            idWidth = Math.max(idWidth, item.getId().length());
            nameWidth = Math.max(nameWidth, item.getName().length());
            quantityWidth = Math.max(quantityWidth, String.valueOf(item.getQuantity()).length());
            priceWidth = Math.max(priceWidth, String.format("%.2f", item.getPrice()).length());
            if (includeCategory) {
                categoryWidth = Math.max(categoryWidth, item.getCategory().getDisplayName().length());
            }
        }

        if (includeCategory) {
            String headerFormat = String.format("%%-%ds    %%-%ds    %%-%ds    %%-%ds    %%-%ds%%n",
                    idWidth, nameWidth, quantityWidth, priceWidth, categoryWidth);
            String rowFormat = String.format("%%-%ds    %%-%ds    %%-%dd    %%-%d.2f    %%-%ds%%n",
                    idWidth, nameWidth, quantityWidth, priceWidth, categoryWidth);
            int totalWidth = idWidth + nameWidth + quantityWidth + priceWidth + categoryWidth + (4 * 4);
            System.out.printf(headerFormat, "ID", "Name", "Quantity", "Price", "Category");
            System.out.println("-".repeat(totalWidth));
            for (Item item : items) {
                System.out.printf(rowFormat, item.getId(), item.getName(), item.getQuantity(),
                        item.getPrice(), item.getCategory().getDisplayName());
            }
        } else {
            String headerFormat = String.format("%%-%ds    %%-%ds    %%-%ds    %%-%ds%%n",
                    idWidth, nameWidth, quantityWidth, priceWidth);
            String rowFormat = String.format("%%-%ds    %%-%ds    %%-%dd    %%-%d.2f%%n",
                    idWidth, nameWidth, quantityWidth, priceWidth);
            int totalWidth = idWidth + nameWidth + quantityWidth + priceWidth + (3 * 4);
            System.out.printf(headerFormat, "ID", "Name", "Quantity", "Price");
            System.out.println("-".repeat(totalWidth));
            for (Item item : items) {
                System.out.printf(rowFormat, item.getId(), item.getName(), item.getQuantity(), item.getPrice());
            }
        }
    }

    /**
     * Prints the list of supported categories in numbered format.
     */
    public static void printCategories() {
        System.out.println("Categories:");
        System.out.println("1 - Clothing");
        System.out.println("2 - Electronics");
        System.out.println("3 - Entertainment");
    }
}
