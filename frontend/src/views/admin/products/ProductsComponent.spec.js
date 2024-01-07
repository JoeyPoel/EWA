import { mount } from '@vue/test-utils';
import { describe, test, expect, jest } from '@jest/globals';
import ProductsComponent from './ProductsComponent.vue';
import ProductAdaptor from "@/services/ProductAdaptor";
import { reactive } from "vue";

let wrapper;

beforeEach(async function() {
    let theProductsService = new ProductAdaptor();
    let theInventoryService = new ProductAdaptor();

    // Mock the methods of ProductAdaptor
    theProductsService.asyncFindAll = jest.fn().mockResolvedValue([]);
    theProductsService.asyncFindAllActive = jest.fn().mockResolvedValue([]);
    theProductsService.asyncAdd = jest.fn().mockResolvedValue(null);
    theProductsService.asyncGetById = jest.fn().mockResolvedValue(null);
    theProductsService.asyncUpdateById = jest.fn().mockResolvedValue(null);
    theProductsService.asyncDeleteById = jest.fn().mockResolvedValue(null);
    theProductsService.asyncFindAllCategories = jest.fn().mockResolvedValue([]);

    wrapper = await mount(ProductsComponent, {
        global: {
            provide: {
                "productsService": reactive(theProductsService),
                "inventoryService": reactive(theInventoryService),
            }
        }
    });
});

describe('ProductsComponent', () => {
    test('is a Vue instance', () => {
        expect(wrapper.vm).toBeTruthy();
    });
});