import ProjectAdaptor from "@/services/ProjectAdaptor";
import { Project } from "@/models/Project";
import '@jest/globals';

/**
 * Tests for project adaptor
 *  @Author Nizar Amine
 */

describe('ProjectAdaptor', () => {
    let projectAdaptor;
    let resourceUrl = 'http://localhost:8087/api/projects';

    beforeEach(() => {
        projectAdaptor = new ProjectAdaptor(resourceUrl);
        jest.spyOn(projectAdaptor, 'fetchJson').mockResolvedValue({});
    });

    test('should create an instance of ProjectAdaptor', () => {
        expect(projectAdaptor).toBeInstanceOf(ProjectAdaptor);
    });

    describe('asyncFindAll', () => {
        test('should fetch all projects successfully', async () => {
            const mockProjects = [{ id: 1, name: 'Project 1' }, { id: 2, name: 'Project 2' }];
            jest.spyOn(projectAdaptor, 'fetchJson').mockResolvedValue(mockProjects);

            const result = await projectAdaptor.asyncFindAll();

            expect(result).toEqual(mockProjects.map(project => Project.fromJson(project)));
        });

        test('should return null if no projects found', async () => {
            jest.spyOn(projectAdaptor, 'fetchJson').mockResolvedValue(null);

            const result = await projectAdaptor.asyncFindAll();

            expect(result).toEqual(null);

        });
    });

    describe('asyncFindById', () => {
        test('should fetch a project by ID successfully', async () => {
            const projectId = '1';
            const mockProject = { id: 1, name: 'Project 1' };
            jest.spyOn(projectAdaptor, 'fetchJson').mockResolvedValue(mockProject);

            const result = await projectAdaptor.asyncFindById(projectId);

            expect(result).toEqual(Project.fromJson(mockProject));
        });

        test('should return null if project not found by ID', async () => {
            const projectId = '999'; // Assuming ID not found
            jest.spyOn(projectAdaptor, 'fetchJson').mockResolvedValue(null);

            const result = await projectAdaptor.asyncFindById(projectId);

            expect(result).toEqual(null);
        });
    });

    describe('asyncAdd', () => {
        test('should add a project successfully', async () => {
            const newProject = { name: 'New Project' };
            const mockAddedProject = { id: 3, name: 'New Project' };
            jest.spyOn(projectAdaptor, 'fetchJson').mockResolvedValue(mockAddedProject);

            const result = await projectAdaptor.asyncAdd(newProject);

            expect(result).toEqual(Project.fromJson(mockAddedProject));
        });

        test('should return null if adding project fails', async () => {
            const newProject = { name: 'New Project' };
            jest.spyOn(projectAdaptor, 'fetchJson').mockResolvedValue(null);

            const result = await projectAdaptor.asyncAdd(newProject);

            expect(result).toEqual(null);
        });
    });

    describe('asyncUpdate', () => {
        test('should update a project successfully', async () => {
            const projectId = '1';
            const updatedProjectData = { name: 'Updated Project' };
            const mockUpdatedProject = { id: 1, name: 'Updated Project' };
            jest.spyOn(projectAdaptor, 'fetchJson').mockResolvedValue(mockUpdatedProject);

            const result = await projectAdaptor.asyncUpdate(projectId, updatedProjectData);

            expect(result).toEqual(Project.fromJson(mockUpdatedProject));
        });

        test('should return null if updating project fails', async () => {
            const projectId = '999'; // Assuming ID not found
            const updatedProjectData = { name: 'Updated Project' };
            jest.spyOn(projectAdaptor, 'fetchJson').mockResolvedValue(null);

            const result = await projectAdaptor.asyncUpdate(projectId, updatedProjectData);

            expect(result).toEqual(null);
        });
    });

    describe('asyncDeleteById', () => {
        test('should delete a project by ID successfully', async () => {
            const projectId = '1';
            const mockDeletedProject = { id: 1, name: 'Deleted Project' };
            jest.spyOn(projectAdaptor, 'fetchJson').mockResolvedValue(mockDeletedProject);

            const result = await projectAdaptor.asyncDeleteById(projectId);

            expect(result).toEqual(Project.fromJson(mockDeletedProject));
        });

        test('should return null if deleting project fails', async () => {
            const projectId = '999'; // Assuming ID not found
            jest.spyOn(projectAdaptor, 'fetchJson').mockResolvedValue(null);

            const result = await projectAdaptor.asyncDeleteById(projectId);

            expect(result).toEqual(null);
        });
    });
});
