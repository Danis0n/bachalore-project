import {createSlice} from "@reduxjs/toolkit";
import {clearReceiverBalances, findBalancesByBic, findReceiverBalancesByBic} from "./balance.actions";
import AccountBalance from "../../types/AccountBalance";

export interface IInitialState {
    isLoading: boolean,
    balances: AccountBalance[]
    receiverBalances: AccountBalance[]
}

const initialState: IInitialState = {
    isLoading: false,
    balances: [],
    receiverBalances: []
}

export const balanceSlice = createSlice({
    name: 'balance',
    initialState,
    reducers: {},
    extraReducers: builder => {
        builder
            .addCase(findBalancesByBic.pending, (state, {payload}) => {
                state.isLoading = true
            }).addCase(findBalancesByBic.fulfilled, (state, {payload}) => {
                state.isLoading = false
                state.balances = payload
            }).addCase(findBalancesByBic.rejected, (state, {payload}: any) => {
                state.isLoading = false
            })
            .addCase(findReceiverBalancesByBic.pending, (state, {payload}) => {
                state.isLoading = true
            }).addCase(findReceiverBalancesByBic.fulfilled, (state, {payload}) => {
                state.isLoading = false
                state.receiverBalances = payload
            }).addCase(findReceiverBalancesByBic.rejected, (state, {payload}: any) => {
                state.isLoading = false
                state.receiverBalances = []
            })
            .addCase(clearReceiverBalances.fulfilled, (state) => {
                state.receiverBalances = []
            })
    }
})