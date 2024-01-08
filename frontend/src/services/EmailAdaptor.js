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

        return await this.fetchJson(this.resourceUrl + "/sendPassResetEmail", options);
    }
    async sendPassGenEmail(email, newPassword) {
        const options = {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({"email": email})
        }

        return await this.fetchJson(`${this.resourceUrl}/sendPassGenEmail?generatedPassword=${encodeURIComponent(newPassword)}`, options);
    }
}
