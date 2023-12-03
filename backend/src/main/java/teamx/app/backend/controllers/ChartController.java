package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import teamx.app.backend.models.dto.charts.ChartsDataDTO;
import teamx.app.backend.services.ChartService;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/charts")
public class ChartController {
    private final ChartService chartService;

    @Autowired
    public ChartController(ChartService chartService) {
        this.chartService = chartService;
    }

    @GetMapping("/inventories/history")
    public ResponseEntity<ChartsDataDTO> inventoryBar() {
        return ResponseEntity.ok(chartService.inventoryBar(null));
    }

    @GetMapping("/inventories/{warehouseId}/history")
    public ResponseEntity<ChartsDataDTO> inventoryBar(@PathVariable Long warehouseId) {
        return ResponseEntity.ok(chartService.inventoryBar(warehouseId));
    }
}
