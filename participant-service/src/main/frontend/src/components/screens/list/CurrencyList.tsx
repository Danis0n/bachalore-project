import React, {useEffect, useState} from "react";
import {AxiosResponse} from "axios";
import {useTranslation} from "react-i18next";
import {Currency} from "../../../types/Currency";
import ParticipantService from "../../../services/ParticipantService";
import {Container, Table} from "react-bootstrap";

const CurrencyList = () => {
    const [currencies, setCurrencies] = useState<Currency[]>([]);
    const { t } = useTranslation();

    useEffect(() => {
        ParticipantService.getAllCurrencies()
            .then((response: AxiosResponse<Array<Currency>>) => setCurrencies(response.data))
            .catch((e: Error) => console.log(e));
    }, []);

    return (
        <Container fluid>
            <br />
            <h4>{t('navigation.currency')}</h4>

            <Table striped bordered hover variant="secondary">
                <thead>
                <tr>
                    <th>{t('col.id')}</th>
                    <th>{t('col.currency_code')}</th>
                    <th>{t('col.currency_description')}</th>
                </tr>
                </thead>
                <tbody>
                {currencies && currencies.length > 0 ? (
                    currencies.map((currency, index) => (
                        <CurrencyComponent key={index} currency={currency} />
                    ))
                ) : (
                    <tr>
                        <td colSpan={3} align="center">
                            {t('load_error')}
                        </td>
                    </tr>
                )}
                </tbody>
            </Table>
        </Container>

    );
};

const CurrencyComponent: React.FC<{currency: Currency}> = ({ currency}) => {
    return (
        <tr>
            <td>{currency.id}</td>
            <td>{currency.code}</td>
            <td>{currency.description}</td>
        </tr>
    );
};

export default CurrencyList;
