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

    async asyncSave(object) {
        console.log(object);
        return null;
    }
}