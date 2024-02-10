import {SubmitErrorHandler, SubmitHandler, useForm} from "react-hook-form";
import {useTranslation} from 'react-i18next';
import "bootstrap/dist/css/bootstrap.css";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faPaperPlane} from "@fortawesome/free-solid-svg-icons";
import {FC, useEffect, useState} from "react";
import {Modal, Spinner} from "react-bootstrap";
import {useAuth} from "../../../hook/useAuth";
import {useActions} from "../../../hook/useActions";
import {TransferForm} from "../../../store/payment/payment.interface";
import {usePayment} from "../../../hook/usePayment";
import {useBalance} from "../../../hook/useBalance";
import {setFail} from "../../../store/payment/payment.actions";
import {useDebounce} from "../../../hook/useDebounce";

const PaymentDialog = () => {
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
                {t('navigation.payment_form')}
            </Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>{t('payment_form.head')}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <PaymentForm close={handleClose}/>
                </Modal.Body>
            </Modal>
        </>
    );
};

interface Payment {
    close: () => void
}

const PaymentForm: FC<Payment> = ({close}) => {
    const {t} = useTranslation();
    const {register, handleSubmit} =
        useForm<TransferForm>();

    const {isFail, isLoading, isSuccess,} = usePayment()
    const {balances, receiverBalances} = useBalance()
    const {user} = useAuth();
    const {makePayment, findBalancesByBic, findReceiverBalancesByBic} = useActions()

    const [bicCd, setBicCd] = useState<string>('')
    const searchBicCd = useDebounce(bicCd, 500)

    const [enoughMoney, setEnoughMoney] = useState(true)

    const submit: SubmitHandler<TransferForm> = data => {
        data.bicDb = searchBicCd
        data.bicCd = user!.bic
        if (data.value > balances.filter(b => b.code == data.codeCd)[0].value) {
            setEnoughMoney(false)
            setFail(false)
        } else {
            setEnoughMoney(true)
            makePayment(data);
        }
    }

    const error: SubmitErrorHandler<TransferForm> = data => {
        console.log(data);
    }

    useEffect(() => {
        if (isSuccess) {
            close()
        }
    }, [isSuccess]);

    useEffect(() => {
        findBalancesByBic(user!.bic)
    }, []);

    useEffect(() => {
        if (searchBicCd != '') {
            findReceiverBalancesByBic(searchBicCd)
        }

    }, [searchBicCd]);

    return (
        <>
            <Form onSubmit={handleSubmit(submit, error)}>
                <Form.Group>
                    <Form.Label>{t('payment_form.bic_cd')}
                        <Form.Control type="text" value={user?.bic} disabled/>
                    </Form.Label>
                </Form.Group>

                <Form.Group>
                    <Form.Label>{t('payment_form.account')}
                        <Form.Select
                            {...register('codeCd', {required: true})}
                            value={user?.bic}
                        >
                            {balances && balances.map(balance => {
                                return (
                                    <option value={balance.code} key={balance.name}>
                                        {balance.name} - {balance.value} - {balance.currencyName}
                                    </option>
                                )
                            })}
                        </Form.Select>
                    </Form.Label>
                </Form.Group>

                <Form.Group>
                    <Form.Label>{t('payment_form.bic_db')}
                        <Form.Control
                            type="text"
                            onChange={(e) => setBicCd(String(e.target.value))}
                        />
                    </Form.Label>
                </Form.Group>

                {receiverBalances && receiverBalances.length != 0 &&
                    <Form.Group>
                        <Form.Label>{t('payment_form.account')}
                            <Form.Select
                                {...register('codeDb', {required: false})}
                                defaultValue={receiverBalances[0].code}
                            >
                                {receiverBalances && receiverBalances.map(balance => {
                                    return (
                                        <option value={balance.code} key={balance.name}>
                                            {balance.name} - {balance.value} - {balance.currencyName}
                                        </option>
                                    )
                                })}
                            </Form.Select>
                        </Form.Label>
                    </Form.Group>
                }

                <Form.Group>
                    <Form.Label>{t('payment_form.value')}
                        <Form.Control
                            type="text"
                            {...register('value', {required: true})}
                        />
                    </Form.Label>
                </Form.Group>

                {isFail && <div>
                    {t('usernotfound_error')}
                </div>}

                {!enoughMoney && <div>
                    {t('notenoughmoney_error')}
                </div>}

                <br></br>

                <Button variant="outline-primary" type="submit">
                    {t('common.form.send')} <FontAwesomeIcon icon={faPaperPlane}/>
                </Button>
                {isLoading && <Spinner/>}

            </Form>
        </>
    )
};

export default PaymentDialog;