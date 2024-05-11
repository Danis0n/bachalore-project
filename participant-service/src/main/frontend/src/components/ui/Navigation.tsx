import React from "react";
import {Container, Nav, Navbar, NavDropdown, Row, Col} from "react-bootstrap";
import {Link} from "react-router-dom";
import {useTranslation} from "react-i18next";
import PaymentDialog from "../screens/forms/PaymentForm";
import {useActions} from "../../hook/useActions";
import Button from "react-bootstrap/Button";
import LanguageSwitcher from "./LanguageSwitcher";
import {useAuth} from "../../hook/useAuth";

const Navigation: React.FC = () => {
    const {t} = useTranslation();
    const {logout} = useActions();

    const {user} = useAuth()

    const onLogout = () => {
        logout()
    }

    return (
        <Navbar bg="dark" data-bs-theme="dark">
            <Container>
                <Navbar.Brand>Web Bank</Navbar.Brand>
                <Nav className="me-auto">
                    <Nav.Link as={Link} to="/home">{t('navigation.home')}</Nav.Link>
                    <NavDropdown title={t('navigation.actions')}>
                        <NavDropdown.Item as={Link} to="/currency">{t('navigation.currency')}</NavDropdown.Item>
                        <NavDropdown.Item as={Link} to="/descriptor">{t('navigation.descriptor')}</NavDropdown.Item>

                        {user?.role == 'ADMIN' &&
                            <>
                                <NavDropdown.Item as={Link}
                                                  to="/participants">{t('navigation.participants')}</NavDropdown.Item>

                                <NavDropdown.Item as={Link}
                                                  to="/incoming-message">{t('navigation.incoming_messages')}</NavDropdown.Item>

                                <NavDropdown.Item as={Link}
                                                  to="/outgoing-message">{t('navigation.outgoing_messages')}</NavDropdown.Item>

                                <NavDropdown.Item as={Link} to="/balances">{t('navigation.balances')}</NavDropdown.Item>

                                <NavDropdown.Item as={Link} to="/paydocs">{t('navigation.paydocs')}</NavDropdown.Item>
                            </>
                        }
                        <NavDropdown.Divider/>
                        <NavDropdown.Item as={Link} to="/report">{t('navigation.report')}</NavDropdown.Item>
                    </NavDropdown>
                </Nav>
                <Navbar.Collapse className="justify-content-end">
                    <Row xs="auto">
                        <Col xs="auto">
                            <LanguageSwitcher/>
                        </Col>
                        <Col xs="auto">
                            <PaymentDialog/>
                        </Col>
                        <Col xs="auto">
                            <Button variant="outline-danger" onClick={() => onLogout()}>{t('app.logout')}</Button>
                        </Col>
                    </Row>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    )
};

export default Navigation;