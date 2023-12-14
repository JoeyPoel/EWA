import {Adaptor} from "./Adaptor.js";
import {Warehouse} from "@/models/Warehouse.js";

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
    async asyncFindAll() {
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
    async asyncFindById(id) {
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
}
