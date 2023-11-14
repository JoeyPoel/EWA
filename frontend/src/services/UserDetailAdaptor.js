import {Adaptor} from "@/services/Adaptor";
import {User} from "@/models/user";

export default class UserDetailAdaptor extends Adaptor {
    constructor() {
        super("http://localhost:8086/api/users");
    }

    async asyncFindAll() {
        return (await this.fetchJson(this.resourceUrl + "/all"))
            .map(user => Object.assign(new User(), user));
    }

    // async asyncDeleteById(id) {
    //     return Promise.resolve(undefined);
    // }

    // async asyncFindById(id) {
    //     return Object.assign(new User(), await this.fetchJson(this.resourceUrl + id));
    // }

    async asyncSave(user) {
        const options = {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(user),
        };

        let response = await this.fetchJson(this.resourceUrl, options);

        if (response.status === 409) {
            options.method = "PUT";
            response = await this.fetchJson(this.resourceUrl + user.id, options);
        }

        if (response.ok) {
            return Object.assign(new User(), await response.json());
        } else {
            console.log(response, !response.bodyUsed ? await response.text() : "");
            return null;
        }
    }
}