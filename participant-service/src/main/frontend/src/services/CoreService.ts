import CoreIncomingMessage from "../types/CoreIncomingMessage";
import {AxiosResponse} from "axios";
import {coreApi} from "../client/HttpCommon";
import AccountBalance from "../types/AccountBalance";
import CorePayDoc from "../types/CorePayDoc";
import {API} from "../utils/local-storage";
import {MoneyInput} from "../store/payment/payment.interface";
import {Transaction} from "../types/Transaction";

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

const findLatestTransactions = (bic: string, limit: number): Promise<AxiosResponse<Transaction[]>> => {
    return coreApi.get<Transaction[]>(`${API}/transaction/by-bic/${bic}?limit=${limit}`)
}

const findCurrencyByBic = (bic: string): Promise<AxiosResponse<string>> => {
    return coreApi.get<string>(`${API}/balances/by-code/${bic}`)
}

const openAccount = (bic: string, currency: string): Promise<AxiosResponse<void>> => {
    return coreApi.post(`${API}/participant/account/${bic}/${currency}`)
}

const closeAccount = (bic: string, accountCode: string): Promise<AxiosResponse<void>> => {
    return coreApi.delete(`${API}/participant/account/${bic}/${accountCode}`)
}

const inputMoney = (data: MoneyInput) : Promise<AxiosResponse<void>> => {
    return coreApi.put(`${API}/balances`, data)
}

const CoreService = {
    getAllIncomingMessages,
    getAllPayDocs,
    getAccountBalances,
    findBalancesByBic,
    openAccount,
    closeAccount,
    inputMoney,
    findCurrencyByBic,
    findLatestTransactions
};

export default CoreService;
