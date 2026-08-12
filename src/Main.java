
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Entry point for the Inventory Management System. Drives the main menu loop
 * and delegates all business logic to InventoryManager, and all
 * input/validation/printing to Utils.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InventoryManager manager = new InventoryManager();

        try {
            boolean running = true;
            while (running) {
                printMenu();
                int choice = Utils.readMenuChoice(scanner, "Enter choice: ", 1, 9);
                System.out.println();

                switch (choice) {
                    case 1:
                        addItem(scanner, manager);
                        break;
                    case 2:
                        updateItem(scanner, manager);
                        break;
                    case 3:
                        removeItem(scanner, manager);
                        break;
                    case 4:
                        displayItemsByCategory(scanner, manager);
                        break;
                    case 5:
                        displayAllItems(manager);
                        break;
                    case 6:
                        searchItem(scanner, manager);
                        break;
                    case 7:
                        sortItems(scanner, manager);
                        break;
                    case 8:
                        displayLowStockItems(manager);
                        break;
                    case 9:
                        System.out.println("Thank you for using the Inventory Management System. Goodbye!");
                        running = false;
                        break;
                    default:
                        // Unreachable: readMenuChoice already restricts to 1-9.
                        break;
                }
                System.out.println();
            }
        } catch (NoSuchElementException e) {
            // Input stream ended unexpectedly (e.g. no more input piped in).
            System.out.println("\nNo more input detected. Exiting the program.");
        } finally {
            scanner.close();
        }
    }

    private static void printMenu() {
        System.out.println("Menu");
        System.out.println("1 - Add Item");
        System.out.println("2 - Update Item");
        System.out.println("3 - Remove Item");
        System.out.println("4 - Display Items by Category");
        System.out.println("5 - Display All Items");
        System.out.println("6 - Search Item");
        System.out.println("7 - Sort Items");
        System.out.println("8 - Display Low Stock Items");
        System.out.println("9 - Exit");
    }

    // 1. Add Item
    private static void addItem(Scanner scanner, InventoryManager manager) {
        Category category = Utils.promptCategoryOrBack(scanner);
        if (category == null) {
            return;
        }

        String id;
        while (true) {
            id = Utils.readNonEmptyString(scanner, "\nEnter Item ID: ");
            if (manager.idExists(id)) {
                System.out.println("Item ID " + id + " already exists! Please enter a different ID.");
                continue;
            }
            break;
        }

        String name = Utils.readNonEmptyString(scanner, "Enter Item Name: ");
        int quantity = Utils.readValidInt(scanner, "Enter Quantity: ");
        double price = Utils.readValidDouble(scanner, "Enter Price: ");

        Item newItem = manager.createItem(category, id, name, quantity, price);
        manager.addItem(newItem);
        System.out.println("Item added successfully!");
    }

    // 2. Update Item
    private static void updateItem(Scanner scanner, InventoryManager manager) {
        System.out.println("Items in Inventory:\n");

        // Print all items in a table format for the user to see
        List<Item> allItems = manager.getAllItems();
        if (allItems.isEmpty()) {
            System.out.println("No items available to update.");
            return;
        }
        Utils.printItemsTable(allItems, true);
        String id = Utils.readNonEmptyString(scanner, "\nEnter Item ID: ");
        Item item = manager.findById(id);
        if (item == null) {
            System.out.println("Item not found!");
            return;
        }

        System.out.println("1 - Quantity");
        System.out.println("2 - Price");
        System.out.println("3 - Back to Main Menu");
        int choice = Utils.readMenuChoice(scanner, "What do you want to update? ", 1, 3);

        if (choice == 3) {
            return;
        }

        if (choice == 1) {
            int oldQuantity = item.getQuantity();
            int newQuantity = Utils.readValidInt(scanner, "Enter new Quantity: ");
            item.setQuantity(newQuantity);
            System.out.println("Quantity of Item " + item.getName() + " is updated from "
                    + oldQuantity + " to " + newQuantity);
        } else {
            double oldPrice = item.getPrice();
            double newPrice = Utils.readValidDouble(scanner, "Enter new Price: ");
            item.setPrice(newPrice);
            System.out.printf("Price of Item %s is updated from %.2f to %.2f%n",
                    item.getName(), oldPrice, newPrice);
        }
    }

    // 3. Remove Item
    private static void removeItem(Scanner scanner, InventoryManager manager) {
        System.out.println("Items in Inventory:\n");
        // Print all items in a table format for the user to see
        List<Item> allItems = manager.getAllItems();
        if (allItems.isEmpty()) {
            System.out.println("No items available to remove.");
            return;
        }

        Utils.printItemsTable(allItems, true);

        // TODO: Add confirmation prompt before removing the item
        String id = Utils.readNonEmptyString(scanner, "\nEnter Item ID: ");
        Item item = manager.findById(id);
        if (item == null) {
            System.out.println("Item not found!");
            return;
        }
        manager.removeItem(id);
        System.out.println("Item " + item.getName() + " has been removed from the inventory");
    }

    // 4. Display Items by Category
    private static void displayItemsByCategory(Scanner scanner, InventoryManager manager) {
        // Check first if there are any items in the inventory
        if (manager.getAllItems().isEmpty()) {
            System.out.println("No items available in the inventory.");
            return;
        }

        Category category = Utils.promptCategoryOrBack(scanner);
        if (category == null) {
            return;
        }
        System.out.println("\nItems in Category: " + category.getDisplayName() + "\n");
        List<Item> items = manager.getItemsByCategory(category);
        Utils.printItemsTable(items, false);
    }

    // 5. Display All Items
    private static void displayAllItems(InventoryManager manager) {
        List<Item> items = manager.getAllItems();
        Utils.printItemsTable(items, true);
    }

    // 6. Search Item
    private static void searchItem(Scanner scanner, InventoryManager manager) {
        String id = Utils.readNonEmptyString(scanner, "Enter Item ID: ");
        Item item = manager.findById(id);
        if (item == null) {
            System.out.println("Item not found!");
            return;
        }
        System.out.println("ID: " + item.getId());
        System.out.println("Name: " + item.getName());
        System.out.println("Quantity: " + item.getQuantity());
        System.out.printf("Price: %.2f%n", item.getPrice());
        System.out.println("Category: " + item.getCategory().getDisplayName());
    }

    // 7. Sort Items
    private static void sortItems(Scanner scanner, InventoryManager manager) {
        String sortBy = Utils.readChoiceFromOptions(scanner, "Sort by Quantity or Price? ", "Quantity", "Price");
        String order = Utils.readChoiceFromOptions(scanner, "Ascending or Descending? ", "Ascending", "Descending");
        boolean ascending = order.equalsIgnoreCase("Ascending");

        List<Item> items = manager.getSortedItems(sortBy, ascending);
        Utils.printItemsTable(items, true);
    }

    // 8. Display Low Stock Items
    private static void displayLowStockItems(InventoryManager manager) {
        List<Item> items = manager.getLowStockItems();
        Utils.printItemsTable(items, true);
    }
}
