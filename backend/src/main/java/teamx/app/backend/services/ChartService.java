package teamx.app.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.utils.DTO.ChartsDataDTO;
import teamx.app.backend.utils.DTO.DataSetDTO;
import teamx.app.backend.models.Product;
import teamx.app.backend.models.Project;
import teamx.app.backend.models.Transaction;

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

    public ChartsDataDTO getStockLineByInterval(Long warehouseId, List<Long> productIds, Date startDate, Date endDate,
                                                String interval) {
        return switch (interval) {
            case "month" -> getStockLineByMonth(warehouseId, productIds, startDate, endDate);
            case "week" -> getStockLineByWeek(warehouseId, productIds, startDate, endDate);
            case "day" -> getStockLineByDay(warehouseId, productIds, startDate, endDate);
            default -> throw new IllegalArgumentException("Invalid interval: " + interval);
        };
    }

    private ChartsDataDTO getStockLineByWeek(Long warehouseId, List<Long> productIds, Date startDate, Date endDate) {
        List<DataSetDTO> dataSets = productIds.stream()
                .map(id -> getStockLineData(warehouseId, id, startDate, endDate, 604800000))
                .toList();
        List<String> labels = new ArrayList<>();
        labels.add(startDate.toString());
        for (Date date = startDate; date.before(endDate); date = new Date(date.getTime() + 604800000)) {
            labels.add(date.toString());
        }
        labels.add(endDate.toString());
        return ChartsDataDTO.builder()
                .labels(labels)
                .datasets(dataSets)
                .build();
    }

    private ChartsDataDTO getStockLineByDay(Long warehouseId, List<Long> productIds, Date startDate, Date endDate) {
        List<DataSetDTO> dataSets = productIds.stream()
                .map(id -> getStockLineData(warehouseId, id, startDate, endDate, 86400000))
                .toList();
        List<String> labels = new ArrayList<>();
        labels.add(startDate.toString());
        for (Date date = startDate; date.before(endDate); date = new Date(date.getTime() + 86400000)) {
            labels.add(date.toString());
        }
        labels.add(endDate.toString());
        return ChartsDataDTO.builder()
                .labels(labels)
                .datasets(dataSets)
                .build();
    }

    private ChartsDataDTO getStockLineByMonth(Long warehouseId, List<Long> productIds, Date startDate, Date endDate) {
        List<DataSetDTO> dataSets = productIds.stream()
                .map(id -> getStockLineData(warehouseId, id, startDate, endDate, 2592000000L))
                .toList();
        List<String> labels = new ArrayList<>();
        labels.add(startDate.toString());
        for (Date date = startDate; date.before(endDate); date = new Date(date.getTime() + 2592000000L)) {
            labels.add(date.toString());
        }
        labels.add(endDate.toString());
        return ChartsDataDTO.builder()
                .labels(labels)
                .datasets(dataSets)
                .build();
    }

    private DataSetDTO getStockLineData(Long warehouseId, Long productId, Date startDate, Date endDate,
                                        long interval) {
        List<Integer> lineData = transactionService.findProductStockHistoryByInterval(warehouseId, productId,
                startDate, endDate, interval);
        return DataSetDTO.builder()
                .label(productService.findById(productId).getName())
                .data(lineData)
                .build();
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

    public ChartsDataDTO getProjectsBarByInterval(Long warehouseId, Date startDate, Date endDate, String interval) {
        return switch (interval) {
            case "month" -> getProjectsBarByMonth(warehouseId, startDate, endDate);
            case "week" -> getProjectsBarByWeek(warehouseId, startDate, endDate);
            case "day" -> getProjectsBarByDay(warehouseId, startDate, endDate);
            default -> throw new IllegalArgumentException("Invalid interval: " + interval);
        };
    }

    private ChartsDataDTO getProjectsBarByMonth(Long warehouseId, Date startDate, Date endDate) {
        return getProjectsBarData(warehouseId, startDate, endDate, "Projects", 2592000000L);
    }

    private ChartsDataDTO getProjectsBarByWeek(Long warehouseId, Date startDate, Date endDate) {
        return getProjectsBarData(warehouseId, startDate, endDate, "Projects", 604800000);
    }

    private ChartsDataDTO getProjectsBarByDay(Long warehouseId, Date startDate, Date endDate) {
        return getProjectsBarData(warehouseId, startDate, endDate, "Projects", 86400000);
    }

    private ChartsDataDTO getProjectsBarData(Long warehouseId, Date startDate, Date endDate, String label, long duration) {
        List<Project> projects = projectService.findProjectsByDateBetween(warehouseId, startDate, endDate);
        List<String> labels = new ArrayList<>();
        List<Integer> data = new ArrayList<>();

        for (Date date = startDate; date.before(endDate); date = new Date(date.getTime() + duration)) {
            labels.add(date.toString());
            data.add(0);
        }

        labels.add(endDate.toString());
        data.add(0);
        setProjectsData(projects, labels, data);

        DataSetDTO dataSetDTO = DataSetDTO.builder().label(label).data(data).build();
        return ChartsDataDTO.builder().labels(labels).datasets(List.of(dataSetDTO)).build();
    }

    private void setProjectsData(List<Project> projects, List<String> labels, List<Integer> data) {
        for (Project project : projects) {
            for (int i = 0; i < labels.size() - 1; i++) {
                if (isInDateRange(labels.get(i), labels.get(i + 1), project.getEndDate())) {
                    data.set(i, data.get(i) + 1);
                }
            }
        }
    }

    private boolean isInDateRange(String start, String end, Date target) {
        return target.after(Date.valueOf(start)) && target.before(Date.valueOf(end));
    }
}
