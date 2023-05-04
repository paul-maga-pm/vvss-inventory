
package inventory.model;

import inventory.model.exception.InvalidProductException;
import javafx.collections.ObservableList;

import java.util.Objects;


public class Product {
    
    // Declare fields
    private ObservableList<AbstractPart> associatedParts;// = FXCollections.observableArrayList();
    private int productId;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;
    private static final double MINIMUM_PRICE = 0.01;

    // Constructor
    public Product(int productId, String name, double price, int inStock, int min, int max, ObservableList<AbstractPart> partList) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
        this.associatedParts= partList;
    }
    
    // Getters
    public ObservableList<AbstractPart> getAssociatedParts() {
        return associatedParts;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getInStock() {
        return inStock;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
    
    // Setters
    public void setAssociatedParts(ObservableList<AbstractPart> associatedParts) {
        this.associatedParts = associatedParts;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }
    
    // Other methods
    public void addAssociatedPart(AbstractPart part) {
        associatedParts.add(part);
    }
    
    public void removeAssociatedPart(AbstractPart part) {
        associatedParts.remove(part);
    }
    
    public AbstractPart lookupAssociatedPart(String searchItem) {
        for(AbstractPart p:associatedParts) {
            if(p.getName().contains(searchItem) || Integer.toString(p.getPartId()).equals(searchItem)) return p;
        }
        return null;
    }
    
    /**
     * Generate an error message for invalid values in a product
     * and evaluate whether the sum of the price of associated parts
     * is less than the price of the resulting product.
     * A valid product will return an empty error message string.
     * @param name
     * @param min
     * @param max
     * @param inStock
     * @param price
     * @param parts
     * @return 
     */
    public static void isValidProduct(String name, double price, int inStock, int min, int max, ObservableList<AbstractPart> parts) throws InvalidProductException {
        String errorMessage = "";
        double sumOfParts = 0.00;
        for (int i = 0; i < parts.size(); i++) {
            sumOfParts += parts.get(i).getPrice();
        }
        if (name.equals("")) {
            errorMessage += "A name has not been entered. ";
        }
        if (min < 0) {
            errorMessage += "The inventory level must be greater than 0. ";
        }
        if (price < MINIMUM_PRICE) {
            errorMessage += "The price must be greater than $0. ";
        }
        if (min > max) {
            errorMessage += "The Min value must be less than the Max value. ";
        }
        if(inStock < min) {
            errorMessage += "Inventory level is lower than minimum value. ";
        }
        if(inStock > max) {
            errorMessage += "Inventory level is higher than the maximum value. ";
        }
        if (parts.size() < 1) {
            errorMessage += "Product must contain at least 1 part. ";
        }
        if (sumOfParts > price) {
            errorMessage += "Product price must be greater than cost of parts. ";
        }
        if (!errorMessage.isEmpty()) {
            throw new InvalidProductException(errorMessage);
        }
    }

    @Override
    public String toString() {
        return "P,"+this.productId+","+this.name+","+this.price+","+this.inStock+","+
                this.min+","+this.max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return productId == product.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
