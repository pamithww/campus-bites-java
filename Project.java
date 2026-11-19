import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//Parent class
class FoodItem {
    // Attributes
    protected String name;
    protected double pricePerUnit;
    protected double proteinPerUnit;
    protected int maxAmount;
    protected int defaultAmount;
    protected String unitName;

    // construct
    // this - used for capturing var name which is declared in this class
    public FoodItem(String name, double pricePerUnit, double proteinPerUnit, int maxAmount, int defaultAmount,
            String unitName) {
        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.proteinPerUnit = proteinPerUnit;
        this.maxAmount = maxAmount;
        this.defaultAmount = defaultAmount;
        this.unitName = unitName;
    }

    public double calculatePrice(int amount) {
        double totalPrice = this.pricePerUnit * amount;
        return totalPrice;
    }

    public double calculateProtein(int amount) {
        double totalProtein = this.proteinPerUnit * amount;
        return totalProtein;
    }

    public boolean isValidPortion(int amount) {
        boolean isValid = false;

        if (amount > 0) {
            if (amount <= this.maxAmount) {
                isValid = true;
            }
        }

        return isValid;
    }

    // to string used bcz the wanted to break the line after couple of variables
    @Override
    public String toString() {
        return this.name + "\n Price: " + this.pricePerUnit + " LKR/" + this.unitName
                + ", Protein: " + this.proteinPerUnit + "g/" + this.unitName
                + "\n Max Amount: " + this.maxAmount + " " + this.unitName
                + "\n Default Amount: " + this.defaultAmount + " " + this.unitName;
    }
}

// Child class 1 extend - connects parent + this child class
class WeightBasedFood extends FoodItem {

    public WeightBasedFood(String name, double pricePerUnit, double proteinPerUnit, int maxAmount, int defaultAmount) {

        super(name, pricePerUnit, proteinPerUnit, maxAmount, defaultAmount, "grams");
    }

    @Override
    public boolean isValidPortion(int amount) {
        boolean isValid = false;
        if (amount >= this.defaultAmount && amount <= this.maxAmount) {
            isValid = true;
        }

        return isValid;
    }

    //
    @Override
    public String toString() {
        return super.toString() + " (portioned by weight)";
    }
}

// Child class 2
class CountBasedFood extends FoodItem {

    public CountBasedFood(String name, double pricePerUnit, double proteinPerUnit, int maxAmount, int defaultAmount) {

        super(name, pricePerUnit, proteinPerUnit, maxAmount, defaultAmount, "eggs");
    }

    @Override
    public String toString() {
        return super.toString() + " (portioned by count)";
    }
}

// Standalone Class
class Menu {

    protected HashMap<Integer, FoodItem> mainDishes;
    protected HashMap<Integer, FoodItem> addOns;

    public Menu() {
        this.mainDishes = new HashMap<>();
        this.addOns = new HashMap<>();
        this.loadMainDishes();
        this.loadAddOns();
    }

    public void addMainDish(int number, FoodItem item) {
        this.mainDishes.put(number, item);
    }

    public void addAddOn(int number, FoodItem item) {
        this.addOns.put(number, item);
    }

    public FoodItem findMainDish(int number) {
        return this.mainDishes.get(number);
    }

    public FoodItem findAddOn(int number) {
        return this.addOns.get(number);
    }

    public void displayMainDishes() {
        System.out.println("--- Main Dishes ---" + "\n");
        for (Map.Entry<Integer, FoodItem> entry : this.mainDishes.entrySet()) {
            int number = entry.getKey();
            FoodItem item = entry.getValue();
            System.out.println(number + ". " + item.toString() + "\n");
        }
    }

    public void displayAddOns() {
        System.out.println("--- Add-ons ---" + "\n");
        for (Map.Entry<Integer, FoodItem> entry : this.addOns.entrySet()) {
            int number = entry.getKey();
            FoodItem item = entry.getValue();
            System.out.println(number + ". " + item.toString() + "\n");
        }
    }

    private void loadMainDishes() {
    
        this.addMainDish(1, new WeightBasedFood("Rice", 0.30, 0.027, 850, 300));
        this.addMainDish(2, new WeightBasedFood("Pasta", 0.45, 0.058, 850, 300));
        this.addMainDish(3, new WeightBasedFood("Noodles", 0.40, 0.045, 850, 300));
        this.addMainDish(4, new WeightBasedFood("Whole Meal Bread", 0.35, 0.090, 850, 300));
    }

