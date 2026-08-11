# Project Overview
Create an inventory management system in **Java** that uses encapsulation and abstraction concepts. This project should allow users to manage items in a store's inventory. These includes: Add Item, Update Item (either Quantity or Price), Remove Item, Display Items by Category, Display All Items, Search Item, Sort Items (either by Quantity or Price, and Ascending or Descending), and Display Low Stock Items. Each item should have details like ID, name, quantity, and price. There should be three categories for the items: Clothing, Electronics, and Entertainment. 

## Important Notes
- Include ALL Types of Validations!
- Use encapsulation and abstraction concepts!
- The application lifecycle should be in a loop after an operation. It shouldn't end but rather after a certian operation, the program would return to the main menu until the user exits.
- I already created the GitHub repository so just give me the entire zip file to test and run on my local device. I will handle the GitHub myself.

## Code Style & Conventions
- For repetitive simple operations, create a Utils.java
- For inputs, get them all as a String then validate. Just parse later on if need for specific type use

## Requirements
### 1. Add Item
- Input Category
- If category is found, input ID (string), Name (string), Quantity (int), Price (float / double)
- After input, it should display 'Item added successfully!'
- If category is not found, display 'Category <input-value> does not exist!'
- Return to Main Menu after adding an item successfully
### 2. Update Item
- Input ID first
- If ID is found, user will input if quantity or price, then input the new value. Then display 'Quantity / Price of Item <name> is updated from <old-value> to <new-value>'
- If ID is not found, display 'Item not found!'
- Return to Main Menu after updating an item successfully
### 3. Remove Item
- Input ID
- If ID is found, display 'Item <name> has been removed from the inventory'
- If ID is not found, display 'Item not found!'
### 4. Display Items by Category
- Input Category
- If Category is existing, then display the items in a table format (ID, Name, Quantity, Price are the columns)
- If Category is not found, display 'Category <input-value> does not exist!'
### 5. Display All Items
- Displays the items in a table format (ID, Name, Quantity, Price, Category are the columns)
### 6. Search Item
- Input ID
- If ID is found, display the item details
- If ID is not found, should display 'Item not found!'
### 7. Sort Items
- Input if sort by quantity or price, then input if ascending or descending
- Display the items in a table format (ID, Name, Quantity, Price, Category are the columns)
### 8. Display Low Stock Items
- Display all the items that has a quantity of 5 and below
- It should be displayed in a table format (ID, Name, Quantity, Price, Category are the columns)
### 9. Exit

## Sample
Sample Menu should look like this
Menu
1 - Add Item
2 - Update Item
3 - Remove Item
4 - Display Items by Category
5 - Display All Items
6 - Search Item
7 - Sort items
8 - Display Low Stock Items
9 - Exit

## Rubric
- **Quality of Code**: 30 points
- **Output**: 40 points
- **Q&A**: 20 points
- **GitHub**: 10 points
Total of 100 points