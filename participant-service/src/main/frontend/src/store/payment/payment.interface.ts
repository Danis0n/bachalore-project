export interface TransferForm {
    bicCd: string;
    codeCd: string
    codeDb: string
    bicDb: string;
    value: number;
}

export interface AccountForm {
    bic: string,
    currency: string
}

export interface MoneyInput {
    bic: string
    accountCode: string
    value: number
}