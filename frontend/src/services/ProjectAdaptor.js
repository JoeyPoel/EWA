import {Adaptor} from "@/services/Adaptor";
import {Project} from "@/models/Project";
import {InventoryProject} from "@/models/InventoryProject";
import {Task} from "@/models/Task";


/**
 * Class representing a ProjectAdaptor.
 * @extends Adaptor
 *
 * @Author Nizar Amine
 * @Author Junior Javier Brito Perez
 */
export default class ProjectAdaptor extends Adaptor {
    constructor(URL) {
        super(URL);
    }

    /**
     * Retrieves all projects asynchronously.
     *
     * @returns {Promise<Array<Project>|null>} - A promise that resolves to an array of Project objects
     * representing all projects, or null if no projects are found.
     *
     * @async
     */
    async asyncGetAll() {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }
        const response = await this.fetchJson(this.resourceUrl, options)
        return response ? response.map(project => Project.fromJson(project)) : null;
    }

    /**
     * Asynchronously retrieves a project by its ID.
     *
     * @param {string} id - The ID of the project to retrieve.
     * @returns {Promise<Project|null>} A promise that resolves to the project if found, or null if not found.
     */
    async asyncGetById(id) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(this.resourceUrl + "/" + id, options)

        return response ? Project.fromJson(response) : null;
    }

    /**
     * Performs an asynchronous addition of a project.
     * @param {Object} project - The project to be added.
     * @return {Promise<Project|null>} - A promise that resolves to the added project (converted from JSON),
     *                                   or null if the response is falsy.
     */
    async asyncAdd(project) {
        const options = {
            method: "POST", headers: {"Content-Type": "application/json"}, body: JSON.stringify(project)
        }
        const response = await this.fetchJson(this.resourceUrl, options);
        return response ? Project.fromJson(response) : null;
    }

    /**
     * Updates a project asynchronously.
     *
     * @param {string} id - The ID of the project to update.
     * @param {Object} project - The updated project data.
     * @returns {Promise<Project|null>} A promise that resolves with the updated project object, or null if the update
     * fails.
     */
    async asyncUpdate(id, project) {
        const options = {
            method: "PUT", headers: {"Content-Type": "application/json"}, body: JSON.stringify(project)
        }
        const response = await this.fetchJson(this.resourceUrl + "/" + id, options);

        return response ? Project.fromJson(response) : null;
    }

    /**
     * Deletes a project by ID asynchronously.
     *
     * @param {string} id - The ID of the project to delete.
     * @returns {Promise<Project|null>} - A Promise that resolves with the deleted Project object if successful,
     *                                    resolves with null if the response is empty or unsuccessful.
     */
    async asyncDeleteById(id) {
        const options = {
            method: "DELETE", headers: {"Content-Type": "application/json"}
        }

        const response = await this.fetchJson(this.resourceUrl + "/" + id, options);

        return response ? Project.fromJson(response) : null;
    }

    /**
     * Retrieves all projects by warehouse ID.
     *
     * @param {number} warehouseId - The ID of the warehouse.
     *
     * @return {Promise<Project[]|null>} A promise that resolves to an array of projects mapped from the response JSON,
     * or null if response is falsy.
     */
    async asyncGetAllByWarehouseId(warehouseId) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(this.resourceUrl + "/warehouse/" + warehouseId, options)

        return response ? response.map(project => Project.fromJson(project)) : null;
    }

    async asyncGetProjectProducts(id) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(this.resourceUrl + "/inventory" + "/" + id, options)
        return response ? response.map(inventoryProject => InventoryProject.fromJson(inventoryProject)) : null;
    }

    async asyncGetProjectTasks(id) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(this.resourceUrl + "/tasks" + "/" + id, options)
        return response ? response.map(task => Task.fromJson(task)) : null;
    }
}