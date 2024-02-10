import React, {useEffect, useState} from "react";
import Descriptor from "../../../types/Descriptor";
import BillingService from "../../../services/BillingService";
import {AxiosResponse} from "axios";
import {useTranslation} from "react-i18next";
import {Container, Table} from "react-bootstrap";

const DescriptorList = () => {
    const [descriptors, setDescriptors] = useState<Descriptor[]>([]);
    const { t } = useTranslation();

    useEffect(() => {
        BillingService.getAllDescriptors()
            .then((response: AxiosResponse<Descriptor[]>) => setDescriptors(response.data))
            .catch((e: Error) => console.log(e));
    }, []);

    return (
        <Container fluid>
            <br />
            <h4>{t('navigation.descriptor')}</h4>

            <Table striped bordered hover variant="secondary">
                <thead>
                <tr>
                    <th>{t('col.name')}</th>
                    <th>{t('col.type')}</th>
                    <th>{t('col.amount')}</th>
                </tr>
                </thead>
                <tbody>
                {descriptors && descriptors.length > 0 ? (
                    descriptors.map((descriptor) => (
                        <DescriptorComponent key={descriptor.id} descriptor={descriptor} />
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

const DescriptorComponent: React.FC<{descriptor: Descriptor}> = ({descriptor}) => {
    const rateDescription = descriptor.rate
        ? `${descriptor.rate}`
        : `${descriptor.minPercent}% - ${descriptor.maxPercent}%`;

    return (
        <tr>
            <td>{descriptor.name}</td>
            <td>{descriptor.type}</td>
            <td>{rateDescription}</td>
        </tr>
    );
};

export default DescriptorList;
