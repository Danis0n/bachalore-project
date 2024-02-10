// LanguageSwitcher.tsx
import React, {useState} from 'react';
import { useTranslation } from 'react-i18next';
import {ToggleButton, ToggleButtonGroup} from "react-bootstrap";

const LanguageSwitcher: React.FC = () => {
    const { i18n } = useTranslation();

    const [value, setValue] = useState(i18n.language);
    const handleChange = (val: string) => {
        setValue(val);
        i18n.changeLanguage(val);
    }

    return (
        <ToggleButtonGroup type="radio" name="LanguageSwitcher" defaultValue={value} onChange={handleChange}>
            <ToggleButton variant="secondary" value={"ru"} id="lang-ru">RU</ToggleButton>
            <ToggleButton variant="secondary" value={"en"} id="lang-en">EN</ToggleButton>
        </ToggleButtonGroup>
    );
};

export default LanguageSwitcher;