import {Adaptor} from "./Adaptor.js";
import {Product} from "@/models/product_MERGE_ME";

/**
 * Adaptor for the product REST API.
 *
 * @extends Adaptor
 * @category Services
 * @Author Joey van der Poel
 */
export default class ProductAdaptor extends Adaptor {
    constructor(URL) {
        super(URL);
    }

    /**
     * Fetches all product from the REST API.
     *
     * @async
     * @returns {Promise<*>} The products.
     */
    async asyncFindAll() {
        return (await this.fetchJson(this.resourceUrl + "/all"))
            .map(product => Object.assign(new Product(), product));
    }

    /**
     * Fetches all product names from the REST API.
     *
     * @async
     * @returns {Promise<*>} The products.
     */
    async asyncFindAllTypes() {
        return (await this.fetchJson(this.resourceUrl + "/allTypes"))
            .map(product => Object.assign(new Product(), product));
    }

    /**
     * Fetches a product by its ID from the REST API.
     *
     * @async
     * @param {string} id - The ID of the product.
     * @returns {Promise<*>} The product.
     */
    async asyncFindById(id) {
        return Object.assign(new Product(), await this.fetchJson(this.resourceUrl + id));
    }

    /**
     * Saves a product to the REST API.
     *
     * @async
     * @param {Product} product - The product to save.
     * @returns {Promise<*>} The saved product.
     */
    async asyncSave(product) {
        const options = {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(product)
        }

        let response = await this.fetchJson(this.resourceUrl + "/add", options);

        if (response.status === 409) {
            options.method = "PUT";
            response = await this.fetchJson(this.resourceUrl + product.id, options);
        }

        if (response.ok) {
            return Object.assign(new Product(), await response.json());
        } else {
            console.log(response, !response.bodyUsed ? await response.text() : "");
            return null;
        }
    }

    /**
     * Deletes a product by its ID from the REST API.
     *
     * @async
     * @param {string} id - The ID of the product.
     * @returns {Promise<*>} The deleted product.
     */
    async asyncDeleteById(id) {
        const options = {
            method: "DELETE",
            headers: {"Content-Type": "application/json"},
        }
        const response = await this.fetchJson(this.resourceUrl + id, options);
        if (response.ok) {
            return Object.assign(new Product(), await response.json());
        } else {
            console.log(response, !response.bodyUsed ? await response.text() : "");
            return null;
        }
    }
}
