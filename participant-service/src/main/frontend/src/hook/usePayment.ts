import {useTypedSelector} from "./useTypedSelector";

export const usePayment = () => useTypedSelector(state => state.payment)
