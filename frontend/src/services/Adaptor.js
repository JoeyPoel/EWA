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

        if (response.status === 204) {
            return null; // For no content
        }

        if (response.ok) {
            return await response.json();
        } else {
            console.log(response, !response.bodyUsed ? await response.text() : "");
            return null;
        }
    }

    static transformDataForItemFields(data, fields) {
        let transformedData = {};
        for (let field of fields) {
            let value = data[field.name];
            if (value === undefined) {
                value = field.value;
            }
            transformedData[field.name] = {
                type: field.type,
                value: value,
                disabled: field.disabled || false,
                label: field.label || "",
                rules: field.rules || [],
                items: field.items || [],
            };
        }

        return transformedData;
    }
}
