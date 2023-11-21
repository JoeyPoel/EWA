import {Adaptor} from "@/services/Adaptor";
import {Project} from "@/models/Project";

export default class ProjectAdaptor extends Adaptor {
    constructor(URL) {
        super(URL);
    }

    async asyncFindAll() {
        const response = await this.fetchJson(this.resourceUrl + "/getAllProjects");
        if (response) {
            return response.map(project => Object.assign(new Project(), project));
        }
        return null;
    }

    async asyncFindById(id) {
        return Object.assign(new Project(), await this.fetchJson(this.resourceUrl + "/getProjectById/" + id));
    }

    async asyncAddProject(project) {
        const options = {
            method: "POST", headers: {"Content-Type": "application/json"}, body: JSON.stringify(project)
        }

        const response = await this.fetchJson(this.resourceUrl + "/addProject", options);

        if (response) {
            return Object.assign(new Project(), await response.json());
        }
        return null;
    }

    async asyncUpdateProject(id, project) {
        const options = {
            method: "PUT", headers: {"Content-Type": "application/json"}, body: JSON.stringify(project)
        }

        const response = await this.fetchJson(this.resourceUrl + "/updateProjectById/" + id, options);

        if (response) {
            return Object.assign(new Project(), await response.json());
        }
        return null;
    }
}