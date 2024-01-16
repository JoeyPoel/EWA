import { mount } from '@vue/test-utils';
import { reactive } from 'vue';
import WarehouseEntityTable from "@/components/warehouses/WarehouseEntityTable.vue";

describe('WarehouseEntityTable', () => {
    let wrapper;

    beforeEach(async () => {
        const mockWarehousesService = {
            asyncFindAll: jest.fn().mockResolvedValue([]),
            asyncAdd: jest.fn().mockResolvedValue(null),
            asyncUpdate: jest.fn().mockResolvedValue(null),
            asyncDeleteById: jest.fn().mockResolvedValue(null)
        };

        wrapper = mount(WarehouseEntityTable, {
            global: {
                provide: {
                    warehousesService: reactive(mockWarehousesService)
                },
            },
        });

        await wrapper.vm.$nextTick();
    });

    test('renders with initial data', () => {
        expect(wrapper.exists()).toBe(true);
        expect(wrapper.vm.warehouses).toEqual([]);
    });

    test('fetches warehouses on mounted', async () => {
        await wrapper.vm.$nextTick();
        expect(wrapper.vm.warehousesService.asyncFindAll).toHaveBeenCalled();
    });

    test('calls asyncUpdate when handleSave is called with an existing item', async () => {
        const existingItem = { id: 1, name: 'Existing Warehouse' };
        await wrapper.vm.handleSave(existingItem);
        expect(wrapper.vm.warehousesService.asyncUpdate).toHaveBeenCalledWith(existingItem.id, existingItem);
    });

    test('calls asyncAdd when handleSave is called with a new item', async () => {
        const newItem = { name: 'New Warehouse' };
        await wrapper.vm.handleSave(newItem);
        expect(wrapper.vm.warehousesService.asyncAdd).toHaveBeenCalledWith(newItem);
    });

    test('calls asyncDeleteById when handleDelete is called with an item', async () => {
        const itemToDelete = { id: 1, name: 'Warehouse to delete' };
        await wrapper.vm.handleDelete(itemToDelete);
        expect(wrapper.vm.warehousesService.asyncDeleteById).toHaveBeenCalledWith(itemToDelete.id);
    });
});