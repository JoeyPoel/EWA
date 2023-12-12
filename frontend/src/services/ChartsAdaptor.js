import {Adaptor} from "@/services/Adaptor";
import {ChartsData} from "@/models/charts/ChartsData";

export class ChartsAdaptor extends Adaptor {
    constructor(URL) {
        super(URL);
    }
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

    async asyncInventoryLineByAllProducts(startDate, endDate) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"}
        }

        const response = await this.fetchJson(this.resourceUrl +
            "/inventories/line/stock-history/products" +
            "?startDate=" + startDate + "&endDate=" + endDate, options);

        return response ? Object.assign(new ChartsData(), response) : null;
    }

    async asyncInventoryLineByProduct(productId, startDate, endDate) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"}
        }
        const response = await this.fetchJson(this.resourceUrl +
            "/inventories/line/stock-history/products/" + productId
            + "?startDate=" + startDate + "&endDate=" + endDate, options);

        return response ? Object.assign(new ChartsData(), response) : null;
    }

    async asyncInventoryLineByWarehouse(warehouseId, startDate, endDate) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"}
        }
        const response = await this.fetchJson(this.resourceUrl +
            "/inventories/line/stock-history/warehouses/"
            + warehouseId + "?startDate=" + startDate + "&endDate=" + endDate, options);

        return response ? Object.assign(new ChartsData(), response) : null;
    }

    async asyncProjectsPieByStatusThisMonth(warehouseId) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"}
        }
        const response = await this.fetchJson(this.resourceUrl +
            "/projects/pie/by-status-this-month/warehouses/" + warehouseId, options);

        return response ? Object.assign(new ChartsData(), response) : null;
    }

    async asyncProjectsPieByStatusThisMonthAll() {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"}
        }
        const response = await this.fetchJson(this.resourceUrl +
            "/projects/pie/by-status-this-month/warehouses", options);

        return response ? Object.assign(new ChartsData(), response) : null;
    }

    async asyncLifetimeStatisticsAll() {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"}
        }
        return await this.fetchJson(this.resourceUrl +
            "/statistics/lifetime", options);
    }

    async asyncProjectsBarByIntervalAllWarehouses(startDate, endDate, interval) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"}
        }
        return await this.fetchJson(this.resourceUrl +
            "/projects/bar/by-interval/warehouses?startDate=" + startDate + "&endDate=" + endDate + "&interval=" + interval, options);
    }

    async asyncProjectsBarByInterval(warehouseId, startDate, endDate, interval) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"}
        }
        return await this.fetchJson(this.resourceUrl +
            "/projects/bar/by-interval/warehouses/" + warehouseId + "?startDate=" + startDate + "&endDate=" + endDate + "&interval=" + interval, options);
    }

    async asyncInventoryLineByInterval(warehouseId, productIds, startDate, endDate, interval) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"}
        }
        return await this.fetchJson(this.resourceUrl +
            "/inventories/line/by-interval/warehouses/" + warehouseId + "?productIds="
            + productIds + "&startDate=" + startDate + "&endDate=" + endDate + "&interval=" + interval, options);
    }

    async asyncInventoryLineByIntervalAllWarehouses(productIds, startDate, endDate, interval) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"}
        }
        return await this.fetchJson(this.resourceUrl +
            "/inventories/line/by-interval/warehouses?productIds=" + productIds + "&startDate="
            + startDate + "&endDate=" + endDate + "&interval=" + interval, options);
    }

}