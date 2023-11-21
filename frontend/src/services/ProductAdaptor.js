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
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(this.resourceUrl + "/getAllProducts", options);
        if (response) {
            return response.map(product => Object.assign(new Product(), product));
        }
    }

    /**
     * Fetches a product by its ID from the REST API.
     *
     * @async
     * @param {string} id - The ID of the product.
     * @returns {Promise<*>} The product.
     */
    async asyncFindById(id) {
        return Object.assign(new Product(), await this.fetchJson(this.resourceUrl + "/getProductById/" + id));
    }

    /**
     * Saves a product to the REST API.
     *
     * @async
     * @param {Product} product - The product to save.
     * @returns {Promise<*>} The saved product.
     */
    async asyncAdd(product) {
        const options = {
            method: "POST", headers: {"Content-Type": "application/json"}, body: JSON.stringify(product)
        }

        let response = await this.fetchJson(this.resourceUrl + "/addProduct", options);

        if (response) {
            return Object.assign(new Product(), response);
        }
        return null;
    }

    async asyncUpdate(id, product) {
        const options = {
            method: "PUT", headers: {"Content-Type": "application/json"}, body: JSON.stringify(product)
        }

        let response = await this.fetchJson(this.resourceUrl + "/updateProduct/" + id, options);

        if (response) {
            return Object.assign(new Product(), response);
        }
        return null;
    }

    /**
     * Deletes a product by its ID from the REST API.
     *
     * @async
     * @param {number} id - The ID of the product.
     * @returns {Promise<*>} The deleted product.
     */
    async asyncDeleteById(id) {
        const options = {
            method: "DELETE", headers: {"Content-Type": "application/json"},
        }
        const response = await this.fetchJson(this.resourceUrl + "/deleteProduct/" + id, options);

        if (response) {
            return Object.assign(new Product(), response);
        }
        return null;
    }
}
