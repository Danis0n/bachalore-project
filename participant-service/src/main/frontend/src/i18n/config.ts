import i18next from 'i18next';
import {initReactI18next} from 'react-i18next';
import LanguageDetector from 'i18next-browser-languagedetector';
import en from './en/translation.json';
import ru from './ru/translation.json';

i18next
    .use(LanguageDetector)
    .use(initReactI18next)
    .init({
        detection: {
            order: ['localStorage', 'cookie'],
            lookupCookie: 'USER_LANG',
            lookupLocalStorage: 'UserLanguage',
        },
        fallbackLng: 'en',
        resources: {
            en: {
                translation: en
            },
            ru: {
                translation: ru
            }
        }
    });