    private void loadAddOns() {
        this.addAddOn(1, new WeightBasedFood("Chicken Curry", 1.50, 0.20, 500, 150));
        this.addAddOn(2, new WeightBasedFood("Fish Curry", 1.30, 0.18, 500, 150));
        this.addAddOn(3, new WeightBasedFood("Dhal Curry", 0.60, 0.09, 500, 150));
        this.addAddOn(4, new WeightBasedFood("Grilled Chicken Breast", 1.80, 0.31, 500, 150));
        this.addAddOn(5, new CountBasedFood("Boiled Eggs", 60, 6.3, 30, 2));
        this.addAddOn(6, new CountBasedFood("Scrambled Eggs", 65, 6.0, 30, 2));
    }
}

// main class
public class Project {
    public static void main(String[] args) {

        Menu menu = new Menu();

        int mainDishAmount = 0;
        int addOnAmount =0;
        // running controls the welcome menu loop. It is only ever set to false
        // when the user picks option 2 (Exit). Invalid choices no longer affect it.
        boolean running = true;

        while (running == true) {
            System.out.println();
            System.out.println("--- Welcome to Campus Bites Meal Builder ---" + "\n");
            System.out.println("1. Build a meal");
            System.out.println("2. Exit");
            System.out.println("Enter your choice:");
            int exitChoice = In.nextInt();

            if (exitChoice == 1) {

                System.out.println("Enter your name:");
                String customerName = In.nextLine();

                if (customerName != null) {

                    ArrayList<FoodItem> pickedItems = new ArrayList<>();
                    ArrayList<Integer> pickedAmounts = new ArrayList<>();

                    FoodItem chosenMainDish = null;

                    int mainDishChoice = 0;
                    boolean mainDishPicked = false;
                    
                    System.out.println();
                    menu.displayMainDishes();
                    
                    while (mainDishPicked == false) {
                        System.out.println("Enter a main dish number:");
                        mainDishChoice = In.nextInt();
                     
                        FoodItem foundDish = menu.findMainDish(mainDishChoice);
                        if (foundDish != null) {
                            chosenMainDish = foundDish;
                            mainDishPicked = true;
                        } else {
                            System.out.println("Invalid item number. Please try again.");
                          continue;
                        }
                    }

                    // mainDishPortionSet controls this loop. It stays false until a valid
                    // portion choice (1 or 2, with a valid amount if 2) has been made.
                    // This keeps invalid input local to this step instead of falling back
                    // to the welcome menu.
                    boolean mainDishPortionSet = false;
                    while (mainDishPortionSet == false) {
                        System.out.println("Use default portion (" + chosenMainDish.defaultAmount + " "
                                + chosenMainDish.unitName + ") or customize? Enter 1 for default, 2 to customize");
                        int portionChoice = In.nextInt();

                        if (portionChoice == 1) {
                            mainDishAmount = chosenMainDish.defaultAmount;
                            mainDishPortionSet = true;
                        } else if (portionChoice == 2) {

                            boolean amountConfirmed = false;
                            while (amountConfirmed == false) {
                                System.out.println("Enter amount in " + chosenMainDish.unitName + " (max "
                                        + chosenMainDish.maxAmount + "):");
                                int customAmount = In.nextInt();

                                if (chosenMainDish.isValidPortion(customAmount)) {
                                    mainDishAmount = customAmount;
                                    amountConfirmed = true;
                                    mainDishPortionSet = true;
                                } else {
                                    System.out.println("Invalid amount. Must be at least "
                                            + chosenMainDish.defaultAmount + " and no more than "
                                            + chosenMainDish.maxAmount + " " + chosenMainDish.unitName + ".");
                                    continue;
                                }
                            }
                        } else {
                            System.out.println("Invalid Choice. Please enter 1 for default, 2 to customize");
                            continue;
                        }
                    }

                    pickedItems.add(chosenMainDish);
                    pickedAmounts.add(mainDishAmount);
                    System.out.println(
                            "Added: " + chosenMainDish.name + " (" + mainDishAmount + " " + chosenMainDish.unitName
                                    + ")");

                    int addOnChoice = -1;
                    boolean addOnPortionSet = false;

                    while (addOnChoice != 0) {
                        System.out.println();
                        menu.displayAddOns();
                    boolean validAddOnChoice = false;

                    while (validAddOnChoice == false) {
                       System.out.println("Enter an add-on number from 1 to "
                              + menu.addOns.size() + ", or 0 to finish:");

                        String addOnInput = In.nextLine();

                        try {
                            addOnChoice = Integer.parseInt(addOnInput);

                            if (addOnChoice >= 0 && addOnChoice <= menu.addOns.size()) {
                                validAddOnChoice = true;
                            } else {
                                System.out.println(
                                     "Invalid input. Please enter a whole number between 0 and "
                                              + menu.addOns.size() + ".");
                            }

                        } catch (NumberFormatException e) {
                           System.out.println(
                                   "Invalid input. Please enter a whole number between 0 and "
                                           + menu.addOns.size() + ".");
    }
}    
                    
                        if (addOnChoice == 0) {
                            System.out.println("Finished adding add-ons.");
                        } else if (addOnChoice <= menu.addOns.size() ) {
                            FoodItem foundAddOn = menu.findAddOn(addOnChoice);
                            if (foundAddOn != null) {

                                // addOnPortionSet controls this inner loop. It stays false until a
                                // valid portion choice (1 or 2, with a valid amount if 2) has been
                                // made, so an invalid choice re-asks here instead of reloading
                                // the whole add-on menu.
                                addOnPortionSet = false;
                                while (addOnPortionSet == false) {
                                    System.out.println("Use default portion (" + foundAddOn.defaultAmount + " "
                                            + foundAddOn.unitName + ") or customize? Enter 1 for default, 2 to customize");
                                    int addOnPortionChoice = In.nextInt();

                                    if (addOnPortionChoice == 1) {
                                        addOnAmount = foundAddOn.defaultAmount;
                                        addOnPortionSet = true;
                                    } else if (addOnPortionChoice == 2) {
                                        boolean addOnAmountConfirmed = false;
                                        while (addOnAmountConfirmed == false) {
                                            System.out.println("Enter amount in " + foundAddOn.unitName + " (max "
                                                    + foundAddOn.maxAmount + "):");
                                            int customAddOnAmount = In.nextInt();

                                            if (foundAddOn.isValidPortion(customAddOnAmount)) {
                                                addOnAmount = customAddOnAmount;
                                                addOnAmountConfirmed = true;
                                                addOnPortionSet = true;
                                            } else {
                                                // WeightBasedFood requires at least defaultAmount.
                                                // CountBasedFood only requires more than 0 (inherited rule).
                                                // The message must match whichever type foundAddOn actually is.
                                                if (foundAddOn.unitName.equals("grams")) {
                                                    System.out.println("Invalid amount. Must be at least "
                                                            + foundAddOn.defaultAmount + " and no more than "
                                                            + foundAddOn.maxAmount + " " + foundAddOn.unitName + ".");
                                                } else {
                                                    System.out.println("Invalid amount. Must be greater than 0 and no more than "
                                                            + foundAddOn.maxAmount + " " + foundAddOn.unitName + ".");
                                                }
                                            }
                                        }
                                    } else {
                                        System.out.println("Invalid Choice. Please enter 1 for default, 2 to customize");
                                        continue;
                                    }
                                }

                                pickedItems.add(foundAddOn);
                                pickedAmounts.add(addOnAmount);
                                System.out.println(
                                        "Added: " + foundAddOn.name + " (" + addOnAmount + " " + foundAddOn.unitName
                                                + ")");
                                addOnChoice = -1;
                            } else {
                                System.out.println("Invalid item number. Please try again.");
                            }
                        } else {
                            System.out.println("Invalid item number. Please try again.");
                        }
                    }

                    double totalPrice = 0;
                    double totalProtein = 0;

                    System.out.println();
                    System.out.println("--- Meal Summary for " + customerName + " ---");
                    for (int i = 0; i < pickedItems.size(); i++) {
                        FoodItem currentItem = pickedItems.get(i);
                        int currentAmount = pickedAmounts.get(i);

                        double itemPrice = currentItem.calculatePrice(currentAmount);
                        double itemProtein = currentItem.calculateProtein(currentAmount);

                        System.out.println(currentItem.name + ": " + currentAmount + " " + currentItem.unitName
                                + ", Price: " + itemPrice + " LKR, Protein: " + itemProtein + "g");

                        totalPrice = totalPrice + itemPrice;
                        totalProtein = totalProtein + itemProtein;
                    }

                    System.out.println("Total Price: " + totalPrice + " LKR");
                    System.out.println("Total Protein: " + totalProtein + "g");

                    System.out.println("Confirm this order? Enter Y to confirm, anything else to cancel");
                    char confirmChar = In.nextChar();

                    if (confirmChar == 'Y' || confirmChar == 'y') {
                        System.out.println();
                        System.out.println("--- Receipt ---");
                        System.out.println("Customer: " + customerName);
                        for (int i = 0; i < pickedItems.size(); i++) {
                            FoodItem currentItem = pickedItems.get(i);
                            int currentAmount = pickedAmounts.get(i);
                            System.out.println(currentItem.name + " x " + currentAmount + " " + currentItem.unitName);
                        }
                        System.out.println("Total Price: " + totalPrice + " LKR");
                        System.out.println("Total Protein: " + totalProtein + "g");
                        System.out.println("Order confirmed. Thank you!");
                    } else {
                        System.out.println("Order cancelled.");
                    }
                } else {
                    System.out.println("Invalid Name");
                    continue;
                }
            } else if (exitChoice == 2) {
                System.out.println("Thank you for visiting Campus Bites. Goodbye!");
                running = false;

            } else {

                System.out.println("Invalid choice. Please enter 1 or 2.");
            }

        }

    }
}
