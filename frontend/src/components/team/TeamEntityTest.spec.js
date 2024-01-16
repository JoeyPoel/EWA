import { mount } from '@vue/test-utils';
import { reactive } from 'vue';
import TeamEntityTable from "@/components/team/TeamEntityTable.vue";

/**
 * Tests for Team Entity Table
 *
 * @author Joey van der Poel
 */
describe('TeamEntityTable', () => {
  let wrapper;

  beforeEach(async () => {
    const teamsService = {
      asyncFindAllByWarehouseId: jest.fn().mockResolvedValue([]),
      asyncFindAll: jest.fn().mockResolvedValue([]),
      asyncAdd: jest.fn().mockResolvedValue(null),
      asyncUpdate: jest.fn().mockResolvedValue(null),
      asyncDeleteById: jest.fn().mockResolvedValue(null),
    };

    const warehousesService = {
      asyncFindAll: jest.fn().mockResolvedValue([]),
    };

    const usersService = {
      asyncFindAll: jest.fn().mockResolvedValue([]),
    };

    wrapper = mount(TeamEntityTable, {
      global: {
        provide: {
          teamsService: reactive(teamsService),
          warehousesService: reactive(warehousesService),
          usersService: reactive(usersService),
        },
      },
    });

    await wrapper.vm.$nextTick();
  });

  test('renders with initial data', () => {
    expect(wrapper.exists()).toBe(true);
    expect(wrapper.vm.teams).toEqual([]);
    expect(wrapper.vm.warehouses).toEqual([]);
    expect(wrapper.vm.users).toEqual([]);
  });

  test('fetches teams when selectedWarehouse changes', async () => {
    wrapper.vm.selectedWarehouse = 'someWarehouseId';
    await wrapper.vm.$nextTick();
    expect(wrapper.vm.teamsService.asyncFindAllByWarehouseId).toHaveBeenCalledWith('someWarehouseId');
  });

  test('calls asyncAdd when handleSave is called with a new item', async () => {
    const newItem = { name: 'New Team', warehouseId: 'someWarehouseId' };
    await wrapper.vm.handleSave(newItem);
    expect(wrapper.vm.teamsService.asyncAdd).toHaveBeenCalledWith(newItem);
  });

  test('calls asyncUpdate when handleSave is called with an existing item', async () => {
    const existingItem = { id: 1, name: 'Existing Team', warehouseId: 'someWarehouseId' };
    await wrapper.vm.handleSave(existingItem);
    expect(wrapper.vm.teamsService.asyncUpdate).toHaveBeenCalledWith(existingItem.id, existingItem);
  });

  test('calls asyncDeleteById when handleDelete is called with an item', async () => {
    const itemToDelete = { id: 1, name: 'Team to delete', warehouseId: 'someWarehouseId' };
    await wrapper.vm.handleDelete(itemToDelete);
    expect(wrapper.vm.teamsService.asyncDeleteById).toHaveBeenCalledWith(itemToDelete.id);
  });
});
