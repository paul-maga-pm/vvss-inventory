
package inventory.model;


import inventory.model.exception.InvalidPartException;

public abstract class AbstractPart {

    // Declare fields
    private int partId;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;
    
    // Constructor
    protected AbstractPart(int partId, String name, double price, int inStock, int min, int max) {
        this.partId = partId;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
    }
    
    // Getters
    public int getPartId() {
        return partId;
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
    public void setPartId(int partId) {
        this.partId = partId;
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
    
    /**
     * Generate an error message for invalid values in a part
     * Valid part will return an empty string
     * @param name
     * @param price
     * @param inStock
     * @param min
     * @param max
     * @return 
     */
    public static void isValidPart(String name, double price, int inStock, int min, int max) throws InvalidPartException {
        String errorMessage = "";
        if("".equals(name)) {
            errorMessage += "A name has not been entered. ";
        }
        if(price < 0.01) {
            errorMessage += "The price must be greater than 0. ";
        }
        if(inStock < 1) {
            errorMessage += "Inventory level must be greater than 0. ";
        }
        if(min > max) {
            errorMessage += "The Min value must be less than the Max value. ";
        }
        if(inStock < min) {
            errorMessage += "Inventory level is lower than minimum value. ";
        }
        if(inStock > max) {
            errorMessage += "Inventory level is higher than the maximum value. ";
        }
        if (!errorMessage.isEmpty()) {
            throw new InvalidPartException(errorMessage);
        }
    }
    @Override
    public String toString() {
        return this.partId+","+this.name+","+this.price+","+this.inStock+","+
                this.min+","+this.max;
    }
}