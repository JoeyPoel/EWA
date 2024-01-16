import { mount } from '@vue/test-utils';
import { reactive } from 'vue';
import ProjectEntityTable from "@/components/project/ProjectEntityTable.vue";

/**
 * Tests for Project Entity Table
 * @Author Nizar Amine
 */
describe('ProjectEntityTable', () => {
  let wrapper;

  beforeEach(async () => {
    const mockProjectsService = {
      asyncFindAll: jest.fn().mockResolvedValue([]),
      asyncUpdate: jest.fn().mockResolvedValue(null),
      asyncAdd: jest.fn().mockResolvedValue(null),
      asyncDeleteById: jest.fn().mockResolvedValue(null),
      initialize: jest.fn(),
    };

    const mockTeamsService = {
      asyncFindAll: jest.fn().mockResolvedValue([]),
    };

    wrapper = mount(ProjectEntityTable, {
      global: {
        provide: {
          projectsService: reactive(mockProjectsService),
          teamsService: reactive(mockTeamsService),
        },
      },
    });

    await wrapper.vm.$nextTick();
  });


  test('renders with initial data', () => {
    expect(wrapper.exists()).toBe(true);
    expect(wrapper.vm.projects).toEqual([]);
    expect(wrapper.vm.teams).toEqual([]);
  });

  test('fetches projects and teams on mounted', async () => {
    await wrapper.vm.$nextTick();
    expect(wrapper.vm.projectsService.asyncFindAll).toHaveBeenCalled();
    expect(wrapper.vm.teamsService.asyncFindAll).toHaveBeenCalled();
  });

  test('calls asyncUpdate when handleSave is called with an existing item', async () => {
    const existingItem = { id: 1, name: 'Existing Project' };
    await wrapper.vm.handleSave(existingItem);
    expect(wrapper.vm.projectsService.asyncUpdate).toHaveBeenCalledWith(existingItem.id, existingItem);
  });

  test('calls asyncAdd when handleSave is called with a new item', async () => {
    const newItem = { name: 'New Project' };
    await wrapper.vm.handleSave(newItem);
    expect(wrapper.vm.projectsService.asyncAdd).toHaveBeenCalledWith(newItem);
  });

  test('calls asyncDeleteById when handleDelete is called with an item', async () => {
    const itemToDelete = { id: 1, name: 'Project to delete' };
    await wrapper.vm.handleDelete(itemToDelete);
    expect(wrapper.vm.projectsService.asyncDeleteById).toHaveBeenCalledWith(itemToDelete.id);
  });
});
