/**
 * Abstract adaptor class for fetching data from a REST API.
 *
 * @class
 * @abstract
 * @param {string} resourceUrl - The base URL of the REST API.
 * @author Junior Javier Brito Perez
 */
export class Adaptor {
    /**
     * @constructor
     * @param {string} resourceUrl - The base URL of the REST API.
     */
    constructor(resourceUrl) {
        this.resourceUrl = resourceUrl;
        console.log("Created Adaptor for " + resourceUrl);
    }

    /**
     * Fetches JSON data from a given URL.
     *
     * @async
     * @param {string} url - The URL to fetch data from.
     * @param {Object} [options=null] - The options to pass to the fetch function.
     * @returns {Promise<Object>} The JSON data from the response.
     */
    async fetchJson(url, options = null) {
        let response = await fetch(url, options);
        if (response.ok) {
            return await response.json();
        } else {
            console.log(response, !response.bodyUsed ? await response.text() : "");
            return null;
        }
    }

    /**
     * Abstract method to find all resources.
     *
     * @async
     * @abstract
     * @throws {Error} When not implemented in a subclass.
     */
    async asyncFindAll() {
        throw new Error("Not implemented");
    }

    /**
     * Abstract method to find a resource by its ID.
     *
     * @async
     * @abstract
     * @param {string} id - The ID of the resource.
     * @throws {Error} When not implemented in a subclass.
     */
    async asyncFindById(id) {
        throw new Error("Not implemented" + id);
    }

    /**
     * Abstract method to save a resource.
     *
     * @async
     * @abstract
     * @param object - The resource to save.
     * @throws {Error} When not implemented in a subclass.
     */
    async asyncSave(object) {
        throw new Error("Not implemented" + object);
    }

    /**
     * Abstract method to delete a resource by its ID.
     *
     * @async
     * @abstract
     * @param {string} id - The ID of the resource.
     * @throws {Error} When not implemented in a subclass.
     */
    async asyncDeleteById(id) {
        throw new Error("Not implemented" + id);
    }
}
