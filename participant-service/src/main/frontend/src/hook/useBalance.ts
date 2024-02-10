import {useTypedSelector} from "./useTypedSelector";

export const useBalance = () => useTypedSelector(state => state.balance)
