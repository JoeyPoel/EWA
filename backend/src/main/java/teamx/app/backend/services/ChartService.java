package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Product;
import teamx.app.backend.models.dto.charts.ChartsDataDTO;
import teamx.app.backend.models.dto.charts.DataSetDTO;

import java.sql.Date;
import java.util.ArrayList;
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

    public ChartsDataDTO inventoryBarForAllWarehouses() {
        List<Long> productIds = productService.findAllActiveIds();

        List<Long> warehouseIds = warehouseService.findAllIds();

        List<DataSetDTO> dataSets = warehouseIds.stream()
                .map(warehouseId -> DataSetDTO.builder()
                        .label(getWarehouseLabel(warehouseId))
                        .data(getProductCurrentStocks(warehouseId, productIds))
                        .build())
                .toList();

        return ChartsDataDTO.builder()
                .labels(getProductNames(productIds))
                .datasets(dataSets)
                .build();
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

    public ChartsDataDTO getStockHistoryByWarehouse(Long warehouseId, Date startDate, Date endDate) {
        List<Long> productIds = productService.findAllActiveIds();
        return productStockHistoryLine(warehouseId, productIds, startDate, endDate);
    }

    public ChartsDataDTO getStockHistoryByProduct(Long productId, Date startDate, Date endDate) {
        List<Long> productIds = List.of(productId);
        return productStockHistoryLine(null, productIds, startDate, endDate);
    }

    private ChartsDataDTO productStockHistoryLine(Long warehouseId, List<Long> productIds, Date startDate, Date endDate) {
        List<String> labels = getProductNames(productIds);
        if (labels.size() != productIds.size()) {
            throw new ResourceNotFoundException("Could not find all product names" +
                    " for product ids: " + productIds + " and warehouse id: " + warehouseId + "." +
                    " Found " + labels.size() + " product names.");
        }

        List<List<Integer>> data = productIds.stream()
                .map(productId -> transactionService
                        .findProductStockHistoryByInterval(warehouseId, productId, startDate, endDate))
                .toList();

        if (data.size() != productIds.size()) {
            throw new ResourceNotFoundException("Could not find all product stock histories" +
                    " for product ids: " + productIds + " and warehouse id: " + warehouseId + "." +
                    " Found " + data.size() + " product stock histories.");
        }

        List<DataSetDTO> dataSets = productIds.stream()
                .map(productId -> DataSetDTO.builder()
                        .label(productService.findDTOById(productId).getName())
                        .data(data.get(productIds.indexOf(productId)))
                        .build())
                .toList();

        List<String> chartLabels = new ArrayList<>();
        Date date = startDate;
        while (date.before(endDate)) {
            chartLabels.add(date.toString());
            date = new Date(date.getTime() + 86400000);
        }
        chartLabels.add(endDate.toString());

        return ChartsDataDTO.builder()
                .labels(chartLabels)
                .datasets(dataSets)
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

    public ChartsDataDTO inventoryBarByProduct(Long productId) {
        List<DataSetDTO> dataSets = warehouseService.findAll()
                .stream()
                .map(warehouse -> DataSetDTO.builder()
                        .label(warehouse.getName())
                        .data(List.of(transactionService.findProductCurrentStock(warehouse.getId(), productId)))
                        .build()
                )
                .toList();

        return ChartsDataDTO.builder()
                .labels(List.of(productService.findById(productId).getName()))
                .datasets(dataSets)
                .build();
    }
}
