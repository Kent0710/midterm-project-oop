import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Manages the collection of inventory Items and all business-logic
 * operations on them: adding, updating, removing, searching, filtering,
 * and sorting. Keeps the item list private and only exposes it through
 * safe copies or query methods (encapsulation).
 */
public class InventoryManager {
    private final List<Item> items;

    public InventoryManager() {
        items = new ArrayList<>();
    }

    /**
     * Factory method that builds the correct Item subclass for a given
     * category. Centralizes object creation so Main does not need to know
     * about concrete subclasses.
     */
    public Item createItem(Category category, String id, String name, int quantity, double price) {
        switch (category) {
            case CLOTHING:
                return new ClothingItem(id, name, quantity, price);
            case ELECTRONICS:
                return new ElectronicsItem(id, name, quantity, price);
            case ENTERTAINMENT:
                return new EntertainmentItem(id, name, quantity, price);
            default:
                throw new IllegalArgumentException("Unknown category: " + category);
        }
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public boolean idExists(String id) {
        return findById(id) != null;
    }

    public Item findById(String id) {
        for (Item item : items) {
            if (item.getId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }

    public boolean removeItem(String id) {
        Item item = findById(id);
        if (item != null) {
            items.remove(item);
            return true;
        }
        return false;
    }

    public List<Item> getAllItems() {
        return new ArrayList<>(items);
    }

    public List<Item> getItemsByCategory(Category category) {
        List<Item> result = new ArrayList<>();
        for (Item item : items) {
            if (item.getCategory() == category) {
                result.add(item);
            }
        }
        return result;
    }

    public List<Item> getLowStockItems() {
        List<Item> result = new ArrayList<>();
        for (Item item : items) {
            if (item.getQuantity() <= 5) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Returns a new sorted list of items, without mutating the original order.
     *
     * @param sortBy    "quantity" or "price" (case-insensitive)
     * @param ascending true for ascending order, false for descending
     */
    public List<Item> getSortedItems(String sortBy, boolean ascending) {
        List<Item> sorted = new ArrayList<>(items);
        Comparator<Item> comparator = "quantity".equalsIgnoreCase(sortBy)
                ? Comparator.comparingInt(Item::getQuantity)
                : Comparator.comparingDouble(Item::getPrice);

        if (!ascending) {
            comparator = comparator.reversed();
        }
        sorted.sort(comparator);
        return sorted;
    }
}
