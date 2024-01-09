import {Adaptor} from "./Adaptor.js";
import {Team} from "@/models/Team.js";

/**
 * Represents an adaptor for interacting with team through a REST API.
 * Extends the base Adaptor class.
 *
 * @extends Adaptor
 *
 * @Author Joey van der Poel
 * @Author Junior Javier Brito Perez
 */
export default class TeamsAdaptor extends Adaptor {
    constructor(URL) {
        super(URL);
    }

    /**
     * Fetches all team from the REST API.
     *
     * @async
     * @returns {Promise<*>} The team.
     */
    async asyncFindAll() {
        const options = {
            method: "GET",
            headers: {"Content-Type": "application/json"},
        };

        const response = await this.fetchJson(this.resourceUrl, options);

        if (Array.isArray(response)) {
            return response.map(team => Team.fromJson(team));
        } else {
            return []; // Return an empty array when no team are retrieved
        }
    }

    /**
     * Fetches a team by its ID from the REST API.
     *
     * @async
     * @param {string} id - The ID of the team.
     * @returns {Promise<*>} The warehouse.
     */
    async asyncFindById(id) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(this.resourceUrl + "/" + id, options);

        return response ? Team.fromJson(response) : null;
    }

    /**
     * Retrieves all team by warehouse ID.
     *
     * @param {string} warehouseId - The warehouse ID to filter team by.
     * @returns {Promise<Array<Team>|null>} - A promise that resolves to an array of Team objects or null if no team
     * are found.
     */
    async asyncFindAllByWarehouseId(warehouseId) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(this.resourceUrl + "/warehouse/" + warehouseId, options);

        if (Array.isArray(response)) {
            return response.map(team => Team.fromJson(team));
        } else {
            return []; // Return an empty array when no team are retrieved
        }
    }

    /**
     * Adds a team asynchronously.
     *
     * @param {Object} team - The team object to be added.
     * @returns {Promise<Team|null>} - A promise that resolves to the added team as a Team object, or null if the
     * operation fails.
     */
    async asyncAdd(team) {
        const options = {
            method: "POST", headers: {"Content-Type": "application/json"}, body: JSON.stringify(team)
        }

        const response = await this.fetchJson(this.resourceUrl, options);

        return response ? Team.fromJson(response) : null;
    }

    /**
     * Updates the team with the given id asynchronously.
     *
     * @param {string} id - The id of the team to update.
     * @param {object} team - The updated team object.
     *
     * @returns {Promise<Team|null>} - A promise that resolves to the updated Team object if successful, otherwise
     * resolves to null.
     */
    async asyncUpdate(id, team) {
        const options = {
            method: "PUT", headers: {"Content-Type": "application/json"}, body: JSON.stringify(team)
        }

        const response = await this.fetchJson(this.resourceUrl + "/" + id, options);

        return response ? Team.fromJson(response) : null;
    }

    /**
     * Deletes a team by its ID from the REST API.
     *
     * @async
     * @param {string} id - The ID of the team.
     * @returns {Promise<*>} The deleted team.
     */
    async asyncDeleteById(id) {
        const response = await this.fetchJson(`${this.resourceUrl}/${id}`,
            {
            method: "DELETE",
            headers: {"Content-Type": "application/json"}
        });

        return response ? Team.fromJson(response) : null;
    }
}
