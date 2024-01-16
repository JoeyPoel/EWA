import ProductAdaptor from "@/services/ProductAdaptor";
import { Product } from "@/models/Product";
import '@jest/globals';
import {ProductCategory} from "@/models/ProductCategory";

/**
 * Tests for Product adaptor
 *  @Author Jayden Gunhan
 */

describe('ProductAdaptor', () => {
    let productAdaptor;
    let resourceUrl = 'http://localhost:8080/api/products';

    beforeEach(() => {
        productAdaptor = new ProductAdaptor(resourceUrl);
        jest.spyOn(productAdaptor, 'fetchJson').mockResolvedValue({});
    });

    test('should create an instance of ProductAdaptor', () => {
        expect(productAdaptor).toBeInstanceOf(ProductAdaptor);
    });

    describe('asyncFindAll', () => {
        test('should fetch all products successfully', async () => {
            const mockProducts = [
                { id: 1, name: 'Product 1', description: 'Desc 1', price: 10, categoryId: 1 },
                { id: 2, name: 'Product 2', description: 'Desc 2', price: 20, categoryId: 2 }
            ];
            jest.spyOn(productAdaptor, 'fetchJson').mockResolvedValue(mockProducts);

            const result = await productAdaptor.asyncFindAll();

            expect(result).toEqual(mockProducts.map(product => Product.fromJson(product)));
        });

        test('should return null if no products found', async () => {
            jest.spyOn(productAdaptor, 'fetchJson').mockResolvedValue(null);

            const result = await productAdaptor.asyncFindAll();

            expect(result).toBeNull();
        });
    });

    describe('asyncFindAllActive', () => {
        test('should fetch all active products successfully', async () => {
            const mockActiveProducts = [
                { id: 1, name: 'Active Product 1', description: 'Desc 1', price: 10, categoryId: 1 },
            ];
            jest.spyOn(productAdaptor, 'fetchJson').mockResolvedValue(mockActiveProducts);

            const result = await productAdaptor.asyncFindAllActive();

            expect(result).toEqual(mockActiveProducts.map(product => Product.fromJson(product)));
        });

        test('should return null if no active products found', async () => {
            jest.spyOn(productAdaptor, 'fetchJson').mockResolvedValue(null);

            const result = await productAdaptor.asyncFindAllActive();

            expect(result).toBeNull();
        });
    });

    describe('asyncGetById', () => {
        test('should fetch a product by ID successfully', async () => {
            const mockProduct = { id: 1, name: 'Product 1', description: 'Desc 1', price: 10, categoryId: 1 };
            jest.spyOn(productAdaptor, 'fetchJson').mockResolvedValue(mockProduct);

            const result = await productAdaptor.asyncGetById(1);

            expect(result).toEqual(Product.fromJson(mockProduct));
        });

        test('should return null if product not found by ID', async () => {
            jest.spyOn(productAdaptor, 'fetchJson').mockResolvedValue(null);

            const result = await productAdaptor.asyncGetById(999);

            expect(result).toBeNull();
        });
    });

    describe('asyncAdd', () => {
        test('should add a product successfully', async () => {
            const newProduct = { name: 'New Product', description: 'New Desc', price: 30, categoryId: 3 };
            const mockAddedProduct = { id: 3, ...newProduct };
            jest.spyOn(productAdaptor, 'fetchJson').mockResolvedValue(mockAddedProduct);

            const result = await productAdaptor.asyncAdd(newProduct);

            expect(result).toEqual(Product.fromJson(mockAddedProduct));
        });

        test('should return null if adding product fails', async () => {
            const newProduct = { name: 'New Product', description: 'New Desc', price: 30, categoryId: 3 };
            jest.spyOn(productAdaptor, 'fetchJson').mockResolvedValue(null);

            const result = await productAdaptor.asyncAdd(newProduct);

            expect(result).toBeNull();
        });
    });

    describe('asyncUpdateById', () => {
        test('should update a product successfully', async () => {
            const updatedProduct = { id: 1, name: 'Updated Product', description: 'Updated Desc', price: 20, categoryId: 1 };
            jest.spyOn(productAdaptor, 'fetchJson').mockResolvedValue(updatedProduct);

            const result = await productAdaptor.asyncUpdateById(1, updatedProduct);

            expect(result).toEqual(Product.fromJson(updatedProduct));
        });

        test('should return null if updating product fails', async () => {
            jest.spyOn(productAdaptor, 'fetchJson').mockResolvedValue(null);

            const result = await productAdaptor.asyncUpdateById(1, {});

            expect(result).toBeNull();
        });
    });

    describe('asyncDeleteById', () => {
        test('should delete a product by ID successfully', async () => {
            const mockDeletedProduct = { id: 1, name: 'Deleted Product', description: 'Desc 1', price: 10, categoryId: 1 };
            jest.spyOn(productAdaptor, 'fetchJson').mockResolvedValue(mockDeletedProduct);

            const result = await productAdaptor.asyncDeleteById(1);

            expect(result).toEqual(Product.fromJson(mockDeletedProduct));
        });

        test('should return null if deleting product fails', async () => {
            jest.spyOn(productAdaptor, 'fetchJson').mockResolvedValue(null);

            const result = await productAdaptor.asyncDeleteById(999);

            expect(result).toBeNull();
        });
    });

    describe('asyncFindAllCategories', () => {
        test('should fetch all categories successfully', async () => {
            const mockCategories = [
                { id: 1, name: 'Category 1' },
                { id: 2, name: 'Category 2' }
            ];
            jest.spyOn(productAdaptor, 'fetchJson').mockResolvedValue(mockCategories);

            const result = await productAdaptor.asyncFindAllCategories();

            expect(result).toEqual(mockCategories.map(category => Object.assign(new ProductCategory(), category)));
        });
    });
});
