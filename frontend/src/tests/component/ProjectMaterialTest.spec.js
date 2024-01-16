import { mount } from '@vue/test-utils';
import ProjectMaterialsTable from "@/components/project/ProjectMaterialsTable.vue";

/**
 * Tests for Project Materials Table
 * @Author Nizar Amine
 */
describe('ProjectMaterialsTable', () => {
  let wrapper;

  beforeEach(async () => {
    const mockProjectsService = {
      asyncFindProjectProducts: jest.fn().mockResolvedValue([
        { id: 1, productName: 'Product 1', quantity: 5, warehouseName: 'Warehouse A' },
        { id: 2, productName: 'Product 2', quantity: 8, warehouseName: 'Warehouse B' },
      ]),
    };

    wrapper = mount(ProjectMaterialsTable, {
      global: {
        provide: {
          projectsService: mockProjectsService,
        },
      },
      props: {
        item: { id: 1, title: 'Project 1', quantity: 1 },
      },
    });

    await wrapper.vm.$nextTick();
  });

  test('renders with initial data', () => {
    expect(wrapper.exists()).toBe(true);
    expect(wrapper.vm.projectProducts).toEqual([
      { id: 1, productName: 'Product 1', quantity: 5, warehouseName: 'Warehouse A' },
      { id: 2, productName: 'Product 2', quantity: 8, warehouseName: 'Warehouse B' },
    ]);
  });

  test('fetches project products on mounted', async () => {
    await wrapper.vm.$nextTick();

    // Make assertions
    expect(wrapper.vm.projectsService.asyncFindProjectProducts).toHaveBeenCalledWith(wrapper.vm.item.id);
    expect(wrapper.vm.projectProducts).toEqual([
      { id: 1, productName: 'Product 1', quantity: 5, warehouseName: 'Warehouse A' },
      { id: 2, productName: 'Product 2', quantity: 8, warehouseName: 'Warehouse B' },
    ]);
  });
});
