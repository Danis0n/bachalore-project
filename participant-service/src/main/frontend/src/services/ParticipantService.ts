import ParticipantData from "../types/Participant";
import {AxiosResponse} from "axios";
import {participantApi} from "../client/HttpCommon";
import OutgoingMessage from "../types/OutgoingMessage";
import {Currency} from "../types/Currency";
import {TransferForm} from "../store/payment/payment.interface";
import {API} from "../utils/local-storage";

const getAll = (): Promise<AxiosResponse<Array<ParticipantData>>> => {
    return participantApi.get<Array<ParticipantData>>(`${API}/participant`);
};

const getAllOutgoingMessages = (): Promise<AxiosResponse<Array<OutgoingMessage>>> => {
    return participantApi.get<Array<OutgoingMessage>>(`${API}/outgoing-message`);
};

const getByBic = (bic: string) : Promise<AxiosResponse<ParticipantData>> => {
    return participantApi.get<ParticipantData>(`${API}/participant/info/${bic}`)
}

const triggerPayment = (data: TransferForm): Promise<AxiosResponse<void>> => {
    return participantApi.post(`${API}/transfer`, data);
};

const getAllCurrencies = (): Promise<AxiosResponse<Array<Currency>>> => {
    return participantApi.get(`${API}/currency`)
}

const ParticipantService = {
    getAll,
    getAllOutgoingMessages,
    triggerPayment,
    getAllCurrencies,
    getByBic
};
  
export default ParticipantService;
