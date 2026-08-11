/**
 * Abstract base class for all inventory items.
 *
 * Demonstrates ABSTRACTION: subclasses (ClothingItem, ElectronicsItem,
 * EntertainmentItem) must define what category they belong to via the
 * abstract getCategory() method, while all shared behavior/state lives here.
 *
 * Demonstrates ENCAPSULATION: all fields are private. Access is only
 * possible through public getters/setters, and setters validate their
 * input before mutating state.
 */
public abstract class Item {
    private final String id;
    private final String name;
    private int quantity;
    private double price;

    public Item(String id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        setQuantity(quantity);
        setPrice(price);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public final void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public final void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }

    /**
     * Every concrete Item subclass must declare which Category it belongs to.
     */
    public abstract Category getCategory();
}
