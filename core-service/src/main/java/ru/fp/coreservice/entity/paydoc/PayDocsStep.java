package ru.fp.coreservice.entity.paydoc;

import lombok.Getter;

@Getter
public enum PayDocsStep {
    CREATE_PAY_DOC("F") {
        @Override
        public PayDocsStep nextStep() {
            return CREATE_GUID;
        }
    },
    CREATE_GUID("P") {
        @Override
        public PayDocsStep nextStep() {
            return WAITING_FOR_SETTLEMENT;
        }
    },
    WAITING_FOR_SETTLEMENT("E") {
        @Override
        public PayDocsStep nextStep() {
            return NOTIFICATION;
        }
    },
    NOTIFICATION("D") {
        @Override
        public PayDocsStep nextStep() {
            return ARCHIVING;
        }
    },
    ARCHIVING("A") {
        @Override
        public PayDocsStep nextStep() {
            return null;
        }
    };

    private final String name;

    public abstract PayDocsStep nextStep();

    PayDocsStep(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}