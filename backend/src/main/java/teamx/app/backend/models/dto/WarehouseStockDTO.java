package teamx.app.backend.models.dto;

public class WarehouseStockDTO {
    private Long warehouseId;
    private String warehouseName;
    private Long stockLevel;

    public WarehouseStockDTO(Long warehouseId, String warehouseName, Long stockLevel) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.stockLevel = stockLevel;
    }

    // getters and setters
}