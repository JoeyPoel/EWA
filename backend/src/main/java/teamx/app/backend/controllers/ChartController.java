package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import teamx.app.backend.models.dto.charts.ChartsDataDTO;
import teamx.app.backend.services.ChartService;

import java.sql.Date;
import java.util.HashMap;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/charts")
public class ChartController {
    private final ChartService chartService;

    @Autowired
    public ChartController(ChartService chartService) {
        this.chartService = chartService;
    }

    @GetMapping("/inventories/bar/current-stock/warehouses")
    public ResponseEntity<ChartsDataDTO> inventoryBarByAllWarehouses() {
        return ResponseEntity.ok(chartService.inventoryBarForAllWarehouses());
    }

    @GetMapping("/inventories/bar/current-stock/warehouses/{warehouseId}")
    public ResponseEntity<ChartsDataDTO> inventoryBarByWarehouse(@PathVariable Long warehouseId) {
        return ResponseEntity.ok(chartService.inventoryBar(warehouseId));
    }

    @GetMapping("/inventories/bar/current-stock/products")
    public ResponseEntity<ChartsDataDTO> inventoryBarTotalStock() {
        return ResponseEntity.ok(chartService.inventoryBar(null));
    }

    @GetMapping("/inventories/bar/current-stock/products/{productId}")
    public ResponseEntity<ChartsDataDTO> inventoryBarByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(chartService.inventoryBarByProduct(productId));
    }

    @GetMapping("/inventories/line/stock-history/products")
    public ResponseEntity<ChartsDataDTO> inventoryLineByAllProducts(
            @RequestParam Date startDate, @RequestParam Date endDate) {
        return ResponseEntity.ok(chartService
                .getStockHistoryByAllProducts(startDate, endDate));
    }

    @GetMapping("/inventories/line/stock-history/products/{productId}")
    public ResponseEntity<ChartsDataDTO> inventoryLineByWarehouse(
            @PathVariable Long productId, @RequestParam Date startDate, @RequestParam Date endDate) {
        return ResponseEntity.ok(chartService
                .getStockHistoryByProduct(productId, startDate, endDate)
        );
    }

    @GetMapping("/inventories/line/stock-history/warehouses/{warehouseId}")
    public ResponseEntity<ChartsDataDTO> inventoryLineByProduct(
            @PathVariable Long warehouseId, @RequestParam Date startDate, @RequestParam Date endDate) {
        return ResponseEntity.ok(chartService
                .getStockHistoryByWarehouse(warehouseId, startDate, endDate)
        );
    }

    @GetMapping("/projects/pie/by-status-this-month/warehouses/{warehouseId}")
    public ResponseEntity<ChartsDataDTO> projectsPieByStatusThisMonth(@PathVariable Long warehouseId) {
        return ResponseEntity.ok(chartService.getProjectsPieByStatusThisMonth(warehouseId));
    }

    @GetMapping("/projects/pie/by-status-this-month/warehouses")
    public ResponseEntity<ChartsDataDTO> projectsPieByStatusThisMonthAllWarehouses() {
        return ResponseEntity.ok(chartService.getProjectsPieByStatusThisMonth(null));
    }

    @GetMapping("/projects/bar/by-interval/warehouses/{warehouseId}")
    public ResponseEntity<ChartsDataDTO> projectsBarByInterval(
            @PathVariable Long warehouseId, @RequestParam Date startDate, @RequestParam Date endDate,
            @RequestParam String interval) {
        return ResponseEntity.ok(chartService.getProjectsBarByInterval(warehouseId, startDate, endDate, interval));
    }

    @GetMapping("/projects/bar/by-interval/warehouses")
    public ResponseEntity<ChartsDataDTO> projectsBarByIntervalAllWarehouses(
            @RequestParam Date startDate, @RequestParam Date endDate,
            @RequestParam String interval) {
        return ResponseEntity.ok(chartService.getProjectsBarByInterval(null, startDate, endDate, interval));
    }

    @GetMapping("/statistics/lifetime")
    public ResponseEntity<HashMap<String, Long>> getLifetimeStatistics() {
        return ResponseEntity.ok(chartService.getLifetimeStatistics());
    }
}
