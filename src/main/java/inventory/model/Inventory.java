
package inventory.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    
    // Declare fields
    private ObservableList<Product> products;
    private ObservableList<AbstractPart> allParts;
    private int autoPartId;
    private int autoProductId;

    public Inventory(){
        this.products = FXCollections.observableArrayList();
        this.allParts= FXCollections.observableArrayList();
        this.autoProductId=0;
        this.autoPartId=0;
    }

    public Inventory(ObservableList<Product> products) {
        this.products = products;
        this.autoProductId = products.stream()
                .map(Product::getProductId)
                .max(Integer::compareTo).get() + 1;
    }

    // Declare methods
    /**
     * Add new product to observable list products
     * @param product 
     */
    public void addProduct(Product product) {
        products.add(product);
    }
    
    /**
     * Remove product from observable list products
     * @param product 
     */
    public void removeProduct(Product product) {
        products.remove(product);
    }
    
    /**
     * Accepts search parameter and if an ID or name matches input, that product is returned
     * @param searchItem
     * @return 
     */
    public Product lookupProduct(String searchItem) {
        boolean isFound = false;
        for(int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if(p.getName().contains(searchItem))
                return p;
            if((p.getProductId()+"").equals(searchItem))
                return p;
            isFound = true;
        }
        if(isFound == false) {
            Product product = new Product(0, null, 0.0, 0, 0, 0, null);
            return product;
        }
        return null;
    }
    
    /**
     * Update product at given index
     * @param index
     * @param product 
     */
    public void updateProduct(int index, Product product) {
        products.set(index, product);
    }
    
    /**
     * Getter for Product Observable List
     * @return 
     */
    public ObservableList<Product> getProducts() {
        return products;
    }

    public void setProducts(ObservableList<Product> list) {
        products=list;
    }
    
    /**
     * Add new part to observable list allParts
     * @param part 
     */
    public void addPart(AbstractPart part) {
        allParts.add(part);
    }
    
    /**
     * Removes part passed as parameter from allParts
     * @param part 
     */
    public void deletePart(AbstractPart part) {
        allParts.remove(part);
    }
    
    /**
     * Accepts search parameter and if an ID or name matches input, that part is returned
     * @param searchItem
     * @return 
     */
    public AbstractPart lookupPart(String searchItem) {
        for(AbstractPart p:allParts) {
            if(p.getName().contains(searchItem) || (p.getPartId()+"").equals(searchItem)) return p;
        }
        return null;
    }
    
    /**
     * Update part at given index
     * @param index
     * @param part 
     */
    public void updatePart(int index, AbstractPart part) {
        allParts.set(index, part);
    }
    
    /**
     * Getter for allParts Observable List
     * @return 
     */
    public ObservableList<AbstractPart> getAllParts() {
        return allParts;
    }

    /**
     *
     * @param list
     */
    public void setAllParts(ObservableList<AbstractPart> list) {
        allParts=list;
    }
    
    /**
     * Method for incrementing part ID to be used to automatically
     * assign ID numbers to parts
     * @return 
     */
    public int getAutoPartId() {
        autoPartId++;
        return autoPartId;
    }
    
    /**
     * Method for incrementing product ID to be used to automatically
     * assign ID numbers to products
     * @return 
     */
    public int getAutoProductId() {
        autoProductId++;
        return autoProductId;
    }


    public void setAutoPartId(int id){
        autoPartId=id;
    }

    public void setAutoProductId(int id){
        autoProductId=id;
    }
    
}
