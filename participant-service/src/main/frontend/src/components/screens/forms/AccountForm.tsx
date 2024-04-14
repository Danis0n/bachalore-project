import {SubmitErrorHandler, SubmitHandler, useForm} from "react-hook-form";
import {useTranslation} from 'react-i18next';
import "bootstrap/dist/css/bootstrap.css";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {FC, useState} from "react";
import {Modal} from "react-bootstrap";
import {useActions} from "../../../hook/useActions";
import {AccountForm} from "../../../store/payment/payment.interface";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPaperPlane} from "@fortawesome/free-solid-svg-icons";
import CoreService from "../../../services/CoreService";
import {useAuth} from "../../../hook/useAuth";

const AccountDialog = () => {
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
                {t('navigation.account_form')}
            </Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>{t('account_form.head')}</Modal.Title>
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

    const {register, handleSubmit} = useForm<AccountForm>();

    const [currencies, setCurrencies] = useState<string[]>(
        ["RUB", "USD", "EUR"]
    )

    const submit: SubmitHandler<AccountForm> = data => {
        CoreService.openAccount(String(user?.bic), data.currency)
            .then()
            .catch();

        close()
    }

    const error: SubmitErrorHandler<AccountForm> = data => {
        console.log(data);
    }

    return (
        <>
            <Form onSubmit={handleSubmit(submit, error)}>
                <Form.Group>
                    <Form.Label>{t('account_form.currency')}
                        <Form.Select
                            {...register('currency', {required: true})}
                            defaultValue={currencies[0]}
                        >
                            {currencies && currencies.map(currency => {
                                return (
                                    <option value={currency} key={currency}>
                                        {currency}
                                    </option>
                                )
                            })}
                        </Form.Select>
                    </Form.Label>
                </Form.Group>

                <Button variant="outline-primary" type="submit">
                    {t('common.form.create-account')} <FontAwesomeIcon icon={faPaperPlane}/>
                </Button>
                {/*{ && <Spinner/>}*/}

            </Form>
        </>
    )
};

export default AccountDialog;