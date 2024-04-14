import {SubmitErrorHandler, SubmitHandler, useForm} from "react-hook-form";
import {useTranslation} from 'react-i18next';
import "bootstrap/dist/css/bootstrap.css";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {FC, useEffect, useState} from "react";
import {Modal} from "react-bootstrap";
import {useActions} from "../../../hook/useActions";
import {MoneyInput} from "../../../store/payment/payment.interface";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPaperPlane} from "@fortawesome/free-solid-svg-icons";
import CoreService from "../../../services/CoreService";
import {useAuth} from "../../../hook/useAuth";
import {AxiosResponse} from "axios";
import CorePayDoc from "../../../types/CorePayDoc";

const MoneyDialog = () => {
    const {t} = useTranslation();
    const [show, setShow] = useState(false);

    const {setFail, setSuccess, clearReceiverBalances} = useActions()

    const setDefaultSettings = () => {
        setFail(false)
        setSuccess(false)
        clearReceiverBalances()
    }

    const handleClose = () => {
        setShow(false)
        setDefaultSettings()
    };
    const handleShow = () => {
        setShow(true)
        setDefaultSettings()
    };

    return (
        <>
            <Button variant="dark" onClick={handleShow}>
                {t('navigation.money_form')}
            </Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>{t('money-form.head')}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <AccountForm close={handleClose}/>
                </Modal.Body>
            </Modal>
        </>
    );
};

interface IAccount {
    close: () => void
}

const AccountForm: FC<IAccount> = ({close}) => {

    const {user} = useAuth();
    const {t} = useTranslation();

    const [code, setCode] = useState<string>('')

    const {register, handleSubmit} = useForm<MoneyInput>();

    const [currency, setCurrency] = useState<string>('')

    const submit: SubmitHandler<MoneyInput> = data => {
        CoreService
            .inputMoney({
                accountCode: code,
                value: data.value,
                bic: String(user?.bic)
            })
            .then()
            .catch();

        close()
    }

    const error: SubmitErrorHandler<MoneyInput> = data => {
        console.log(data);
    }

    useEffect(() => {
        CoreService.findCurrencyByBic(code)
            .then((response: AxiosResponse<string>) => setCurrency(response.data))
            .catch((e: Error) => console.log(e));
    }, [code]);

    return (
        <>
            <Form onSubmit={handleSubmit(submit, error)}>

                <Form.Group>
                    <Form.Label>{t('money-form.value')}
                        <Form.Control
                            {...register('value', {required: true})}
                            type="text"/>
                    </Form.Label>
                </Form.Group>

                <Form.Group>
                    <Form.Label>{t('money-form.accountCode')}
                        <Form.Control
                            onChange={(e) => setCode(String(e.target.value))}
                            type="text"/>
                    </Form.Label>
                </Form.Group>

                {currency &&
                    <Form.Group>
                        <Form.Label>{t('money-form.bic')}
                            <Form.Control
                                type="text" value={currency} disabled/>
                        </Form.Label>
                    </Form.Group>
                }

                <Button variant="outline-primary" type="submit">
                    {t('common.form.create-account')} <FontAwesomeIcon icon={faPaperPlane}/>
                </Button>
                {/*{ && <Spinner/>}*/}

            </Form>
        </>
    )
};

export default MoneyDialog;