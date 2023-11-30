import {Adaptor} from "./Adaptor.js";
import {Warehouse} from "@/models/Warehouse.js";
import {Capacity} from "@/models/Capacity";

/**
 * Adaptor for the warehouses REST API.
 *
 * @extends Adaptor
 *
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
    async asyncGetAll() {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(this.resourceUrl, options);

        return response ? response.map(warehouse => Warehouse.fromJson(warehouse)) : null;
    }

    /**
     * Fetches a warehouse by its ID from the REST API.
     *
     * @async
     * @param {string} id - The ID of the warehouse.
     * @returns {Promise<*>} The warehouse.
     */
    async asyncGetById(id) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(this.resourceUrl + "/" + id, options);

        return response ? Warehouse.fromJson(response) : null;
    }

    /**
     * Sends a POST request to add a warehouse asynchronously.
     * @param {Object} warehouse - The warehouse object to be added.
     * @returns {Promise<Warehouse|null>} - A Promise that resolves with the added warehouse object, or null if the
     * request fails.
     */
    async asyncAdd(warehouse) {
        const options = {
            method: "POST", headers: {"Content-Type": "application/json"}, body: JSON.stringify(warehouse)
        }

        const response = await this.fetchJson(this.resourceUrl, options);

        return response ? Warehouse.fromJson(response) : null;
    }

    /**
     * Updates a warehouse asynchronously.
     *
     * @param {string} id - The ID of the warehouse to be updated.
     * @param {object} warehouse - The updated warehouse object.
     *                           It should be in JSON format.
     *
     * @returns {Promise<Warehouse|null>} - A promise that resolves with the updated Warehouse object
     *                                    or null if the update was unsuccessful.
     */
    async asyncUpdate(id, warehouse) {
        const options = {
            method: "PUT", headers: {"Content-Type": "application/json"}, body: JSON.stringify(warehouse)
        }

        const response = await this.fetchJson(this.resourceUrl + "/" + id, options);

        return response ? Warehouse.fromJson(response) : null;
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

        const response = await this.fetchJson(this.resourceUrl + "/" + id, options);

        return response ? Warehouse.fromJson(response) : null;
    }

    /**
     * Retrieves the capacity information for the specified ID asynchronously.
     *
     * @param {number|string} id - The ID of the resource.
     * @return {Promise<Array<Capacity>|null>} - A Promise that resolves to an array of capacity objects, or null if
     * there is no response.
     */
    async asyncGetCapacity(id) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(this.resourceUrl + "/" + id + "/capacity");

        return response ? response.map(capacity => Capacity.fromJson(capacity)) : null;
    }

    /**
     * Adds warehouse capacity by warehouse id.
     *
     * @param {string} id - The id of the warehouse.
     * @param {object} warehouseProductCategoryCapacity - The warehouse product category capacity.
     * @returns {object} - The capacity after adding the warehouse capacity.
     */
    async asyncAddCapacityByWarehouseId(id, warehouseProductCategoryCapacity) {
        const options = {
            method: "POST", headers: {"Content-Type": "application/json"},
            body: JSON.stringify(warehouseProductCategoryCapacity)
        }

        const response = await this.fetchJson(this.resourceUrl + "/addWarehouseCapacityByWarehouseId/" + id,
            options);

        return response ? Capacity.fromJson(response) : null;
    }

    /**
     * Updates warehouse capacity by ID asynchronously.
     *
     * @param {string} id - The ID of the warehouse.
     * @param {object} warehouseProductCategoryCapacity - The new warehouse capacity information.
     * @return {Promise<Capacity|null>} - A promise that resolves to the updated warehouse capacity as a Capacity object
     * , or null if the update failed.
     */
    async asyncUpdateCapacityById(id, warehouseProductCategoryCapacity) {
        const options = {
            method: "PUT", headers: {"Content-Type": "application/json"},
            body: JSON.stringify(warehouseProductCategoryCapacity)
        }

        const response = await this.fetchJson(
            this.resourceUrl + "/updateWarehouseCapacityById/" + id, options);

        return response ? Capacity.fromJson(response) : null;
    }
}
