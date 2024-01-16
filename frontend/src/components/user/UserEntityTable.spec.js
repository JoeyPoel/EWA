import { mount } from '@vue/test-utils';
import { reactive } from 'vue';
import UserEntityTable from '@/components/user/UserEntityTable.vue';

/**
 * Tests for User Entity Table
 *
 * @Author Kaifie Dil.
 */
describe('UserEntityTable', () => {
    let wrapper;
    let mockUsersService;
    let mockTeamsService;
    let mockEmailService;

    beforeEach(async () => {
        mockUsersService = {
            asyncFindAllByTeamId: jest.fn().mockResolvedValue([]),
            asyncFindAll: jest.fn().mockResolvedValue([]),
            asyncSave: jest.fn().mockResolvedValue(null),
            asyncUpdate: jest.fn().mockResolvedValue(null),
            asyncDeleteById: jest.fn().mockResolvedValue(null),
        };

        mockTeamsService = {
            asyncFindAll: jest.fn().mockResolvedValue([]),
        };

        mockEmailService = {
            sendPassGenEmail: jest.fn().mockResolvedValue(null),
        };

        wrapper = mount(UserEntityTable, {
            global: {
                provide: {
                    usersService: reactive(mockUsersService),
                    teamsService: reactive(mockTeamsService),
                    emailService: reactive(mockEmailService),
                },
            },
        });

        await wrapper.vm.$nextTick();
    });

    it('renders with initial data', () => {
        expect(wrapper.exists()).toBe(true);
        expect(wrapper.vm.users).toEqual([]);
        expect(wrapper.vm.teams).toEqual([]);
    });

    it('fetches users when selectedTeam changes', async () => {
        wrapper.vm.selectedTeam = 'someTeamId';
        await wrapper.vm.$nextTick();
        expect(mockUsersService.asyncFindAllByTeamId).toHaveBeenCalledWith('someTeamId');
    });

    it('calls asyncSave when handleSave is called with a new item', async () => {
        const newItem = { name: 'New User', email: 'newuser@example.com', role: 'USER' };
        await wrapper.vm.handleSave(newItem);
        expect(mockUsersService.asyncSave).toHaveBeenCalledWith(newItem);
    });

    it('calls asyncUpdate when handleSave is called with an existing item', async () => {
        const existingItem = { id: 1, name: 'Existing User', email: 'existinguser@example.com', role: 'USER' };
        await wrapper.vm.handleSave(existingItem);
        expect(mockUsersService.asyncUpdate).toHaveBeenCalledWith(existingItem.id, existingItem);
    });

    it('calls asyncDeleteById when handleDelete is called with an item', async () => {
        const itemToDelete = { id: 1, name: 'User to delete', email: 'usertodelete@example.com', role: 'USER' };
        await wrapper.vm.handleDelete(itemToDelete);
        expect(mockUsersService.asyncDeleteById).toHaveBeenCalledWith(itemToDelete.id);
    });
});