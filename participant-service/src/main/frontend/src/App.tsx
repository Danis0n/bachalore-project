import './App.css'
import './i18n/config';
import React from "react";
import ReactDOM from "react-dom/client";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import {Provider} from "react-redux";
import {persistor, store} from "./store/store";
import AppRouter from "./routes/AppRouter";
import {PersistGate} from "redux-persist/integration/react";

const queryClient = new QueryClient({
    defaultOptions: {
        queries: {
            refetchOnWindowFocus: false
        }
    }
})

ReactDOM.createRoot(document.getElementById('root')!).render(
    <QueryClientProvider client={queryClient}>
        <Provider store={store}>
            <PersistGate persistor={persistor} loading={null}>
                <AppRouter/>
            </PersistGate>
        </Provider>
    </QueryClientProvider>
)