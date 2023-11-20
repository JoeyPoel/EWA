import { Adaptor } from "./Adaptor.js";
import { Project } from "@/models/project.js";

/**
 * Adaptor for the project REST API.
 *
 * @extends Adaptor
 * @category Services
 * @Author Nizar Amine
 */
export default class ProjectAdaptor extends Adaptor {
    constructor(URL) {
        super(URL);
    }

    /**
     * Fetches all projects from the REST API.
     *
     * @async
     * @returns {Promise<*>} The projects.
     */
    async asyncFindAll() {
        const options = {
            method: "GET", headers: { "Content-Type": "application/json" },
        };

        const response = await this.fetchJson(this.resourceUrl + "/getAllProjects", options);
        if (response) {
            return response.map(project => Object.assign(new Project(), project));
        }
    }

    /**
     * Fetches a project by its ID from the REST API.
     *
     * @async
     * @param {string} id - The ID of the project.
     * @returns {Promise<*>} The project.
     */
    async asyncFindById(id) {
        return Object.assign(new Project(), await this.fetchJson(this.resourceUrl + "/getProjectById/" + id));
    }

    /**
     * Saves a project to the REST API.
     *
     * @async
     * @param {Project} project - The project to save.
     * @returns {Promise<*>} The saved project.
     */
    async asyncAdd(project) {
        const options = {
            method: "POST", headers: { "Content-Type": "application/json" }, body: JSON.stringify(project),
        };

        let response = await this.fetchJson(this.resourceUrl + "/addProject", options);

        if (response) {
            return Object.assign(new Project(), response);
        }
        return null;
    }

    async asyncUpdate(id, project) {
        const options = {
            method: "PUT", headers: { "Content-Type": "application/json" }, body: JSON.stringify(project),
        };

        let response = await this.fetchJson(this.resourceUrl + "/updateProjectById/" + id, options);

        if (response) {
            return Object.assign(new Project(), response);
        }
        return null;
    }

    /**
     * Deletes a project by its ID from the REST API.
     *
     * @async
     * @param {string} id - The ID of the project.
     * @returns {Promise<*>} The deleted project.
     */
    async asyncDeleteById(id) {
        const options = {
            method: "DELETE", headers: { "Content-Type": "application/json" },
        };
        const response = await this.fetchJson(this.resourceUrl + "/deleteProjectById/" + id, options);

        if (response) {
            return Object.assign(new Project(), response);
        }
        return null;
    }
}
