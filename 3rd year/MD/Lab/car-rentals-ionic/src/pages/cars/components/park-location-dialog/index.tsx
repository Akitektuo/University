import { AppBar, Button, Dialog, IconButton, Slide, Toolbar, Typography } from "@mui/material";
import { forwardRef, useContext, useEffect } from "react";
import CloseIcon from "@mui/icons-material/CloseSharp";
import Map from "./map";
import styles from "./park-location-dialog.module.scss";
import { ParkLocationDialogContext } from "./park-location-dialog-store";
import { IonSpinner } from "@ionic/react";
import { observer } from "mobx-react";
import { toJS } from "mobx";

interface Props {
    isOpen: boolean;
    onClose: () => void;
}

const Transition = forwardRef(function Transition(props: any, ref) {
    return <Slide direction="right" ref={ref} {...props} />;
});

const ParkLocationDialog = ({ isOpen, onClose }: Props) => {
    const {
        parkLocation,
        loadParkLocation,
        reset,
        setParkLocation
    } = useContext(ParkLocationDialogContext);

    useEffect(() => {
        if (isOpen) {
            loadParkLocation();
    
            return reset;
        }
    }, [loadParkLocation, reset, isOpen]);

    console.log(toJS(parkLocation));

    return (
        <Dialog
            fullScreen
            open={isOpen}
            onClose={onClose}
            TransitionComponent={Transition}>
            <AppBar
                sx={{ position: "relative" }}
                color="inherit">
                <Toolbar>
                    <Typography
                        sx={{
                            ml: 2,
                            flex: 1
                        }}
                        variant="h6"
                        color="black"
                        fontWeight="bold"
                        component="div">
                        Your car park location
                    </Typography>
                    <IconButton
                        onClick={onClose}
                        color="primary">
                        <CloseIcon />
                    </IconButton>
                </Toolbar>
            </AppBar>
            <div className={styles.container}>
                {parkLocation && (
                    <Map 
                        googleMapURL="https://maps.googleapis.com/maps/api/js?key=AIzaSyC4R6AN7SmujjPUIGKdyao2Kqitzr1kiRg&v=3.exp&libraries=geometry,drawing,places"
                        mapElement={<div style={{ height: `100%`, width: "100%" }} />}
                        containerElement={<div style={{ height: `400px`, width: "100%" }} />}   
                        loadingElement={<IonSpinner />} 
                        {...parkLocation} />
                )}
                <Button 
                    color="secondary"
                    variant="outlined"
                    onClick={setParkLocation}>
                    Set current location
                </Button>
            </div>
        </Dialog>
    );
}

export default observer(ParkLocationDialog);