import {Navigate} from "react-router-dom";
import {useAuth} from "../hook/useAuth";
import Layout from "../components/ui/Layout";

const PrivateRoute = () => {
    const {user, isLoading}  = useAuth()

    if (isLoading) {
        return <div>Checking auth...</div>;
    }

    if (user != null) {
        return <Layout/>
    } else {
        return <Navigate to="/" />;
    }
};

export default PrivateRoute;