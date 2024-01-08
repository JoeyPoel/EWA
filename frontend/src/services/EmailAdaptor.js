import {Adaptor} from "@/services/Adaptor";

export default class EmailAdaptor extends Adaptor {
    constructor(URL) {
        super(URL);
    }

    async sendPassResetEmail(email) {
        const options = {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({"email": email})
        }

        const response = await fetch(this.resourceUrl + "/sendPassResetEmail", options);
        console.log(response)

        return await this.fetchJson(this.resourceUrl + "/sendPassResetEmail", options);
    }
}
