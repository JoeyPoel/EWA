import { mount } from '@vue/test-utils';
import { reactive } from 'vue';
import OrderEntityTable from "@/components/order/OrderEntityTable.vue";

describe('OrderEntityTable', () => {
    let wrapper;

    beforeEach(async () => {
        const mockOrderService = {
            asyncFindAll: jest.fn().mockResolvedValue([]),
            asyncFindAllByWarehouseId: jest.fn().mockResolvedValue([]),
            asyncDeleteById: jest.fn().mockResolvedValue(null)
        };

        const

        wrapper = mount(OrderEntityTable, {
            global: {
                provide: {
                    orderService: reactive(mockOrderService)
                },
            },
        });

        await wrapper.vm.$nextTick();
    });

    test('renders with initial data', () => {
        expect(wrapper.exists()).toBe(true);
        expect(wrapper.vm.tableConfig.items).toEqual([]);
    });

    test('fetches orders on mounted', async () => {
        wrapper.vm.selectedWarehouseId = null;
        await wrapper.vm.$nextTick();
        expect(wrapper.vm.orderService.asyncFindAll).toHaveBeenCalled();
    });

    test('fetches orders by warehouse id on warehouse change', async () => {
        wrapper.vm.selectedWarehouseId = 1;
        await wrapper.vm.$nextTick();
        expect(wrapper.vm.orderService.asyncFindAllByWarehouseId).toHaveBeenCalledWith(1);
    });

    test('calls asyncDeleteById when handleDelete is called with an item', async () => {
        const itemToDelete = { id: 1, warehouseId: 1, orderDate: '2024-01-01', deliveryDate: '2024-01-02' };
        await wrapper.vm.handleDelete(itemToDelete);
        expect(wrapper.vm.orderService.asyncDeleteById).toHaveBeenCalledWith(itemToDelete.id);
    });
});