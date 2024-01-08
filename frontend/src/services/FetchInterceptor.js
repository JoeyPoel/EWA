import fetchIntercept from 'fetch-intercept';

export class FetchInterceptor {
    static theInstance;
    unregister;
    router;

    constructor(router) {
        this.router = router;
        FetchInterceptor.theInstance = this;
        this.unregister = fetchIntercept.register({
            request: (url, config) => [url, config],
            requestError: (error) => Promise.reject(error),
            response: async (response) => {
                if (response.status >= 400 && response.status < 600) {
                    console.log("FetchInterceptor response: ", response);
                    const clone = response.clone(); // Clone the response
                    const data = await clone.text(); // Get the response data as text from the cloned response
                    const regex = /interpolatedMessage='(.*?)'/g;
                    let match;
                    let messages = [];
                    while ((match = regex.exec(data)) !== null) {
                        messages.push(match[1]); // Add the interpolatedMessage to the messages array
                    }
                    // Show the error messages in a popup
                    this.router.app.error.message = messages.join('\n');
                    this.router.app.error.isOpen = true;
                }
                return response;
            },
            responseError: (error) => {
                console.log("FetchInterceptor responseError: ", error);
                // Show the error message in a popup
                this.router.app.error.message = error.message;
                this.router.app.error.isOpen = true;
                return Promise.reject(error);
            }
        });
        console.log("FetchInterceptor has been registered.");
    }
}