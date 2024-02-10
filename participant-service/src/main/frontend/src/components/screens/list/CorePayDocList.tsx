import React, {useEffect, useState} from "react";
import {AxiosResponse} from "axios";
import CoreService from "../../../services/CoreService";
import CorePayDoc from "../../../types/CorePayDoc";
import {useTranslation} from "react-i18next";
import {Container, Table} from "react-bootstrap";

const CorePayDocList: React.FC = () => {
    const [corePayDocs, setCorePayDocs] = useState<Array<CorePayDoc>>([]);
    const {t} = useTranslation()

    useEffect(() => {
        retrievePayDocs();
    }, []);

    const retrievePayDocs = () => {
        CoreService.getAllPayDocs()
            .then((response: AxiosResponse<Array<CorePayDoc>>) => setCorePayDocs(response.data))
            .catch((e: Error) => console.log(e));
    };

    return (
        <Container fluid>
            <br />
            <h4>{t('navigation.paydocs')}</h4>

            <Table striped bordered hover variant="secondary">
                <thead>
                <tr>
                    <th>{t('col.debit_account')}</th>
                    <th>{t('col.credit_account')}</th>
                    <th>{t('col.amount')}</th>
                    <th>{t('col.currency')}</th>
                    <th>{t('col.step')}</th>
                    <th>{t('col.value_date')}</th>
                    <th>{t('col.participant_bic')}</th>
                    <th>{t('col.start_time')}</th>
                    <th>{t('col.finish_time')}</th>
                </tr>
                </thead>
                <tbody>
                {corePayDocs.length > 0 ? (
                    corePayDocs.map(paydoc => <PayDoc key={paydoc.guid} paydoc={paydoc} />)
                ) : (
                    <tr>
                        <td colSpan={9} align="center">
                            {t('load_error')}
                        </td>
                    </tr>
                )}
                </tbody>
            </Table>
        </Container>
    );
};

const PayDoc: React.FC<{ paydoc: CorePayDoc }> = ({paydoc}) => {
    return (
        <tr>
            <td>{paydoc.debitAcc}</td>
            <td>{paydoc.creditAcc}</td>
            <td>{paydoc.amount}</td>
            <td>{paydoc.currencyCode}</td>
            <td>{paydoc.step}</td>
            <td>{paydoc.valueDate}</td>
            <td>{paydoc.participantBic}</td>
            <td>{paydoc.startTime}</td>
            <td>{paydoc.finishTime}</td>
        </tr>
    )
}

export default CorePayDocList;