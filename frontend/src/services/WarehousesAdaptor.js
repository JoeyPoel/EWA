import {Adaptor} from "./Adaptor.js";
import {Warehouse} from "@/models/Warehouse.js";
import {Capacity} from "@/models/Capacity";

/**
 * Adaptor for the warehouses REST API.
 *
 * @extends Adaptor
 * @category Services
 * @Author Junior Javier Brito Perez
 */
export default class WarehousesAdaptor extends Adaptor {
    constructor(URL) {
        super(URL);
    }

    /**
     * Fetches all warehouses from the REST API.
     *
     * @async
     * @returns {Promise<*>} The warehouses.
     */
    async asyncGetAllWarehouses() {
        const response = await this.fetchJson(this.resourceUrl + "/getAllWarehouses");
        if (response) {
            return response.map(warehouse => Object.assign(new Warehouse(), warehouse));
        }
        return null;
    }

    /**
     * Fetches a warehouse by its ID from the REST API.
     *
     * @async
     * @param {string} id - The ID of the warehouse.
     * @returns {Promise<*>} The warehouse.
     */
    async asyncFindById(id) {
        return Object.assign(new Warehouse(), await this.fetchJson(this.resourceUrl + "/getWarehouseById/" + id));
    }

    async asyncAddWarehouse(warehouse) {
        const options = {
            method: "POST", headers: {"Content-Type": "application/json"}, body: JSON.stringify(warehouse)
        }

        const response = await this.fetchJson(this.resourceUrl + "/addWarehouse", options);

        if (response) {
            return Object.assign(new Warehouse(), response);
        }
        return null;
    }

    async asyncUpdateWarehouse(id, warehouse) {
        const options = {
            method: "PUT", headers: {"Content-Type": "application/json"}, body: JSON.stringify(warehouse)
        }

        const response = await this.fetchJson(this.resourceUrl + "/updateWarehouseById/" + id, options);

        if (response) {
            return Object.assign(new Warehouse(), response);
        }
        return null;
    }

    /**
     * Deletes a warehouse by its ID from the REST API.
     *
     * @async
     * @param {string} id - The ID of the warehouse.
     * @returns {Promise<*>} The deleted warehouse.
     */
    async asyncDeleteById(id) {
        const options = {
            method: "DELETE", headers: {"Content-Type": "application/json"},
        }
        const response = await this.fetchJson(this.resourceUrl + "/deleteWarehouseById/" + id, options);
        if (response) {
            return Object.assign(new Warehouse(), await response.json());
        }
        return null;
    }

    async asyncGetWarehouseCapacityCategories(id) {
        const response = await this.fetchJson(this.resourceUrl + "/getWarehouseCapacityCategories/" + id);
        if (response) {
            return response.map(category => Object.assign(new Capacity(), category));
        }
        return null;
    }

    async asyncAddWarehouseCapacityByWarehouseId(id, warehouseProductCategoryCapacity) {
        const options = {
            method: "POST", headers: {"Content-Type": "application/json"},
            body: JSON.stringify(warehouseProductCategoryCapacity)
        }

        const response = await this.fetchJson(this.resourceUrl + "/addWarehouseCapacityByWarehouseId/" + id,
            options);

        if (response) {
            return Object.assign(new Capacity(), response);
        }
        return null;
    }

    async asyncUpdateWarehouseCapacityById(id, warehouseProductCategoryCapacity) {
        const options = {
            method: "PUT", headers: {"Content-Type": "application/json"},
            body: JSON.stringify(warehouseProductCategoryCapacity)
        }

        const response = await this.fetchJson(
            this.resourceUrl + "/updateWarehouseCapacityById/" + id, options);

        if (response) {
            return Object.assign(new Capacity(), response);
        }
        return null;
    }

    async asyncGetWarehouseFromRoute(routeId) {
            if (!routeId) {
                return null;
            }
            return await this.asyncFindById(routeId);

    }
}
