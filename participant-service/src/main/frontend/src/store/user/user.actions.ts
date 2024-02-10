import {createAsyncThunk} from "@reduxjs/toolkit";
import {AuthResponse, UsernamePassword} from "./user.interface";
import {AuthService} from "../../services/auth/AuthService";

export const login = createAsyncThunk<AuthResponse, UsernamePassword>(
    'authentication/login',
    async (data,thunkApi ): Promise<any> => {
        try {
            const response = await AuthService.login(data)
            return response.data
        } catch (error) {
            return thunkApi.rejectWithValue(error)
        }
    }
)

export const register = createAsyncThunk<any, UsernamePassword>(
    'authentication/register',
    async (data,thunkApi ): Promise<any> => {
        try {
            const response = await AuthService.register(data)
            return response.status
        } catch (error) {
            return thunkApi.rejectWithValue(error)
        }
    }
)

export const logout = createAsyncThunk(
    'authentication/logout', async () => {})
