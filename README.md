# Inventory Management System

A console-based Java inventory management system for a store, built around
**encapsulation** and **abstraction**.

## How to Compile & Run

```bash
cd src
javac *.java
java Main
```

## Project Structure

```
InventoryManagementSystem/
├── README.md
└── src/
    ├── Main.java              # Entry point, menu loop, all 9 operations
    ├── InventoryManager.java  # Business logic: add/update/remove/search/sort/filter
    ├── Item.java               # Abstract base class (encapsulation + abstraction)
    ├── ClothingItem.java       # Concrete Item subclass
    ├── ElectronicsItem.java    # Concrete Item subclass
    ├── EntertainmentItem.java  # Concrete Item subclass
    ├── Category.java           # Enum: CLOTHING, ELECTRONICS, ENTERTAINMENT
    └── Utils.java               # Shared input validation & table-printing helpers
```

## Design Notes

- **Abstraction**: `Item` is an abstract class. Each category (`ClothingItem`,
  `ElectronicsItem`, `EntertainmentItem`) extends it and defines its own
  `getCategory()`. `InventoryManager.createItem()` acts as a factory so the
  rest of the app never needs to know which concrete class is used.
- **Encapsulation**: All fields in `Item` are `private`. `id` and `name` are
  immutable (no setters — they're not editable per the spec). `quantity` and
  `price` can only be changed through validated setters that reject negative
  values.
- **Input handling**: Per the "read as String, validate, then parse" style,
  every prompt reads a raw line first. `Utils` centralizes all
  validation/re-prompt loops (non-empty strings, valid non-negative
  integers/doubles, menu ranges, and fixed text choices like
  "Quantity"/"Price").
- **Validation coverage**:
  - Unknown category → `Category <input> does not exist!`
  - Unknown item ID → `Item not found!`
  - Duplicate ID on Add → re-prompts for a different ID
  - Non-numeric or negative Quantity/Price → re-prompts until valid
  - Menu choices outside 1–9 (or invalid Quantity/Price/Ascending/Descending
    choices) → re-prompts until valid
- **Program lifecycle**: The main menu runs in a loop and always returns to
  the menu after each operation, only stopping on option 9 (Exit). It also
  catches an unexpected end of input (EOF) so it exits gracefully instead of
  crashing if input runs out (e.g. during automated testing).

## Sample Menu

```
Menu
1 - Add Item
2 - Update Item
3 - Remove Item
4 - Display Items by Category
5 - Display All Items
6 - Search Item
7 - Sort Items
8 - Display Low Stock Items
9 - Exit
```
