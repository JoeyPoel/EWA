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
            response: (response) => {
                if (response.status >= 400 && response.status < 600) {
                    console.log("FetchInterceptor response: ", response);
                    this.router.push({ path: '/error',}); // Redirect to error page
                }
                return response;
            },
            responseError: (error) => {
                console.log("FetchInterceptor responseError: ", error);
                this.router.push({ path: '/error',}); // Redirect to error page
                return Promise.reject(error);
            }
        });
        console.log("FetchInterceptor has been registered.");
    }
}