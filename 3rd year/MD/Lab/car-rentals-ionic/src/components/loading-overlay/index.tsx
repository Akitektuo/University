import { IonSpinner } from "@ionic/react";
import { Backdrop } from "@mui/material";
import styles from "./loading-overlay.module.scss";

interface Props {
    show: boolean;
}

const LoadingOverlay = ({ show }: Props) => (
    <Backdrop className={styles.elevatedLoadingOverlay} open={show} >
        <IonSpinner />
    </Backdrop>
);

export default LoadingOverlay;
