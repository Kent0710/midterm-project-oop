/**
 * Represents the fixed set of categories an inventory Item can belong to.
 * Using an enum keeps category values type-safe and centralizes the
 * valid list of categories in one place.
 */
public enum Category {
    CLOTHING("Clothing"),
    ELECTRONICS("Electronics"),
    ENTERTAINMENT("Entertainment");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Matches a raw user-typed String (case-insensitive) to a Category.
     *
     * @param input the raw text typed by the user
     * @return the matching Category, or null if no category matches
     */
    public static Category fromString(String input) {
        if (input == null) {
            return null;
        }
        String trimmed = input.trim();
        for (Category category : values()) {
            if (category.displayName.equalsIgnoreCase(trimmed)) {
                return category;
            }
        }
        return null;
    }
}
