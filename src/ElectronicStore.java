//Class representing an electronic store
//Has an array of products that represent the items the store can sell

import java.util.*;

public class ElectronicStore {
    public final int MAX_PRODUCTS = 10; //Maximum number of products the store can have
    private int curProducts;
    private String name;
    private Product[] stock; //Array to hold all products
    private double revenue;
    private int numberOfSales;
    private ArrayList<Product> currentCart;
    private HashSet<Product> noDuplicateCart;
    private ArrayList<Product> displayCart;
    private ArrayList<Product> popularityList;
    private HashSet<Product> allStockProducts;
    private double cartTotal;

    public ElectronicStore(String initName) {
        revenue = 0.0;
        name = initName;
        stock = new Product[MAX_PRODUCTS];
        curProducts = 0;
        currentCart = new ArrayList<>();
        cartTotal = 0;
        noDuplicateCart = new HashSet<>();
        displayCart = new ArrayList<>();
        popularityList = new ArrayList<>();
        allStockProducts = new HashSet<>();
        numberOfSales = 0;
    }

    public String getName() {
        return name;
    }

    //Get revenue
    public double getRevenue() {return revenue;}

    //Get current amount of products
    public int getCurProducts() {return curProducts;}

    //Get current cart list
    public ArrayList<Product> getCurrentCart() {return currentCart;}
    public HashSet<Product> getNoDuplicateCart() {return noDuplicateCart;}

    //Get number of sales made
    public int getNumberOfSales() {
        return numberOfSales;
    }

    //Get cart total
    public double getCartTotal(){
        return cartTotal;
    }

    //Adds a product and returns true if there is space in the array
    //Returns false otherwise
    public boolean addProduct(Product newProduct) {
        if (curProducts < MAX_PRODUCTS) {
            stock[curProducts] = newProduct;
            curProducts++;
            allStockProducts.add(newProduct);
            return true;
        }
        return false;
    }

    //Get product list
    public Product[] getStockList() {
        Product[] list = new Product[curProducts];
        for(int i = 0; i < curProducts; i++){
            list[i] = stock[i];
        }
        return list;
    }

    //List for displaying products
    public String[] getDisplayProducts() {
        String[] displayProducts = new String[curProducts];
        for(int i = 0; i < getStockList().length; i++){
            displayProducts[i] = "$" + getStockList()[i].getPrice() + " " + getStockList()[i].toString();
        }
        return displayProducts;
    }

    //Non-null arrayList for cart
    public ArrayList<Product> getDisplayCart() {
        ArrayList<Product> displayCart = new ArrayList<>();
        for(Product p : noDuplicateCart){
            if(displayCart.size() != noDuplicateCart.size()){
                displayCart.add(p);
            }
        }
        return displayCart;
    }

    //Display cart 2
    public String[] getDisplayCart2() {
        String[] displayCart2 = new String[getDisplayCart().size()];
        int i = 0;
        for(Product p : getDisplayCart()){
            displayCart2[i] = totalInCart(p) + "x " + p.toString();
            i++;
        }
        return displayCart2;
    }

    //Count up the total in the cart
    public int totalInCart(Product product) {
        int amount = 0;
        for(Product p : currentCart){
            if(product == p){
                amount++;
            }
        }
        return amount;
    }

    //Remove from cart
    public boolean removeFromCart(Product p){
        //Remove amount of money to be spent
        cartTotal -= p.getPrice();

        //Remove from cart
        currentCart.remove(p);

        //If amount of the product is 0 in cart
        if(totalInCart(p) == 0){
            //Remove from no duplicate cart
            noDuplicateCart.remove(p);
        }

        //Check if it exists in list
        for(int i = 0; i < stock.length; i++){
            //If it does return false
            if(stock[i] == p){
                return false;
            }
        }

        //Add to list
        addProduct(p);
        return true;
    }

    //Add to cart
    public boolean addToCart(Product newProduct, int index) {
        //Add amount of money to be spent
        cartTotal += newProduct.getPrice();

        //Add to list
        noDuplicateCart.add(newProduct);

        //If there are less in the cart than in inventory
        if(totalInCart(newProduct) < (newProduct.getStockQuantity())) {
            //Add to cart if not already in it
            currentCart.add(newProduct);
            noDuplicateCart.add(newProduct);
        }
        if (totalInCart(newProduct) == (newProduct.getStockQuantity())){
            removeFromStockList(index);
        }
        return true;
    }

    //Remove from stock list
    //Make index of list null
    //Move every product down after null
    //Reduce list size so no null in display
    public void removeFromStockList(int index) {
        stock[index] = null;
        for(int i = index; i < curProducts - 1; i++){
            stock[i] = stock[i+1];
        }
        curProducts--;
    }

    //Purchase method
    public void purchase(Product p){
        //reset cart
        cartTotal = 0;

        //Sell amount
        revenue += p.sellUnits(totalInCart(p));
        //Remove product from cart display
        while(currentCart.contains(p)){
            currentCart.remove(p);
            popularityList.add(p);
        }
        //Remove from no duplicate cart
        noDuplicateCart.remove(p);
    }

    //Increase number of sales when purchase button is pressed
    public void saleMade() {
        numberOfSales++;
    }

    //Get popularityList
    public String[] getPopularityList() {

        Product[] sortPopList = new Product[allStockProducts.size()];

        int t = 0;
        for(Product p : allStockProducts){
            sortPopList[t] = p;
            t++;
        }

        //Iterate through list after each full comparison
        int i = 0;
        Product tempHolder;
        while(i < sortPopList.length - 1) {
            //Reset j
            int j = i + 1;

            //Check through to find the highest
            while (j < sortPopList.length) {
                if (sortPopList[j].getSoldQuantity() > sortPopList[i].getSoldQuantity()) {
                    //Switch when highest is found
                    tempHolder = sortPopList[i];
                    sortPopList[i] = sortPopList[j];
                    sortPopList[j] = tempHolder;
                }
                j++;
            }
            i++;
        }

        //String length of 3
        String[] finalSortedPopList = new String[3];

        //Add to string list
        for(int k = 0; k < 3; k++){
            finalSortedPopList[k] = "Sold: " + sortPopList[k].getSoldQuantity() + " " + sortPopList[k].toString();
        }
        return finalSortedPopList;
    }

    public static ElectronicStore createStore() {
        ElectronicStore store1 = new ElectronicStore("Watts Up Electronics");
        Desktop d1 = new Desktop(100, 10, 3.0, 16, false, 250, "Compact");
        Desktop d2 = new Desktop(200, 10, 4.0, 32, true, 500, "Server");
        Laptop l1 = new Laptop(150, 10, 2.5, 16, true, 250, 15);
        Laptop l2 = new Laptop(250, 10, 3.5, 24, true, 500, 16);
        Fridge f1 = new Fridge(500, 10, 250, "White", "Sub Zero", false);
        Fridge f2 = new Fridge(750, 10, 125, "Stainless Steel", "Sub Zero", true);
        ToasterOven t1 = new ToasterOven(25, 10, 50, "Black", "Danby", false);
        ToasterOven t2 = new ToasterOven(75, 10, 50, "Silver", "Toasty", true);
        store1.addProduct(d1);
        store1.addProduct(d2);
        store1.addProduct(l1);
        store1.addProduct(l2);
        store1.addProduct(f1);
        store1.addProduct(f2);
        store1.addProduct(t1);
        store1.addProduct(t2);
        return store1;
    }
}