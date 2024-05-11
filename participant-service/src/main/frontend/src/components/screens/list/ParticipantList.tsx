import React, { useState, useEffect } from "react";
import ParticipantData from "../../../types/Participant";
import ParticipantDataService from "../../../services/ParticipantService";
import {AxiosResponse} from "axios";
import {useTranslation} from "react-i18next";
import {Container, Table} from "react-bootstrap";
import {Link} from "react-router-dom";


const ParticipantList: React.FC = () => {
    const [participants, setParticipants] = useState<Array<ParticipantData>>([]);
    const {t} = useTranslation()

    useEffect(() => {
        retrieveParticipants();
    }, []);

    const retrieveParticipants = () => {
        ParticipantDataService.getAll()
        .then((response: AxiosResponse<Array<ParticipantData>>) => {
            setParticipants(response.data);
        })
        .catch((e: Error) => {
            console.log(e);
        });
    };

    return (
        <Container fluid>
            <br />
            <h4>{t('navigation.participants')}</h4>

            <Table striped bordered hover variant="secondary">
                <thead>
                <tr>
                    <th>{t('col.id')}</th>
                    <th>{t('col.name')}</th>
                    <th>{t('col.bic')}</th>
                    <th>{t('col.type')}</th>
                    <th>{t('col.reg_date')}</th>
                </tr>
                </thead>
                <tbody>
                {participants.length > 0 ? (
                    participants.map((participant) => (
                        <Participant key={participant.id} participant={participant} />
                    ))
                ) : (
                    <tr>
                        <td colSpan={5} align="center">
                            {t('load_error')}
                        </td>
                    </tr>
                )}
                </tbody>
            </Table>
        </Container>

    );
};

const Participant: React.FC<{participant: ParticipantData}> = ({ participant }) => {
    return (
        <tr>
            <td>{participant.id}</td>
            <td><Link to={`/user?bic=${participant.bic}`}>{participant.name}</Link></td>
            <td>{participant.bic}</td>
            <td>{participant.type?.name}</td>
            <td>{participant.registrationDate}</td>
        </tr>
    )
}

export default ParticipantList;