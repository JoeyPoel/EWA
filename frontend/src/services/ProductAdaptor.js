import {Adaptor} from "./Adaptor.js";
import {Product} from "@/models/Product";
import {ProductCategory} from "@/models/ProductCategory";

/**
 * Adaptor for the product REST API.
 *
 * @extends Adaptor
 * @category Services
 *
 * @Author Joey van der Poel
 * @Author Junior Javier Brito Perez
 */
export default class ProductAdaptor extends Adaptor {
    constructor(URL) {
        super(URL);
    }

    /**
     * Retrieves all product asynchronously.
     *
     * @async
     * @returns {Promise<Array<Product> | null>} - An array of Product objects if successful, or null if there wa an
     * error.
     */
    async asyncFindAll() {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(this.resourceUrl, options);

        return response ? response.map(product => Product.fromJson(product)) : null;
    }

    async asyncFindAllActive() {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(this.resourceUrl + "/active", options);

        return response ? response.map(product => Product.fromJson(product)) : null;
    }

    /**
     * Adds a product asynchronously.
     *
     * @param {Object} product - The product object to add.
     * @returns {Promise} - A promise that resolves with the added product, or null if unsuccessful.
     */
    async asyncAdd(product) {
        const options = {
            method: "POST", headers: {"Content-Type": "application/json"}, body: JSON.stringify(product)
        }

        let response = await this.fetchJson(this.resourceUrl, options);

        if (response) {
            return Product.fromJson(response);
        }
        return null;
    }

    /**
     * Retrieves a product from the server by its ID.
     *
     * @param {string} id - The ID of the product to retrieve.
     * @returns {Promise<Product|null>} - A promise that resolves with the retrieved product or null if not found.
     */
    async asyncGetById(id) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(this.resourceUrl + "/" + id, options);

        return response ? Product.fromJson(response) : null;
    }

    /**
     * Updates a product with the given ID asynchronously.
     *
     * @param {string} id - The ID of the product to update.
     * @param {Object} product - The updated product object.
     * @return {Promise<Product|null>} - A promise that resolves to the updated product,
     * or null if the update was unsuccessful.
     */
    async asyncUpdateById(id, product) {
        const options = {
            method: "PUT", headers: {"Content-Type": "application/json"}, body: JSON.stringify(product)
        }

        let response = await this.fetchJson(this.resourceUrl + "/" + id, options);

        return response ? Product.fromJson(response) : null;
    }

    /**
     * Deletes a product by its ID asynchronously.
     *
     * @param {string} id - The ID of the product to be deleted.
     * @returns {Promise<Product|null>} A promise that resolves to the deleted product. Returns null if the product
     * does not exist.
     */
    async asyncDeleteById(id) {
        const options = {
            method: "DELETE", headers: {"Content-Type": "application/json"},
        }
        const response = await this.fetchJson(this.resourceUrl + "/" + id, options);

        return response ? Product.fromJson(response) : null;
    }

    /**
     * Retrieves all categories asynchronously.
     *
     * @returns {Promise<ProductCategory[]>} - A promise that resolves to an array of product categories.
     */
    async asyncFindAllCategories() {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(this.resourceUrl + "/categories", options);
        if (response) {
            return response.map(productCategory => Object.assign(new ProductCategory(), productCategory));
        }
    }
}
