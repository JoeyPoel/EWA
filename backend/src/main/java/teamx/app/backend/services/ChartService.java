package teamx.app.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Product;
import teamx.app.backend.models.Project;
import teamx.app.backend.models.Transaction;
import teamx.app.backend.models.dto.charts.ChartsDataDTO;
import teamx.app.backend.models.dto.charts.DataSetDTO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class ChartService {
    InventoryService inventoryService;
    TransactionService transactionService;
    OrderService orderService;
    ProductService productService;
    TeamService teamService;
    UserService userService;
    WarehouseService warehouseService;

    ProjectService projectService;

    @Autowired
    public ChartService(InventoryService inventoryService, TransactionService transactionService,
                        OrderService orderService, ProductService productService, TeamService teamService,
                        UserService userService, WarehouseService warehouseService,
                        ProjectService projectRepository) {
        this.inventoryService = inventoryService;
        this.transactionService = transactionService;
        this.orderService = orderService;
        this.productService = productService;
        this.teamService = teamService;
        this.userService = userService;
        this.warehouseService = warehouseService;
        this.projectService = projectRepository;
    }

    public ChartsDataDTO inventoryBarForAllWarehouses() {
        List<Long> productIds = productService.findAllActiveIds();

        List<Long> warehouseIds = warehouseService.findAllIds();

        List<DataSetDTO> dataSets = warehouseIds.stream().map(warehouseId -> DataSetDTO.builder().label(getWarehouseLabel(warehouseId)).data(getProductCurrentStocks(warehouseId, productIds)).build()).toList();

        return ChartsDataDTO.builder().labels(getProductNames(productIds)).datasets(dataSets).build();
    }

    public ChartsDataDTO inventoryBar(Long warehouseId) {
        List<Long> productIds = productService.findAllActiveIds();
        return currentStockBar(productIds, warehouseId);
    }

    private ChartsDataDTO currentStockBar(List<Long> productIds, Long warehouseId) {
        List<String> labels = getProductNames(productIds);
        List<Integer> data = getProductCurrentStocks(warehouseId, productIds);

        DataSetDTO dataSetDTO = DataSetDTO.builder().label(getWarehouseLabel(warehouseId)).data(data).build();


        return ChartsDataDTO.builder().labels(labels).datasets(List.of(dataSetDTO)).build();
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
            throw new ResourceNotFoundException("Could not find all product names" + " for product ids: " + productIds + " and warehouse id: " + warehouseId + "." + " Found " + labels.size() + " product names.");
        }

        List<List<Integer>> data = productIds.stream().map(productId -> transactionService.findProductStockHistoryByInterval(warehouseId, productId, startDate, endDate)).toList();

        if (data.size() != productIds.size()) {
            throw new ResourceNotFoundException("Could not find all product stock histories" + " for product ids: " + productIds + " and warehouse id: " + warehouseId + "." + " Found " + data.size() + " product stock histories.");
        }

        List<DataSetDTO> dataSets = productIds.stream().map(productId -> DataSetDTO.builder().label(productService.findDTOById(productId).getName()).data(data.get(productIds.indexOf(productId))).build()).toList();
//TODO: remove logging
        log.error("dataSets: " + dataSets);

        List<String> chartLabels = new ArrayList<>();
        Date date = startDate;
        while (date.before(endDate)) {
            chartLabels.add(date.toString());
            date = new Date(date.getTime() + 86400000);
        }
        chartLabels.add(endDate.toString());

        return ChartsDataDTO.builder().labels(chartLabels).datasets(dataSets).build();
    }

    private String getWarehouseLabel(Long warehouseId) {
        return warehouseId == null ? "All warehouses" : warehouseService.findById(warehouseId).getName();
    }

    private List<Integer> getProductCurrentStocks(Long warehouseId, List<Long> productIds) {
        return productIds.stream()
                .map(productId -> transactionService.findProductCurrentStock(warehouseId, productId)).toList();
    }

    private List<String> getProductNames(List<Long> productIds) {
        return productService.findAllByIds(productIds).stream().map(Product::getName).toList();
    }

    public ChartsDataDTO inventoryBarByProduct(Long productId) {
        List<DataSetDTO> dataSets = warehouseService.findAll()
                .stream()
                .map(warehouse -> DataSetDTO.builder()
                        .label(warehouse.getName())
                        .data(List.of(transactionService.findProductCurrentStock(warehouse.getId(), productId)))
                        .build()).toList();

        return ChartsDataDTO.builder().labels(List.of(productService.findById(productId).getName()))
                .datasets(dataSets).build();
    }

    // TODO: This is a test method, remove or implement properly
    public ChartsDataDTO getStockHistoryByAllProducts(Date startDate, Date endDate) {
        List<Long> productIds = productService.findAllActiveIds();
        return productStockHistoryLine(null, productIds, startDate, endDate);
    }

    public ChartsDataDTO projectPieByStatusAndDateBetween(Long warehouseId, Date startDate, Date endDate) {
        int projectsInProgress = projectService.findProjectsByStatusAndDateBetween(
                        Project.Status.IN_PROGRESS, warehouseId, startDate, endDate)
                .size();

        int projectsFinished = projectService.findProjectsByStatusAndDateBetween(
                        Project.Status.FINISHED, warehouseId, startDate, endDate)
                .size();

        DataSetDTO dataSetDTO = DataSetDTO.builder()
                .data(List.of(projectsInProgress, projectsFinished))
                .build();

        return ChartsDataDTO.builder()
                .labels(List.of("In progress", "Finished"))
                .datasets(List.of(dataSetDTO))
                .build();
    }

    public ChartsDataDTO getProjectsPieByStatusThisMonth(Long warehouseId) {
        Date startDate = new Date(System.currentTimeMillis());
        Date endDate = new Date(System.currentTimeMillis() + 2592000000L);
        return projectPieByStatusAndDateBetween(warehouseId, startDate, endDate);
    }

    public HashMap<String, Long> getLifetimeStatistics() {
        List<Project> projects = projectService.findAll();
        HashMap<String, Long> map = new HashMap<>();
        map.put("totalProjects", (long) projects.size());
        map.put("totalFinishedProjects", lifetimeCompletedProjects(projects));
        map.put("totalMaterialsUsed", lifetimeMaterialsQuantityUsed(projects));
        map.put("totalSolarPanelsInstalled", lifetimeSolarPanelsInstalled(projects));
        map.put("totalEmployees", (long) userService.getAll().size());
        map.put("totalWarehouses", (long) warehouseService.findAll().size());
        map.put("totalTeams", (long) teamService.findAll().size());
        map.put("totalProductCost", lifetimeProductCost(projects));
        map.put("totalOrders", orderService.totalOrders());
        return map;
    }

    private long lifetimeSolarPanelsInstalled(List<Project> projects) {
        return projects.stream()
                .mapToLong(project -> project.getMaterials().stream()
                        .filter(material -> material.getProduct().getName().toLowerCase().contains("solar panel"))
                        .mapToLong(Transaction::getQuantity).sum()).sum();
    }

    private long lifetimeProductCost(List<Project> projects) {
        return projects.stream()
                .mapToLong(project -> project.getMaterials().stream()
                        .mapToLong(material -> (long) (material.getProduct().getPrice() *
                                material.getQuantity())).sum()).sum();
    }


    private long lifetimeMaterialsQuantityUsed(List<Project> projects) {
        return projects.stream()
                .mapToLong(project -> project.getMaterials().stream()
                        .mapToLong(Transaction::getQuantity).sum()).sum();
    }

    private long lifetimeCompletedProjects(List<Project> projects) {
        return projects.stream()
                .filter(project -> project.getStatus() == Project.Status.FINISHED)
                .count();
    }

//    public ChartsDataDTO getProjectsBarByWeek(Long warehouseId, Date startDate, Date endDate) {
//        List<Project> projects;
//        if (warehouseId == null) {
//            projects = projectService.findAllDateBetween(startDate, endDate);
//        } else {
//            projects = projectService.findAllByWarehouseIdAndDateBetween(warehouseId, startDate, endDate);
//        }
//        List<String> labels = new ArrayList<>();
//        List<Integer> data = new ArrayList<>();
//        Date date = startDate;
//        while (date.before(endDate)) {
//            labels.add(date.toString());
//            data.add(0);
//            date = new Date(date.getTime() + 604800000);
//        }
//        labels.add(endDate.toString());
//        data.add(0);
//        for (Project project : projects) {
//            for (int i = 0; i < labels.size() - 1; i++) {
//                if (project.getEndDate().after(Date.valueOf(labels.get(i))) && project.getEndDate().before(Date.valueOf(labels.get(i + 1)))) {
//                    data.set(i, data.get(i) + 1);
//                }
//            }
//        }
//        DataSetDTO dataSetDTO = DataSetDTO.builder().label("Projects").data(data).build();
//        return ChartsDataDTO.builder().labels(labels).datasets(List.of(dataSetDTO)).build();
//    }
}
