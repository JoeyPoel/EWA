import {Adaptor} from "@/services/Adaptor";
import {User} from "@/models/User";

export default class UserAdaptor extends Adaptor {
    constructor(URL) {
        super(URL);
    }

    async asyncGetUser(user) {
        const options = {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(user)
        }

        const response = await this.fetchJson(this.resourceUrl + "/login", options);

        return response ? User.fromJson(response) : null;
    }

    async asyncGetAllByTeamId(teamId) {
        const options = {
            method: "GET",
            headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(this.resourceUrl + "/team/" + teamId, options);

        return response ? response.map(user => User.fromJson(user)) : null;
    }

    async asyncGetAllByNoTeam() {
        const options = {
            method: "GET",
            headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(this.resourceUrl + "/noTeam", options);

        return response ? response.map(user => User.fromJson(user)) : null;
    }

    async asyncDeleteById(id) {
        const options = {
            method: "DELETE",
            headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(this.resourceUrl + "/" + id, options);

        return response ? User.fromJson(response) : null;
    }

    async asyncGetAll() {
        const options = {
            method: "GET",
            headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(this.resourceUrl, options);

        return response ? response.map(user => User.fromJson(user)) : null;
    }

    async asyncGetById(id) {
        const options = {
            method: "GET",
            headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(this.resourceUrl + "/" + id, options);

        return response ? User.fromJson(response) : null;
    }

    async asyncSave(user) {
        const options = {
            method: "POST", headers: {"Content-Type": "application/json"}, body: JSON.stringify(user)
        }

        let response = await this.fetchJson(this.resourceUrl + "/add", options);

        return response ? User.fromJson(response) : null;
    }

    async asyncUpdate(id, user) {
        const options = {
            method: "PUT", headers: {"Content-Type": "application/json"}, body: JSON.stringify(user)
        }

        let response = await this.fetchJson(this.resourceUrl + "/" +  id, options);

        return response ? User.fromJson(response) : null;
    }

    async asyncDeactivateUser(id) {
        const options = {
            method: "PUT", headers: {"Content-Type": "application/json"}
        }

        let response = await this.fetchJson(this.resourceUrl + "/deactivate/" + id, options);
        return response ? User.fromJson(response) : null;
    }
}