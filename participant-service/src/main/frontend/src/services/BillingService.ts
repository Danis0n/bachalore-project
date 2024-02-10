import {AxiosResponse} from "axios";
import {billingApi} from "../client/HttpCommon";
import Descriptor from "../types/Descriptor";
import {API} from "../utils/local-storage";

const getAllDescriptors = (): Promise<AxiosResponse<Array<Descriptor>>> => {
    return billingApi.get<Array<Descriptor>>(`${API}/descriptor`);
}

const BillingService = {
    getAllDescriptors
};

export default BillingService;
