import {User} from "@/models/User";
import {Adaptor} from "@/services/Adaptor";
import {FetchInterceptor} from "@/services/FetchInterceptor";

export default class AuthenticationAdaptor extends Adaptor {
    constructor(URL, router) {
        super(URL);
        this.router = router;
        this.fetchInterceptor = new FetchInterceptor(this.router);
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
