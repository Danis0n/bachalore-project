import React from 'react';
import {useAuth} from "../../hook/useAuth";
import {Navigate, useNavigate} from "react-router-dom";
import {useActions} from "../../hook/useActions";
import {SubmitHandler, useForm} from "react-hook-form";
import {UsernamePassword} from "../../store/user/user.interface";
import {useTranslation} from "react-i18next";
import {Button, Card, Container, Form} from "react-bootstrap"

const Register = () => {

    const {t} = useTranslation();
    const {user} = useAuth();
    const {register} = useActions();
    const navigate = useNavigate();

    const {
        register: registerForm,
        handleSubmit,
    } = useForm<UsernamePassword>({
        mode: 'onChange'
    })

    const onSubmit: SubmitHandler<UsernamePassword> = (data) => {
        register(data)
        navigate('/')
    }

    if (user != null) {
        return <Navigate to="/home"/>;
    }

    return (
        <Container fluid>
            <h1 className="mt-5 text-center">{t("registration.welcome")}</h1>

            <div className="container mt-5">
                <div className="row justify-content-center">
                    <div className="col-md-6">
                        <Card body>
                            <Form onSubmit={handleSubmit(onSubmit)}>
                                <Form.Group className="mb-3">
                                    <Form.Label>{t('registration.login')}</Form.Label>
                                    <Form.Control {...registerForm('login')} type="text" placeholder={t('registration.login')}/>
                                </Form.Group>

                                <Form.Group className="mb-3">
                                    <Form.Label>{t('registration.password')}</Form.Label>
                                    <Form.Control {...registerForm('password')} type="password" placeholder={t('registration.password')}/>
                                </Form.Group>
y
                                <Form.Group className="mb-3">
                                    <Form.Label>{t('registration.email')}</Form.Label>
                                    <Form.Control {...registerForm('email')} type="text" placeholder={t('registration.email')}/>
                                </Form.Group>

                                <Button variant="primary" type="submit">
                                    {t('registration.submit')}
                                </Button>
                            </Form>
                        </Card>
                    </div>
                </div>
            </div>

        </Container>

    );
};

export default Register;