import {User} from "@/models/User";
import {Adaptor} from "@/services/Adaptor";

export default class AuthenthicationAdaptor extends Adaptor {
    constructor(URL) {
        super(URL);
    }

    async asyncLogin(user) {
        const options = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(user),
        };

        const response = await this.fetchJson(this.resourceUrl + "/login", options);

        return response ? User.fromJson(response) : null;
    }
}
