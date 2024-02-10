import {createSlice} from "@reduxjs/toolkit";
import {makePayment, setFail, setSuccess} from "./payment.actions";

export interface IInitialState {
    isLoading: boolean,
    isSuccess: boolean,
    isFail: boolean
}

const initialState: IInitialState = {
    isLoading: false,
    isFail: false,
    isSuccess: false
}

export const paymentSlice = createSlice({
    name: 'payment',
    initialState,
    reducers: {},
    extraReducers: builder => {
        builder
            .addCase(makePayment.pending, (state, {payload}) => {
                state.isLoading = true
                state.isFail = false
            }).addCase(makePayment.fulfilled, (state, {payload}) => {
                state.isLoading = false
                state.isFail = false
                state.isSuccess = true
            }).addCase(makePayment.rejected, (state) => {
                state.isLoading = false
                state.isFail = true
            })
            .addCase(setFail.fulfilled, (state, {payload}) => {
                state.isFail = payload
            })
            .addCase(setSuccess.fulfilled, (state, {payload}) => {
                state.isSuccess = payload
            })
    }
})