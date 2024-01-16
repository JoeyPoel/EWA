import UserAdaptor from "@/services/UserAdaptor";
import '@jest/globals';

/**
 * Tests for user adaptor
 *
 * @Author Johnny Magielse
 */
describe('UserAdaptor', () => {
    let userAdaptor;
    let resourceUrl = 'http://localhost:8087/api/users';

    beforeEach(() => {
        userAdaptor = new UserAdaptor(resourceUrl);
        jest.spyOn(userAdaptor, 'fetchJson').mockResolvedValue({});
    });

    // johnny
    describe('asyncFindUser', () => {
        it('should fetch a user from the API', async () => {
            const user = { username: 'test', password: 'password' };
            await userAdaptor.asyncFindUser(user);
            expect(userAdaptor.fetchJson).toHaveBeenCalledWith(`${resourceUrl}/login`, expect.objectContaining({
                method: 'POST',
                body: JSON.stringify(user),
            }));
        });
    });

    // johnny
    describe('asyncResetPassword', () => {
        it('should reset a user password via the API', async () => {
            const id = '123';
            const password = 'newPassword';
            await userAdaptor.asyncResetPassword(password, id);
            expect(userAdaptor.fetchJson).toHaveBeenCalledWith(`${resourceUrl}/passReset`, expect.objectContaining({
                method: 'PUT',
                body: JSON.stringify({ password, id }),
            }));
        });
    });
});