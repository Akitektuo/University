import { IonContent, IonImg, IonPage, IonTitle } from "@ionic/react";
import { Fab, IconButton, Tab, Tabs } from "@mui/material";
import { Box } from "@mui/system";
import { useContext } from "react";
import SwipeableViews from "react-swipeable-views";
import AddIcon from '@mui/icons-material/AddSharp';
import styles from "./main-page.module.scss";
import { ToastService, WithDataProvider, withDataProvider } from "../../infrastructure";
import CarList from "./components/car-list";
import { MainPageContext } from "./main-page-store";
import CarEdit from "./components/car-edit";
import { observer } from "mobx-react";
import SignOutIcon from '@mui/icons-material/NoAccountsSharp';
import InfoIcon from '@mui/icons-material/InfoSharp';

const MainPage = ({ availableCars, relatedCars }: WithDataProvider) => {
    const {
        selectedTab,
        carToEdit,

        setSelectedTab,
        showAddDialog,
        showEditDialog,
        closeDialog,
        signOut
    } = useContext(MainPageContext);

    return (
        <IonPage>
            <IonContent fullscreen>
                <div className={styles.pageContainer}>
                    <Box sx={{ borderBottom: 1, borderColor: "divider"}}>
                        <div className={styles.header}>
                            <IconButton>
                                <InfoIcon color="primary" />
                            </IconButton>
                            <IonTitle className={styles.applicationTitle}>Car Rentals</IonTitle>
                            <IconButton onClick={signOut}>
                                <SignOutIcon color="primary" />
                            </IconButton>
                        </div>
                        <Tabs
                            variant="fullWidth"
                            value={selectedTab}
                            onChange={(_, tab) => setSelectedTab(tab)}>
                                <Tab label="Available cars" value={0} />
                                <Tab label="My renting offers" value={1} />
                        </Tabs>
                    </Box>
                    <SwipeableViews
                        axis="x"
                        index={selectedTab}
                        onChangeIndex={setSelectedTab}>
                        <div
                            className={styles.tabPanel}
                            role="tabpanel"
                            hidden={selectedTab !== 0}>
                            <CarList 
                                cars={availableCars} />
                        </div>
                        <div
                            className={styles.tabPanel}
                            role="tabpanel"
                            hidden={selectedTab !== 1}>
                            <CarList
                                cars={relatedCars}
                                onClick={showEditDialog} />
                        </div>
                    </SwipeableViews>
                    <Fab
                        className={styles.addButton}
                        onClick={() => showAddDialog()}>
                        <AddIcon color="primary" />
                    </Fab>
                    <ToastService />
                    <CarEdit
                        initialCar={carToEdit}
                        onClose={closeDialog} />
                </div>
            </IonContent>
        </IonPage>
    );
}

export default withDataProvider(observer(MainPage));