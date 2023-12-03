package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Product;
import teamx.app.backend.models.dto.charts.ChartsDataDTO;
import teamx.app.backend.models.dto.charts.DataSetDTO;

import java.util.List;

@Service
public class ChartService {
    InventoryService inventoryService;
    ProjectService projectService;
    TransactionService transactionService;
    OrderService orderService;
    ProductService productService;
    TeamService teamService;
    UserService userService;
    WarehouseService warehouseService;

    @Autowired
    public ChartService(InventoryService inventoryService, ProjectService projectService,
                        TransactionService transactionService, OrderService orderService,
                        ProductService productService, TeamService teamService, UserService userService,
                        WarehouseService warehouseService) {
        this.inventoryService = inventoryService;
        this.projectService = projectService;
        this.transactionService = transactionService;
        this.orderService = orderService;
        this.productService = productService;
        this.teamService = teamService;
        this.userService = userService;
        this.warehouseService = warehouseService;
    }

    public ChartsDataDTO inventoryBar(Long warehouseId) {
        List<Long> productIds = productService.findAllActiveIds();
        return currentStockBar(productIds, warehouseId);
    }

    private ChartsDataDTO currentStockBar(List<Long> productIds, Long warehouseId) {
        List<String> labels = getProductNames(productIds);
        List<Integer> data = getProductCurrentStocks(warehouseId, productIds);

        DataSetDTO dataSetDTO = DataSetDTO.builder()
                .label(getWarehouseLabel(warehouseId))
                .data(data)
                .build();


        return ChartsDataDTO.builder()
                .labels(labels)
                .datasets(List.of(dataSetDTO))
                .build();
    }

    private String getWarehouseLabel(Long warehouseId) {
        return warehouseId == null ? "All warehouses" : warehouseService.findById(warehouseId).getName();
    }

    private List<Integer> getProductCurrentStocks(Long warehouseId, List<Long> productIds) {
        return productIds.stream()
                .map(productId -> transactionService.findProductCurrentStock(warehouseId, productId))
                .toList();
    }

    private List<String> getProductNames(List<Long> productIds) {
        return productService.findAllByIds(productIds)
                .stream()
                .map(Product::getName)
                .toList();
    }
}
