import React, {useEffect, useState} from "react";
import AccountBalance from "../../../types/AccountBalance";
import CoreService from "../../../services/CoreService";
import {AxiosResponse} from "axios";
import {useTranslation} from "react-i18next";
import {Container, Table} from "react-bootstrap";

const AccountBalancesList = () => {
    const [accountBalances, setAccountBalances] = useState<AccountBalance[]>([]);
    const {t} = useTranslation();

    useEffect(() => {
        loadBalances();

        const intervalId = setInterval(() => loadBalances(), 3_000);

        return () => clearInterval(intervalId);
    }, []);

    function loadBalances() {
        CoreService.getAccountBalances()
            .then((response: AxiosResponse<Array<AccountBalance>>) => setAccountBalances(response.data))
            .catch((e: Error) => console.log(e))
    }

    return (
        <Container fluid>
            <br/>
            <h3>{t('navigation.balances')}</h3>

            <Table striped bordered hover variant="secondary">
                <thead>
                <tr>
                    <th>{t('col.account_name')}</th>
                    <th>{t('col.currency')}</th>
                    <th>{t('col.debit_balance')}</th>
                    <th>{t('col.credit_balance')}</th>
                    <th>{t('col.amount')}</th>
                </tr>
                </thead>
                <tbody>
                {
                    accountBalances && accountBalances.length > 0
                        ? accountBalances.map((balance, index) => (
                            <AccountBalanceComponent key={index} balance={balance}/>
                        ))
                        : <tr>
                            <td align="center" colSpan={5}>{t('load_error')}</td>
                        </tr>
                }
                </tbody>
            </Table>
        </Container>
    );
};

const AccountBalanceComponent: React.FC<{ balance: AccountBalance }> = ({balance}) => {
    return (
        <tr>
            <td>{balance.name}</td>
            <td>{balance.currencyName}</td>
            <td>{balance.debitBalance}</td>
            <td>{balance.creditBalance}</td>
            <td>{balance.value}</td>
        </tr>
    );
};

export default AccountBalancesList;
