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
        return (await this.fetchJson(this.resourceUrl + "/getAllTeams"))
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
        return Object.assign(new Team(), await this.fetchJson(this.resourceUrl + "/getTeamById/" + id));
    }

    async asyncAddTeam(team) {
        const options = {
            method: "POST", headers: {"Content-Type": "application/json"}, body: JSON.stringify(team)
        }
        const response = await this.fetchJson(this.resourceUrl + "/addTeam", options);
        if (response) {
            return Object.assign(new Team(), response);
        }
        return null;

    }

    async asyncUpdateTeam(id, team) {
        const options = {
            method: "PUT", headers: {"Content-Type": "application/json"}, body: JSON.stringify(team)
        }
        const response = await this.fetchJson(this.resourceUrl + "/updateTeamById/" + id, options);
        if (response) {
            return Object.assign(new Team(), response);
        }
        return null;
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
            method: "DELETE", headers: {"Content-Type": "application/json"},
        }
        const response = await this.fetchJson(this.resourceUrl + "/deleteTeamById/" + id, options);
        if (response) {
            return Object.assign(new Team(), response);
        }
        return null;
    }
}
