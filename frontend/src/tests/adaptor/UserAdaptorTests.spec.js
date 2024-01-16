import UserAdaptor from "@/services/UserAdaptor";
import '@jest/globals';

/**
 * Tests for user adaptor
 *
 * @Author Johnny Magielse
 * @Author Kaifie Dil.
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

    // Kaifie
    describe('asyncFindAllByTeamId', () => {
        it('should fetch users by team ID from the API', async () => {
            const teamId = '123';
            await userAdaptor.asyncFindAllByTeamId(teamId);
            expect(userAdaptor.fetchJson).toHaveBeenCalledWith(`${resourceUrl}/team/${teamId}`, expect.objectContaining({
                method: 'GET',
            }));
        });
    });

// Kaifie
    describe('asyncFindAllByNoTeam', () => {
        it('should fetch users with no team from the API', async () => {
            await userAdaptor.asyncFindAllByNoTeam();
            expect(userAdaptor.fetchJson).toHaveBeenCalledWith(`${resourceUrl}/noTeam`, expect.objectContaining({
                method: 'GET',
            }));
        });
    });

// Kaifie
    describe('asyncGetUserInfo', () => {
        it('should fetch user information by user ID from the API', async () => {
            const id = '123';
            await userAdaptor.asyncGetUserInfo(id);
            expect(userAdaptor.fetchJson).toHaveBeenCalledWith(`${resourceUrl}${id}`, expect.objectContaining({
                method: 'GET',
            }));
        });
    });
});