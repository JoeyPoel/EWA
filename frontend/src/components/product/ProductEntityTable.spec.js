import { mount } from '@vue/test-utils';
import { reactive } from 'vue';
import ProductEntityTable from "@/components/product/ProductEntityTable.vue";

/**
 * Tests for Product Entity Table
 * @Author Jayden Gunhan
 */
describe('ProductEntityTable', () => {
    let wrapper;

    beforeEach(async () => {
        const mockProductsService = {
            asyncFindAll: jest.fn().mockResolvedValue([]),
            asyncFindAllCategories: jest.fn().mockResolvedValue([]),
            asyncAdd: jest.fn().mockResolvedValue(null),
            asyncUpdateById: jest.fn().mockResolvedValue(null),
            asyncDeleteById: jest.fn().mockResolvedValue(null)
        };

        wrapper = mount(ProductEntityTable, {
            global: {
                provide: {
                    productsService: reactive(mockProductsService)
                },
            },
        });

        await wrapper.vm.$nextTick();
    });

    test('renders with initial data', () => {
        expect(wrapper.exists()).toBe(true);
        expect(wrapper.vm.products).toEqual([]);
        expect(wrapper.vm.categories).toEqual([]);
    });

    test('fetches products and categories on mounted', async () => {
        await wrapper.vm.$nextTick();
        expect(wrapper.vm.productsService.asyncFindAll).toHaveBeenCalled();
        expect(wrapper.vm.productsService.asyncFindAllCategories).toHaveBeenCalled();
    });

    test('calls asyncUpdateById when handleSave is called with an existing item', async () => {
        const existingItem = { id: 1, name: 'Existing Product', categoryId: 1 };
        await wrapper.vm.handleSave(existingItem);
        expect(wrapper.vm.productsService.asyncUpdateById).toHaveBeenCalledWith(existingItem.id, existingItem);
    });

    test('calls asyncAdd when handleSave is called with a new item', async () => {
        const newItem = { name: 'New Product', categoryId: 1 };
        await wrapper.vm.handleSave(newItem);
        expect(wrapper.vm.productsService.asyncAdd).toHaveBeenCalledWith(newItem);
    });

    test('calls asyncDeleteById when handleDelete is called with an item', async () => {
        const itemToDelete = { id: 1, name: 'Product to delete', categoryId: 1 };
        await wrapper.vm.handleDelete(itemToDelete);
        expect(wrapper.vm.productsService.asyncDeleteById).toHaveBeenCalledWith(itemToDelete.id);
    });
});
