import {Adaptor} from "./Adaptor.js";
import {Warehouse} from "@/models/Warehouse.js";

/**
 * Adaptor for the warehouses REST API.
 *
 * @extends Adaptor
 * @category Services
 * @Author Junior Javier Brito Perez
 */
export default class WarehousesAdaptor extends Adaptor {
    constructor() {
        super("http://localhost:8087/api/warehouses");
    }

    /**
     * Fetches all warehouses from the REST API.
     *
     * @async
     * @returns {Promise<*>} The warehouses.
     */
    async asyncFindAll() {
        return (await this.fetchJson(this.resourceUrl + "/all"))
            .map(warehouse => Object.assign(new Warehouse(), warehouse));
    }

    /**
     * Fetches a warehouse by its ID from the REST API.
     *
     * @async
     * @param {string} id - The ID of the warehouse.
     * @returns {Promise<*>} The warehouse.
     */
    async asyncFindById(id) {
        return Object.assign(new Warehouse(), await this.fetchJson(this.resourceUrl + "/"+ id));
    }

    /**
     * Saves a warehouse to the REST API.
     *
     * @async
     * @param {Warehouse} warehouse - The warehouse to save.
     * @returns {Promise<*>} The saved warehouse.
     */
    async asyncSave(warehouse) {
        const options = {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(warehouse)
        }

        let response = await this.fetchJson(this.resourceUrl, options);

        if (response.status === 409) {
            options.method = "PUT";
            response = await this.fetchJson(this.resourceUrl + warehouse.id, options);
        }

        if (response.ok) {
            return Object.assign(new Warehouse(), await response.json());
        } else {
            console.log(response, !response.bodyUsed ? await response.text() : "");
            return null;
        }
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
            method: "DELETE",
            headers: {"Content-Type": "application/json"},
        }
        const response = await this.fetchJson(this.resourceUrl + id, options);
        if (response.ok) {
            return Object.assign(new Warehouse(), await response.json());
        } else {
            console.log(response, !response.bodyUsed ? await response.text() : "");
            return null;
        }
    }
}
