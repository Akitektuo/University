import { Backdrop, CircularProgress } from "@mui/material";
import { observer } from "mobx-react";
import { useContext } from "react";
import { LoadingOverlayContext } from "./loading-overlay-store";
import styles from "./loading-overlay.module.scss";

const LoadingOverlay = () => {
    const { activeLoadings } = useContext(LoadingOverlayContext);

    return (
        <Backdrop className={styles.elevatedLoadingOverlay} open={!!activeLoadings}>
            <CircularProgress />
        </Backdrop>
    );
}

export default observer(LoadingOverlay);