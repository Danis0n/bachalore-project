import {createAsyncThunk} from "@reduxjs/toolkit";
import CoreService from "../../services/CoreService";
import AccountBalance from "../../types/AccountBalance";

export const findBalancesByBic = createAsyncThunk<AccountBalance[], string>(
    'balance/findBalanceByBic',
    async (data, thunkApi): Promise<any> => {
        try {
            const response = await CoreService.findBalancesByBic(data)
            return response.data
        } catch (error) {
            return thunkApi.rejectWithValue(error)
        }
    }
)

export const findReceiverBalancesByBic = createAsyncThunk<AccountBalance[], string>(
    'balance/findReceiverBalancesByBic',
    async (data, thunkApi): Promise<any> => {
        try {
            const response = await CoreService.findBalancesByBic(data)
            return response.data
        } catch (error) {
            return thunkApi.rejectWithValue(error)
        }
    }
)

export const clearReceiverBalances = createAsyncThunk<void, void>(
    'balance/setReceiverBalances',
    (_, thunkApi): any => {}
)

