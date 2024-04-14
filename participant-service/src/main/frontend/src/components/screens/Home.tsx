import React, {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {Col, Container, Row, Table} from "react-bootstrap";
import AccountBalance from "../../types/AccountBalance";
import CoreService from "../../services/CoreService";
import {AxiosResponse} from "axios";
import {useAuth} from "../../hook/useAuth";
import {AccountBalanceComponent} from "./list/AccountBalancesList";
import AccountDialog from "./forms/AccountForm";
import MoneyDialog from "./forms/MoneyInputForm";

const Home: React.FC = () => {

    const {user} = useAuth();

    const [accountBalances, setAccountBalances] = useState<AccountBalance[]>([]);
    const {t} = useTranslation();

    function loadBalances() {
        CoreService.findBalancesByBic(String(user?.bic))
            .then((response: AxiosResponse<Array<AccountBalance>>) => setAccountBalances(response.data))
            .catch((e: Error) => console.log(e))
    }

    useEffect(() => {
        loadBalances();

        const intervalId = setInterval(() => loadBalances(), 3_000);

        return () => clearInterval(intervalId);
    }, []);

    return (
        <Container fluid className="mt-5 p-3">
            <div className="d-flex flex-wrap justify-content-start align-items-center gap-2 mb-3">
                <AccountDialog/>
                <MoneyDialog/>
            </div>

            <h3 className="text-center text-primary mb-4">{t('navigation.home-balance')}</h3>

            <div className="table-responsive">
                <Table striped bordered hover variant="secondary">
                    <thead className="thead-dark">
                    <tr>
                        <th>{t('col.account_name')}</th>
                        <th>{t('col.currency')}</th>
                        <th>{t('col.debit_balance')}</th>
                        <th>{t('col.credit_balance')}</th>
                        <th>{t('col.amount')}</th>
                        <th>{t('col.action')}</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        accountBalances && accountBalances.length > 0 ?
                            accountBalances.map((balance, index) => (
                                <AccountBalanceComponent key={index} balance={balance}/>
                            )) :
                            <tr>
                                <td align="center" colSpan={6} className="text-secondary">
                                    {t('load_error')}
                                </td>
                            </tr>
                    }
                    </tbody>
                </Table>
            </div>
        </Container>

    )
}

export default Home;