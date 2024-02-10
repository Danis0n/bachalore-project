package ru.fp.coreservice.entity.transaction;

import lombok.Getter;

@Getter
public enum TransactionStatus {
    READY("R") {
        @Override
        public TransactionStatus nextStep() {
            return ACTIVATED;
        }
    },
    ACTIVATED("E") {
        @Override
        public TransactionStatus nextStep() {
            return EFFECTED;
        }
    },
    QUEUED("Q") {
        @Override
        public TransactionStatus nextStep() {
            return EFFECTED;
        }
    },
    EFFECTED("F") {
        @Override
        public TransactionStatus nextStep() {
            return EFFECTED;
        }
    },
    REJECTED("X") {
        @Override
        public TransactionStatus nextStep() {
            return REJECTED;
        }
    };

    private final String name;

    public abstract TransactionStatus nextStep();

    TransactionStatus(String name) {
        this.name = name;
    }

}
