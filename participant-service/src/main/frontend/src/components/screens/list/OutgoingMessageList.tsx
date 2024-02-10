import React, { useState, useEffect } from "react";
import {AxiosResponse} from "axios";
import OutgoingMessage from "../../../types/OutgoingMessage";
import ParticipantService from "../../../services/ParticipantService";
import {useTranslation} from "react-i18next";
import {Container, Table} from "react-bootstrap";

const OutgoingMessageList: React.FC = () => {
    const [messages, setMessages] = useState<Array<OutgoingMessage>>([]);
    const {t} = useTranslation()

    useEffect(() => {
        retrieveOutgoingMessages();
    }, []);

    const retrieveOutgoingMessages = () => {
        ParticipantService.getAllOutgoingMessages()
        .then((response: AxiosResponse<Array<OutgoingMessage>>) => {
            setMessages(response.data);
        })
        .catch((e: Error) => {
            console.log(e);
        });
    };

    console.log(messages, "messages");
    return (
        <Container fluid>
            <br />
            <h4>{t('navigation.outgoing_messages')}</h4>

            <Table striped bordered hover variant="secondary">
                <thead>
                <tr>
                    <th>{t('col.id')}</th>
                    <th>{t('col.sender')}</th>
                    <th>{t('col.type')}</th>
                    <th>{t('col.message')}</th>
                </tr>
                </thead>
                <tbody>
                {messages.length > 0 ? (
                    messages.map((message, index) => (
                        <OutgoingMessageCard message={message} id={index} key={index} />
                    ))
                ) : (
                    <tr>
                        <td colSpan={4} align="center">
                            {t('load_error')}
                        </td>
                    </tr>
                )}
                </tbody>
            </Table>
        </Container>

    );
};

const OutgoingMessageCard: React.FC<{message: OutgoingMessage, id: number}> = ({ message, id }) => {
    return (
        <tr>
            <td>{id}</td>
            <td>{message.sender}</td>
            <td>{message.type}</td>
            <td>{message.msg}</td>
        </tr>
    )
}

export default OutgoingMessageList;