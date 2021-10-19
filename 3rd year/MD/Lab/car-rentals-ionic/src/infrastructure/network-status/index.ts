import { useContext, useEffect } from "react";
import { NetworkStatusContext } from "./network-status-store";

const useNetworkStatus = () => {
    const { initialize, isConnected } = useContext(NetworkStatusContext);

    useEffect(() => {
        initialize();
    }, [initialize]);

    return { isConnected };
}

export default useNetworkStatus;