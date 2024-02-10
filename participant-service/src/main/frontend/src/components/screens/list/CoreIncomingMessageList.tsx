import React, {useEffect, useState} from "react";
import CoreIncomingMessage from "../../../types/CoreIncomingMessage";
import {AxiosResponse} from "axios";
import CoreService from "../../../services/CoreService";
import {useTranslation} from "react-i18next";
import {Container, Table} from "react-bootstrap";

const IncomingMessageList: React.FC = () => {
    const [coreIncomingMessages, setCoreIncomingMessages] = useState<Array<CoreIncomingMessage>>([]);
    const {t} = useTranslation()

    useEffect(() => {
        retrieveIncomingMessages();
    }, []);

    const retrieveIncomingMessages = () => {
        CoreService.getAllIncomingMessages()
            .then((response: AxiosResponse<Array<CoreIncomingMessage>>) => setCoreIncomingMessages(response.data))
            .catch((e: Error) => console.log(e));
    };

    return (
        <Container fluid>
            <br/>
            <h3>{t('navigation.incoming_messages')}</h3>

            <Table striped bordered hover variant="secondary">
                <thead>
                <tr>
                    <th>{t('col.message')}</th>
                    <th>{t('col.type_name')}</th>
                    <th>{t('col.sender_bic')}</th>
                </tr>
                </thead>
                <tbody>
                {
                    coreIncomingMessages.length > 0
                        ? coreIncomingMessages.map(message => (<IncomingMessage incomingMessage={message}/>))
                        : (
                            <tr>
                                <td align="center" colSpan={3}>{t('load_error')}</td>
                            </tr>
                        )
                }
                </tbody>
            </Table>
        </Container>
    )
};

const IncomingMessage: React.FC<{ incomingMessage: CoreIncomingMessage }> = ({incomingMessage}) => {
    return (
        <tr>
            <td>{incomingMessage.msg}</td>
            <td>{incomingMessage.type}</td>
            <td>{incomingMessage.sender}</td>
        </tr>
    )
}

export default IncomingMessageList;