import { mount } from '@vue/test-utils';
import LoginComponent from '@/components/user/LoginComponent.vue';

/**
 * Tests for login component
 *
 * @Author Johnny Magielse
 */
describe('LoginComponent', () => {
    let wrapper;
    let mockAuthService;

    beforeEach(() => {
        mockAuthService = {
            asyncLogin: jest.fn().mockResolvedValue({}),
        };

        wrapper = mount(LoginComponent, {
            global: {
                provide: {
                    authenthicationService: mockAuthService,
                },
            },
        });
    });


    it('calls login method when form is submitted', async () => {
        const loginSpy = jest.spyOn(wrapper.vm, 'login');
        await wrapper.find('form').trigger('submit.prevent');
        expect(loginSpy).toHaveBeenCalled();
    });

    it('sets formIsValid to false when email or password is not provided', async () => {
        wrapper.vm.email = "";
        wrapper.vm.password = "";

        await wrapper.vm.login();

        expect(wrapper.vm.formIsValid).toBe(false);
    });

    it('calls asyncLogin when login is called with valid email and password', async () => {
        const email = 'test@example.com';
        const password = 'password';
        wrapper.vm.email = email;
        wrapper.vm.password = password;

        await wrapper.vm.login();

        expect(mockAuthService.asyncLogin).toHaveBeenCalledWith({ email, password });
    });

});