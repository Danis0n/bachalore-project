export default interface CorePayDoc {
    guid: string
    debitAcc: string
    creditAcc: string
    amount: number
    step: string
    valueDate: string
    participantBic: string
    startTime: string
    finishTime: string
    currencyCode: string
}