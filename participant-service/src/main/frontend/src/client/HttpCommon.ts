import axios from "axios";
import {getAccessToken, getRefreshToken, saveTokenToStorage} from "../services/auth/AuthHelper";
import {logout} from "../store/user/user.actions";
import {JWTTokenPair} from "../store/user/user.interface";

const headers = {
    "Content-type": "application/json"
}

export const billingUrl = (): string => {
    return process.env.BILLING_API_URL || '';
}

export const participantApi = axios.create({
    withCredentials: true,
    baseURL: process.env.PARTICIPANT_API_URL,
    headers: headers
});

participantApi.interceptors.request.use(
    (config) => {
        const accessToken = getAccessToken();

        if (accessToken != null || accessToken !== 'undefined') {
            config.headers.Authorization = `Bearer ${accessToken}`
        }
        return config;
    }
)

participantApi.interceptors.response.use(
    (config) => {
        return config;
    },
    async (error) => {
        const originalRequest = {...error.config};
        originalRequest._isRetry = true;
        if (
            error.response.status === 403 &&
            error.config &&
            !error.config._isRetry
        ) {
            try {

                const refreshToken = getRefreshToken();

                if (refreshToken == null || refreshToken === 'undefined') {
                    logout()
                } else {
                    const resp = await participantApi.post<JWTTokenPair>(
                        `/authentication/refresh&token=${refreshToken}`
                    );

                    saveTokenToStorage(resp.data)
                    return participantApi.request(originalRequest);
                }
            } catch (error) {
                console.log("AUTH ERROR");
            }
        }
        throw error;
    }
);

export const coreApi = axios.create({
    baseURL: process.env.CORE_API_URL,
    headers: headers
});

export const billingApi = axios.create({
    baseURL: billingUrl(),
    headers: headers
});
