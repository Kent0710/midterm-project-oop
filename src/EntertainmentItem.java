/**
 * Concrete Item representing a product in the Entertainment category.
 */
public class EntertainmentItem extends Item {

    public EntertainmentItem(String id, String name, int quantity, double price) {
        super(id, name, quantity, price);
    }

    @Override
    public Category getCategory() {
        return Category.ENTERTAINMENT;
    }
}
