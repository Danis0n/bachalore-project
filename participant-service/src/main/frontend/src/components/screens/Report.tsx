import React, {FC} from 'react';
import {ReportOptions} from "../../types/ReportOptions";
import {SubmitHandler, useForm} from "react-hook-form";
import {billingUrl} from "../../client/HttpCommon";
import {useTranslation} from "react-i18next";
import {Button, Card, Container, Form} from "react-bootstrap";

const Report: FC = () => {
    const {t} = useTranslation()

    const {
        register: reportForm,
        handleSubmit,
    } = useForm<ReportOptions>({
        mode: 'onChange'
    })

    const onSubmit: SubmitHandler<ReportOptions> = (data) => {
        let link = billingUrl() + '/result' + "?format=" + data.format;

        if (data.bic) {
            link += '&bic=' + data.bic
        }
        if (data.startDate) {
            link += '&startDate=' + data.startDate
        }
        if (data.endDate) {
            link += '&endDate=' + data.endDate
        }

        window.location.replace(link);
    }

    return (
        <Container fluid>
            <h1 className="mt-5 text-center">{t('navigation.report')}</h1>

            <div className="container mt-5">
                <div className="row justify-content-center">
                    <div className="col-md-6">
                        <Card body>
                            <Form onSubmit={handleSubmit(onSubmit)}>

                                <Form.Group className="mb-3">
                                    <Form.Label>{t('report.format')}</Form.Label>
                                    <Form.Select {...reportForm('format')}
                                                 placeholder={t('login.login')}
                                                 defaultValue={'pdf'}
                                                 required>

                                        <option value="pdf">PDF</option>
                                        <option value="xlsx">XLSX</option>
                                    </Form.Select>
                                </Form.Group>

                                <Form.Group className="mb-3">
                                    <Form.Label>{t('report.date_from')}</Form.Label>
                                    <Form.Control {...reportForm('startDate')} type="datetime-local"/>
                                </Form.Group>

                                <Form.Group className="mb-3">
                                    <Form.Label>{t('report.date_to')}</Form.Label>
                                    <Form.Control {...reportForm('endDate')} type="datetime-local"/>
                                </Form.Group>

                                <Button variant="primary" type="submit">
                                    {t('report.request_button')}
                                </Button>
                            </Form>
                        </Card>
                    </div>
                </div>
            </div>

        </Container>
    )
};

export default Report;