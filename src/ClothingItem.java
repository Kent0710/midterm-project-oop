/**
 * Concrete Item representing a product in the Clothing category.
 */
public class ClothingItem extends Item {

    public ClothingItem(String id, String name, int quantity, double price) {
        super(id, name, quantity, price);
    }

    @Override
    public Category getCategory() {
        return Category.CLOTHING;
    }
}
