import Cookies from "js-cookie";
import {JWTTokenPair} from "../../store/user/user.interface";

const REFRESH_TOKEN: string = 'refreshToken';
const ACCESS_TOKEN: string = 'accessToken';

export const saveTokenToStorage = (data: JWTTokenPair) => {
    Cookies.set(ACCESS_TOKEN, data.accessToken)
    Cookies.set(REFRESH_TOKEN, data.refreshToken)
}

export const getAccessToken = () => {
    const accessToken = Cookies.get(ACCESS_TOKEN)
    return accessToken || null;
}

export const getRefreshToken = () => {
    const refreshToken = Cookies.get(REFRESH_TOKEN)
    return refreshToken || null;
}

export const getUserFromStorage = () => {
    return JSON.parse(localStorage.getItem('user') || '{}')
}

export const removeTokenStorage = () => {
    Cookies.remove(ACCESS_TOKEN)
    Cookies.remove(REFRESH_TOKEN)
    localStorage.removeItem('user')
}