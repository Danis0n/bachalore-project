import React from 'react';
import {useAuth} from "../../hook/useAuth";
import {Link, Navigate} from "react-router-dom";
import {useActions} from "../../hook/useActions";
import {SubmitHandler, useForm} from "react-hook-form";
import {UsernamePassword} from "../../store/user/user.interface";
import {useTranslation} from "react-i18next";
import {Alert, Button, Card, Container, Form} from "react-bootstrap"

const Login = () => {

    const {t} = useTranslation();
    const {user} = useAuth();
    const {login} = useActions();

    const {
        register: loginForm,
        handleSubmit,
    } = useForm<UsernamePassword>({
        mode: 'onChange'
    })

    const onSubmit: SubmitHandler<UsernamePassword> = (data) => {
        login(data)
    }

    if (user != null) {
        return <Navigate to="/home"/>;
    }

    return (
        <Container fluid>
            <h1 className="mt-5 text-center">{t("login.welcome")}</h1>

            <div className="container mt-5">
                <div className="row justify-content-center">
                    <div className="col-md-6">
                        <Card body>
                            <Form onSubmit={handleSubmit(onSubmit)}>
                                <Form.Group className="mb-3">
                                    <Form.Label>{t('login.login')}</Form.Label>
                                    <Form.Control {...loginForm('login')} type="text" placeholder={t('login.login')}/>
                                </Form.Group>

                                <Form.Group className="mb-3">
                                    <Form.Label>{t('login.password')}</Form.Label>
                                    <Form.Control {...loginForm('password')} type="password" placeholder={t('login.password')}/>
                                </Form.Group>

                                <Form.Label>
                                    {t("login.creation_prompt")} <Link to={"/register"}>{t("login.creation_link")}</Link>
                                </Form.Label>

                                <Button variant="primary" type="submit">
                                    {t('login.submit')}
                                </Button>
                            </Form>
                        </Card>
                    </div>
                </div>
            </div>

        </Container>

    );
};

export default Login;