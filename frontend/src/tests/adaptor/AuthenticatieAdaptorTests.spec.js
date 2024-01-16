import AuthenthicationAdaptor from "@/services/AuthenthicationAdaptor";
import { User } from "@/models/User";

jest.mock('@/services/FetchInterceptor', () => {
    return {
        FetchInterceptor: jest.fn().mockImplementation(() => ({
            intercept: jest.fn(),
        })),
    };
});

describe('AuthenthicationAdaptor', () => {
    let adaptor;
    let mockRouter;

    beforeEach(() => {
        mockRouter = {
            push: jest.fn(),
        };

        adaptor = new AuthenthicationAdaptor('https://localhost.com', mockRouter);
    });

    test('asyncLogin sends a POST request and returns a User object on success', async () => {
        const mockUser = { username: 'testuser', password: 'password123' };
        const mockResponse = { id: 1, username: 'testuser', email: 'test@example.com' };

        adaptor.fetchJson = jest.fn().mockResolvedValue(mockResponse);

        const result = await adaptor.asyncLogin(mockUser);

        expect(adaptor.fetchJson).toHaveBeenCalledWith(adaptor.resourceUrl + '/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(mockUser),
        });

        expect(result).toBeInstanceOf(User);
        expect(result.id).toBe(mockResponse.id);
        expect(result.username).toBe(mockResponse.username);
        expect(result.email).toBe(mockResponse.email);
    });

    test('asyncLogin returns null on failed login', async () => {
        const mockUser = { username: 'testuser', password: 'wrongpassword' };

        adaptor.fetchJson = jest.fn().mockResolvedValue(null);

        const result = await adaptor.asyncLogin(mockUser);

        expect(adaptor.fetchJson).toHaveBeenCalledWith(adaptor.resourceUrl + '/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(mockUser),
        });

        expect(result).toBeNull();
    });
});
