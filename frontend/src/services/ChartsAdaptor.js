import {Adaptor} from "@/services/Adaptor";
import {ChartsData} from "@/models/charts/ChartsData";

export class ChartsAdaptor extends Adaptor {
    constructor(URL) {
        super(URL);
    }

    // @GetMapping("/inventories/bar/current-stock/warehouses")
    //     public ResponseEntity<ChartsDataDTO> inventoryBarByAllWarehouses() {
    //         return ResponseEntity.ok(chartService.inventoryBarForAllWarehouses());
    //     }
    //
    //     @GetMapping("/inventories/bar/current-stock/warehouses/{warehouseId}")
    //     public ResponseEntity<ChartsDataDTO> inventoryBarByWarehouse(@PathVariable Long warehouseId) {
    //         return ResponseEntity.ok(chartService.inventoryBar(warehouseId));
    //     }
    //
    //     @GetMapping("/inventories/bar/current-stock/products")
    //     public ResponseEntity<ChartsDataDTO> inventoryBarTotalStock() {
    //         return ResponseEntity.ok(chartService.inventoryBar(null));
    //     }
    //
    //     @GetMapping("/inventories/bar/current-stock/products/{productId}")
    //     public ResponseEntity<ChartsDataDTO> inventoryBarByProduct(@PathVariable Long productId) {
    //         return ResponseEntity.ok(chartService.inventoryBarByProduct(productId));
    //     }
    //
    //     @GetMapping("/inventories/line/stock-history/products/{productId}")
    //     public ResponseEntity<ChartsDataDTO> inventoryLineByWarehouse(
    //             @PathVariable Long productId, @RequestParam Date startDate, @RequestParam Date endDate) {
    //         return ResponseEntity.ok(chartService
    //                 .getStockHistoryByProduct(productId, startDate, endDate)
    //         );
    //     }
    //
    //     @GetMapping("/inventories/line/stock-history/warehouses/{warehouseId}")
    //     public ResponseEntity<ChartsDataDTO> inventoryLineByProduct(
    //             @PathVariable Long warehouseId, @RequestParam Date startDate, @RequestParam Date endDate) {
    //         return ResponseEntity.ok(chartService
    //                 .getStockHistoryByWarehouse(warehouseId, startDate, endDate)
    //         );
    //     }

    async asyncInventoryBarByAllWarehouses() {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"}
        }
        const response = await this.fetchJson(this.resourceUrl
            + "/inventories/bar/current-stock/warehouses", options);

        return response ? Object.assign(new ChartsData(), response) : null;
    }

    async asyncInventoryBarByWarehouse(warehouseId) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"}
        }
        const response = await this.fetchJson(this.resourceUrl
            + "/inventories/bar/current-stock/warehouses/" + warehouseId, options);

        return response ? Object.assign(new ChartsData(), response) : null;
    }

    async asyncInventoryBarByAllProducts() {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"}
        }
        const response = await this.fetchJson(this.resourceUrl
            + "/inventories/bar/current-stock/products", options);

        return response ? Object.assign(new ChartsData(), response) : null;
    }

    async asyncInventoryBarByProduct(productId) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"}
        }
        const response = await this.fetchJson(this.resourceUrl
            + "/inventories/bar/current-stock/products/" + productId, options);

        return response ? Object.assign(new ChartsData(), response) : null;
    }

    async asyncInventoryLineByProduct(productId, startDate, endDate) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"}
        }
        const response = await this.fetchJson(this.resourceUrl + "/line/stock-history/products/" + productId
            + "?startDate=" + startDate + "&endDate=" + endDate, options);

        return response ? Object.assign(new ChartsData(), response) : null;
    }

    async asyncInventoryLineByWarehouse(warehouseId, startDate, endDate) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"}
        }
        const response = await this.fetchJson(this.resourceUrl + "/line/stock-history/warehouses/"
            + warehouseId + "?startDate=" + startDate + "&endDate=" + endDate, options);

        return response ? Object.assign(new ChartsData(), response) : null;
    }
}