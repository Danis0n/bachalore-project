import React, {useEffect, useState} from 'react';
import {useAuth} from "../../hook/useAuth";
import {useTranslation} from "react-i18next";
import AccountBalance from "../../types/AccountBalance";
import CoreService from "../../services/CoreService";
import {AxiosResponse} from "axios";
import {Link, redirect, useSearchParams} from "react-router-dom";
import {Transaction} from "../../types/Transaction";
import {Container, ListGroup, Table} from "react-bootstrap";
import {AccountBalanceComponent} from "./list/AccountBalancesList";
import ParticipantService from "../../services/ParticipantService";
import ParticipantData from "../../types/Participant";


const User: React.FC = () => {

    const {t} = useTranslation();

    const {user: me} = useAuth()

    const [searchParams, setSearchParams] = useSearchParams();
    const bic = searchParams.get("bic")

    const [accountBalances, setAccountBalances] = useState<AccountBalance[]>([]);
    const [transactions, setTransactions] = useState<Transaction[]>([]);
    const [user, setUser] = useState<ParticipantData>();

    const loadBalances = (bic: string) => {
        CoreService.findBalancesByBic(String(bic))
            .then((response: AxiosResponse<AccountBalance[]>) => setAccountBalances(response.data))
            .catch((e: Error) => console.log(e))
    }

    const loadLatestTransaction = (bic: string, limit: number) => {
        CoreService.findLatestTransactions(String(bic), limit)
            .then((response: AxiosResponse<Transaction[]>) => setTransactions(response.data))
            .catch((e: Error) => console.log(e))
    }

    const loadUser = (bic: string) => {
        ParticipantService.getByBic(bic)
            .then((response: AxiosResponse<ParticipantData>) => setUser(response.data))
            .catch((e: Error) => console.log(e))
    }

    useEffect(() => {
        if (bic == null && me != null) {
            loadUser(me.bic)
            loadBalances(me.bic);
            loadLatestTransaction(me.bic, 1000)
        }
        if (bic != null) {
            loadUser(bic)
            loadBalances(bic);
            loadLatestTransaction(bic, 1000)
        }

        const intervalId = setInterval(
            () => {
                loadUser(bic == null ? me!.bic : bic)
                loadBalances(bic == null ? me!.bic : bic);
                loadLatestTransaction(bic == null ? me!.bic : bic, 1000)
            }, 20_000
        );

        return () => clearInterval(intervalId);
    }, []);

    useEffect(() => {
        if (user == null) {
            redirect("/")
            //     todo: redirect to '/'
        }
    }, [user]);

    return (
        <div>
            <Container fluid>
                <br/>
                <h3>{t('user-page.name')}: {user?.name} - {t('user-page.bic')}: {user?.bic}</h3>

                {user && user?.role == 'USER' &&
                    <div>
                        <h3>{t('user-page.balance')}</h3>

                        <div style={{
                            padding: 5,
                            color: "black",
                        }}><Link to={`/report?bic=${bic}`}>Отчёт</Link></div>

                        <Table striped bordered hover variant="Primary">
                            <thead>
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

                        {!!transactions && transactions.length > 0 &&
                            <Container>
                                <br/>
                                <h3>{t('user-page.transactions-history')}</h3>

                                <ListGroup>
                                    {
                                        transactions && transactions.length > 0
                                            ? transactions.map((transaction, index) => (
                                                <TransactionComponent
                                                    key={index}
                                                    transaction={transaction}
                                                />
                                            ))
                                            :
                                            <div></div>
                                    }
                                </ListGroup>
                            </Container>
                        }
                    </div>
                }
            </Container>
        </div>
    );
};

export const TransactionComponent: React.FC<{ transaction: Transaction }> = ({transaction}) => {
    return (
        <div style={{
            padding: 5, margin: 5
        }}>
            <ListGroup.Item>
                {transaction.creditAccount} - {transaction.debitAccount} | {transaction.amount} - {transaction.currency} | {transaction.time}
            </ListGroup.Item>
        </div>
    )
};

export default User;
