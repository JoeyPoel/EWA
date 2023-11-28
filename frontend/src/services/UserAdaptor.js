import {Adaptor} from "@/services/Adaptor";
import {User} from "@/models/User";
export default class UserAdaptor extends Adaptor {
    constructor(URL) {
        super(URL);
    }

    async asyncFindUser(user) {
        const options = {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(user)
        }
        return Object.assign(new User(), await this.fetchJson(this.resourceUrl + "/login", options));
    }

    async asyncFindAllByTeamId(teamId) {
        const options = {
            method: "GET",
            headers: {"Content-Type": "application/json"},
        }
        return (await this.fetchJson(this.resourceUrl + "/getAllByTeamId/" + teamId, options))
            .map(user => Object.assign(new User(), user));
    }

    async asyncDeleteById(id) {
        const options = {
            method: "DELETE",
            headers: {"Content-Type": "application/json"},
        }
        const response = await this.fetchJson(this.resourceUrl + id, options);
        if (response.ok) {
            return Object.assign(new User(), await response.json());
        } else {
            console.log(response, !response.bodyUsed ? await response.text() : "");
            return null;
        }
    }

    async asyncFindAll() {
        return (await this.fetchJson(this.resourceUrl + "/all"))
            .map(user => Object.assign(new User(), user));
    }

    async asyncFindById(id) {
        return Object.assign(new User(), await this.fetchJson(this.resourceUrl + "/" + id));
    }

    async asyncSave(user) {
        const options = {
            method: "POST", headers: {"Content-Type": "application/json"}, body: JSON.stringify(user)
        }

        let response = await this.fetchJson(this.resourceUrl + "/add", options);

        if (response) {
            return Object.assign(new User(), response);
        }
        return null;
    }

    // async asyncSave(user) {
    //     const options = {
    //         method: "POST",
    //         headers: {"Content-Type": "application/json"},
    //         body: JSON.stringify(user)
    //     }
    //
    //     let response = await this.fetchJson(this.resourceUrl + "/add", options);
    //
    //     // if (response.status === 409) {
    //     //     options.method = "PUT";
    //     //     response = await this.fetchJson(this.resourceUrl + user.id, options);
    //     // }
    //
    //     if (response) {
    //         return Object.assign(new User(), await response.json());
    //     } else {
    //         console.log(response, !response.bodyUsed ? await response.text() : "");
    //         return null;
    //     }
    // }
}