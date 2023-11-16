import {Adaptor} from "./Adaptor.js";
import {Team} from "@/models/Team.js";

/**
 * Adaptor for the teams REST API.
 *
 * @extends Adaptor
 * @category Services
 * @Author Joey van der Poel
 */
export default class TeamsAdaptor extends Adaptor {
    constructor(URL) {
        super(URL);
    }

    /**
     * Fetches all teams from the REST API.
     *
     * @async
     * @returns {Promise<*>} The teams.
     */
    async asyncFindAll() {
        return (await this.fetchJson(this.resourceUrl + "/all"))
            .map(team => Object.assign(new Team(), team));
    }

    /**
     * Fetches a team by its ID from the REST API.
     *
     * @async
     * @param {string} id - The ID of the team.
     * @returns {Promise<*>} The warehouse.
     */
    async asyncFindById(id) {
        return Object.assign(new Team(), await this.fetchJson(this.resourceUrl + "/" + id));
    }

    /**
     * Saves a team to the REST API.
     *
     * @async
     * @param {Team} team - The team to save.
     * @returns {Promise<*>} The saved team.
     */
    async asyncSave(team) {
        const options = {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(team)
        }

        console.log(team)

        let response = await this.fetchJson(this.resourceUrl + "/add", options);

        if (response.ok) {
            return Object.assign(new Team(), await response.json());
        } else {
            console.log(response, !response.bodyUsed ? response.text : "");
            return null;
        }
    }

    /**
     * Updates a team to the REST API.
     *
     * @async
     * @param {Team} team - The team to save.
     * @returns {Promise<*>} The saved team.
     */
    async asyncUpdate(team) {
        const options = {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(team)
        }

        console.log(team)

        let response = await this.fetchJson(this.resourceUrl + "/" + team.id, options);

        if (response.ok) {
            return Object.assign(new Team(), await response.json());
        } else {
            console.log(response, !response.bodyUsed ? response.text : "");
            return null;
        }
    }

    /**
     * Deletes a team by its ID from the REST API.
     *
     * @async
     * @param {string} id - The ID of the team.
     * @returns {Promise<*>} The deleted team.
     */
    async asyncDeleteById(id) {
        const options = {
            method: "DELETE",
            headers: {"Content-Type": "application/json"},
        }
        const response = await this.fetchJson(this.resourceUrl + "/" + id, options);
        if (response.ok) {
            return Object.assign(new Team(), await response.json());
        } else {
            // TODO FIX THIS
            // console.log(response, !response.bodyUsed ? await response.text() : "");
            return null;
        }
    }
}
