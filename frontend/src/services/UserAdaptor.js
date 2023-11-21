import {Adaptor} from "@/services/Adaptor";
import {User} from "@/models/user";

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

    async asyncDeleteById(id) {
        console.log(id);
        return null;
    }

    async asyncFindAll() {
        return null;
    }

    async asyncFindById(id) {
        console.log(id);
        return null;
    }

    async asyncSave(object) {
        console.log(object);
        return null;
    }
}