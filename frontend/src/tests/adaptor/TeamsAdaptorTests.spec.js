import TeamsAdaptor from "@/services/TeamsAdaptor";
import '@jest/globals'; // Import Jest's global functions and matchers
/**
 * Tests for teams adaptor
 *
 * @author Joey van der Poel
 */
describe('TeamsAdaptor', () => {
    let teamsAdaptor;
    let resourceUrl = 'http://localhost:8087/api/teams';

    beforeEach(() => {
        // Assuming a mock URL for testing purposes
        teamsAdaptor = new TeamsAdaptor(resourceUrl);
        jest.spyOn(teamsAdaptor, 'fetchJson').mockResolvedValue({});
    });

    describe('asyncFindAll', () => {
        it('should fetch all teams from the API', async () => {
            const teams = await teamsAdaptor.asyncFindAll();
            expect(teams).toEqual([]);
            expect(teamsAdaptor.fetchJson).toHaveBeenCalledWith(resourceUrl, expect.any(Object));
        });
    });

    describe('asyncFindById', () => {
        it('should fetch a team by ID from the API', async () => {
            const teamId = '123';
            await teamsAdaptor.asyncFindById(teamId);

            expect(teamsAdaptor.fetchJson).toHaveBeenCalledWith(
                `http://localhost:8087/api/teams/${teamId}`,
                expect.any(Object)
            );
        });

        it('should return an empty object if team is not found', async () => {
            const teamId = '123';
            const team = await teamsAdaptor.asyncFindById(teamId);

            expect(team).toEqual({});
        });
    });

    describe('asyncFindAllByWarehouseId', () => {
        it('should fetch teams by warehouse ID from the API', async () => {
            const warehouseId = '456';
            const teams = await teamsAdaptor.asyncFindAllByWarehouseId(warehouseId);
            expect(teams).toEqual([]);
            expect(teamsAdaptor.fetchJson).toHaveBeenCalledWith(`${resourceUrl}/warehouse/${warehouseId}`, expect.any(Object));
        });
    });

    describe('asyncAdd', () => {
        it('should add a team via the API', async () => {
            const teamToAdd = { name: 'Test Team', members: 5 };
            const addedTeam = await teamsAdaptor.asyncAdd(teamToAdd);
            expect(addedTeam).toEqual({});
            expect(teamsAdaptor.fetchJson).toHaveBeenCalledWith(resourceUrl, expect.objectContaining({
                method: 'POST',
                body: JSON.stringify(teamToAdd),
            }));
        });
    });

    describe('asyncUpdate', () => {
        it('should update a team via the API', async () => {
            const teamIdToUpdate = '789';
            const updatedTeamData = { name: 'Updated Team', members: 8 };
            const updatedTeam = await teamsAdaptor.asyncUpdate(teamIdToUpdate, updatedTeamData);
            expect(updatedTeam).toEqual({});
            expect(teamsAdaptor.fetchJson).toHaveBeenCalledWith(`${resourceUrl}/${teamIdToUpdate}`, expect.objectContaining({
                method: 'PUT',
                body: JSON.stringify(updatedTeamData),
            }));
        });
    });

    describe('asyncDeleteById', () => {
        it('should delete a team by ID via the API', async () => {
            const teamIdToDelete = '321';
            const deletedTeam = await teamsAdaptor.asyncDeleteById(teamIdToDelete);
            expect(deletedTeam).toEqual({});
            expect(teamsAdaptor.fetchJson).toHaveBeenCalledWith(`${resourceUrl}/${teamIdToDelete}`, expect.objectContaining({
                method: 'DELETE',
            }));
        });
    });
});
