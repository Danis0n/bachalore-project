import * as userActions from './user/user.actions'
import * as paymentActions from './payment/payment.actions'
import * as balanceActions from './balance/balance.actions'

export const rootActions = {
    ...userActions,
    ...paymentActions,
    ...balanceActions
}