import classNames from "classnames";
import { observer } from "mobx-react";
import { useNetworkStatus } from "../../../../infrastructure";
import usePlayAnimation from "../../../../shared/hooks/play-animation-hook";
import styles from "./network-status-bar.module.scss";

const NetworkStatusBar = () => {
    const { isConnected } = useNetworkStatus();
    const [ playShowAnimation ] = usePlayAnimation(isConnected);

    const getConnectionStatus = () => {
        if (!!isConnected !== isConnected) {
            return "Network status unknown";
        }

        return isConnected ? "Connected to the internet!" : "No internet connection!"
    }

    return (
        <div className={classNames(styles.barContainer, {
            [styles.show]: playShowAnimation,
            [styles.connected]: isConnected
        })}>
            <div className={styles.text}>{getConnectionStatus()}</div>
        </div>
    );
}

export default observer(NetworkStatusBar);