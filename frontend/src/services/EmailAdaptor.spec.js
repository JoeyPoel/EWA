import EmailAdaptor from "@/services/EmailAdaptor";
import '@jest/globals';
/**
 * Tests for email adaptor
 *
 * @author Johnny Magielse
 */
describe('EmailAdaptor', () => {
    let emailAdaptor;
    let resourceUrl = 'http://localhost:8087/api/mail';

    beforeEach(() => {
        emailAdaptor = new EmailAdaptor(resourceUrl);
        jest.spyOn(emailAdaptor, 'fetchJson').mockResolvedValue({});
    });

    // johnny
    it('should handle successful response', async () => {
        const email = 'test@example.com';
        const mockResponse = { message: 'Email sent successfully' };
        emailAdaptor.fetchJson.mockResolvedValue(mockResponse);

        const result = await emailAdaptor.sendPassResetEmail(email);

        expect(emailAdaptor.fetchJson).toHaveBeenCalledWith(`${resourceUrl}/sendPassResetEmail`, expect.any(Object));
        expect(result).toEqual(mockResponse);
    });

    // johnny
    it('should handle error response', async () => {
        const email = 'test@example.com';
        const mockError = new Error('Failed to send email');
        emailAdaptor.fetchJson.mockRejectedValue(mockError);

        await expect(emailAdaptor.sendPassResetEmail(email)).rejects.toThrow(mockError);
        expect(emailAdaptor.fetchJson).toHaveBeenCalledWith(`${resourceUrl}/sendPassResetEmail`, expect.any(Object));
    });
});