import {createAsyncThunk} from "@reduxjs/toolkit";
import ParticipantService from "../../services/ParticipantService";
import {TransferForm} from "./payment.interface";

export const makePayment = createAsyncThunk<void, TransferForm>(
    'participant/makePayment',
    async (data,thunkApi ): Promise<any> => {
        try {
            const response = await ParticipantService.triggerPayment(data)
            return response.data
        } catch (error) {
            return thunkApi.rejectWithValue(error)
        }
    }
)

export const setFail = createAsyncThunk<boolean, boolean>(
    'participant/setFail',
    (data, _): boolean => {
        return data;
    }
)

export const setSuccess = createAsyncThunk<boolean, boolean>(
    'participant/setSuccess',
    (data, _): boolean => {
        return data;
    }
)