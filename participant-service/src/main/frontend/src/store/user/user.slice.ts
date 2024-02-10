import {createSlice} from "@reduxjs/toolkit";
import {Participant} from "./user.interface";
import {login, logout} from "./user.actions";
import {removeTokenStorage, saveTokenToStorage} from "../../services/auth/AuthHelper";

export interface IInitialState {
    user: Participant | null,
    isLoading: boolean,
    isFail: boolean
}

const initialState: IInitialState = {
    user: null,
    isLoading: false,
    isFail: false
}

export const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {},
    extraReducers: builder => {
        builder
            .addCase(login.pending, state => {
                state.isLoading = true
            })
            .addCase(login.fulfilled, (state, {payload}) => {
                state.isLoading = false
                state.user = payload.participant
                state.isFail = false;
                saveTokenToStorage(payload.tokens)
            })
            .addCase(login.rejected, state => {
                state.isLoading = false
                state.user = null
                state.isFail = true;
            })
            .addCase(logout.fulfilled, (state, {payload}) => {
                state.isLoading = false
                state.user = null
                state.isFail = false;
                removeTokenStorage()
            })
    }
})