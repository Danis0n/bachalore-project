import {combineReducers, configureStore} from "@reduxjs/toolkit";
import {userSlice} from "./user/user.slice";
import storage from 'redux-persist/lib/storage'
import {persistReducer, persistStore} from "redux-persist";
import {paymentSlice} from "./payment/payments.slice";
import {balanceSlice} from "./balance/balance.slice";

const persistConfig = {
    key: 'root',
    storage: storage,
}

const rootReducer = combineReducers({
    user: userSlice.reducer,
    payment: paymentSlice.reducer,
    balance: balanceSlice.reducer
})

const persistedReducer = persistReducer(persistConfig, rootReducer)

export const store = configureStore({
    reducer: persistedReducer,
    middleware: getDefaultMiddleware => {
        return getDefaultMiddleware({
            serializableCheck: {}
        });
    }
})

export const persistor = persistStore(store)

export type TypeRootState = ReturnType<typeof rootReducer>
