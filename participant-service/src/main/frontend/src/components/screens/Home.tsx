import React, {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {Button, Col, Container, NavDropdown, Row, Table} from "react-bootstrap";
import AccountBalance from "../../types/AccountBalance";
import CoreService from "../../services/CoreService";
import {AxiosResponse} from "axios";
import {useAuth} from "../../hook/useAuth";
import {AccountBalanceComponent} from "./list/AccountBalancesList";
import AccountDialog from "./forms/AccountForm";
import MoneyDialog from "./forms/MoneyInputForm";
import User from "./User";
import {Link} from "react-router-dom";

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
            {user && user.role != 'ADMIN' &&
                <div>
                    <h3>{t('home-page.welcome-user')}</h3>

                    <div className="d-flex flex-wrap justify-content-start align-items-center gap-2 mb-3">
                        <AccountDialog/>
                        <MoneyDialog/>
                    </div>

                    <div className="d-flex flex-wrap justify-content-start align-items-center gap-2 mb-3">
                        <Button variant="dark">
                            <Link to="/currency"
                                  className="text-white text-decoration-none">{t('navigation.currency')}</Link>
                        </Button>

                        <Button variant="dark">
                            <Link className="text-white text-decoration-none"
                                  to="/descriptor">{t('navigation.descriptor')}</Link>
                        </Button>

                        <Button variant="dark">
                            <Link className="text-white text-decoration-none"
                                  to="/report">{t('navigation.report')}</Link>
                        </Button>
                    </div>

                    <User/>
                </div>
            }

            {user && user.role == 'ADMIN' &&
                <div>
                    <h3>{t('home-page.welcome-admin')}</h3>
                    <h3>{t('user-page.name')}: {user?.name}</h3>

                    <div className="d-flex flex-wrap justify-content-start align-items-center gap-2 mb-3">
                        <Button variant="dark">
                            <Link to="/currency"
                                  className="text-white text-decoration-none">{t('navigation.currency')}</Link>
                        </Button>

                        <Button variant="dark">
                            <Link className="text-white text-decoration-none"
                                  to="/descriptor">{t('navigation.descriptor')}</Link>
                        </Button>

                        <Button variant="dark">
                            <Link className="text-white text-decoration-none"
                                  to="/participants">{t('navigation.participants')}</Link>
                        </Button>

                        <Button variant="dark">
                            <Link className="text-white text-decoration-none"
                                  to="/incoming-message">{t('navigation.incoming_messages')}</Link>
                        </Button>

                        <Button variant="dark">
                            <Link className="text-white text-decoration-none"
                                  to="/outgoing-message">{t('navigation.outgoing_messages')}</Link>
                        </Button>

                        <Button variant="dark">
                            <Link className="text-white text-decoration-none"
                                  to="/balances">{t('navigation.balances')}</Link>
                        </Button>

                        <Button variant="dark">
                            <Link className="text-white text-decoration-none"
                                  to="/paydocs">{t('navigation.paydocs')}</Link>
                        </Button>

                        <Button variant="dark">
                            <Link className="text-white text-decoration-none"
                                  to="/report">{t('navigation.report')}</Link>
                        </Button>

                    </div>
                </div>
            }

        </Container>

    )
}

export default Home;