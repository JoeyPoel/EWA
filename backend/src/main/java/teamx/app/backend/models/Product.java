package teamx.app.backend.models;

public class Product {
    private int warehouseId;
    private String description;
    private int quantity;
    private String name;
    private int id;

    public Product(int id) {
        this.id = id;
    }

    public Product() {}

    public static Product createProduct(int id, String name, String description, int quantity, int warehouseId){
        Product product = new Product(id);
        product.setName(name);
        product.setDescription(description);
        product.setQuantity(quantity);
        product.setWarehouseId(warehouseId);
        return product;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getWarehouseId() {
        return warehouseId;
    }
    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
