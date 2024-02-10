import {participantApi} from "../../client/HttpCommon";
import {AxiosResponse} from "axios";
import {AuthResponse, UsernamePassword} from "../../store/user/user.interface";
import {API} from "../../utils/local-storage";

export const AuthService = {

    async login (data: UsernamePassword): Promise<AxiosResponse<AuthResponse>> {
        return participantApi.post(`${API}/authentication/login`, data)
    },

    async register (data: UsernamePassword): Promise<AxiosResponse<AuthResponse>> {
        return participantApi.post("/authentication/register", data)
    },

    async refresh() {
        return participantApi.get(`${API}/authentication/refresh`);
    },
}