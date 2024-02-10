import React from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import PrivateRoute from "../hoc/PrivateRoute";
import CorePayDocList from "../components/screens/list/CorePayDocList";
import DescriptorList from "../components/screens/list/DescriptorList";
import AccountBalancesList from "../components/screens/list/AccountBalancesList";
import CoreIncomingMessageList from "../components/screens/list/CoreIncomingMessageList";
import OutgoingMessageList from "../components/screens/list/OutgoingMessageList";
import ParticipantList from "../components/screens/list/ParticipantList";
import Home from "../components/screens/Home";
import Login from "../components/screens/Login";
import Report from "../components/screens/Report";
import CurrencyList from "../components/screens/list/CurrencyList";
import Register from "../components/screens/Register";

export default function AppRouter() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/register" element={<Register />} />

                <Route element={<PrivateRoute />}>
                    <Route path="/home" element={<Home />} />
                    <Route path="/currency" element={<CurrencyList />} />
                    <Route path="/paydocs" element={<CorePayDocList />} />
                    <Route path="/descriptor" element={<DescriptorList />} />
                    <Route path="/report" element={<Report />} />
                    <Route path="/balances" element={<AccountBalancesList />} />
                    <Route path="/incoming-message" element={<CoreIncomingMessageList />} />
                    <Route path="/outgoing-message" element={<OutgoingMessageList />} />
                    <Route path="/participants" element={<ParticipantList />} />
                </Route>

                <Route path="*" element={<div>404... not found </div>} />
            </Routes>
        </BrowserRouter>
    )
}