import CoreIncomingMessage from "../types/CoreIncomingMessage";
import {AxiosResponse} from "axios";
import {coreApi} from "../client/HttpCommon";
import AccountBalance from "../types/AccountBalance";
import CorePayDoc from "../types/CorePayDoc";
import {API} from "../utils/local-storage";

const getAllIncomingMessages = (): Promise<AxiosResponse<Array<CoreIncomingMessage>>> => {
    return coreApi.get<Array<CoreIncomingMessage>>(`${API}/incoming-message`);
};

const getAllPayDocs = (): Promise<AxiosResponse<Array<CorePayDoc>>> => {
    return coreApi.get<Array<CorePayDoc>>(`${API}/paydocs`);
};

const getAccountBalances = (): Promise<AxiosResponse<AccountBalance[]>> => {
    return coreApi.get<AccountBalance[]>(`${API}/balances`);
}

const findBalancesByBic = (bic: string): Promise<AxiosResponse<AccountBalance[]>> => {
    return coreApi.get<AccountBalance[]>(`${API}/balances/by-bic/${bic}`)
}

const CoreService = {
    getAllIncomingMessages,
    getAllPayDocs,
    getAccountBalances,
    findBalancesByBic
};

export default CoreService;
