import { IonContent, IonPage, IonTitle } from "@ionic/react";
import { Fab, Tab, Tabs } from "@mui/material";
import { Box } from "@mui/system";
import { useState } from "react";
import SwipeableViews from "react-swipeable-views";
import AddIcon from '@mui/icons-material/AddSharp';
import styles from "./main-page.module.scss";
import { ToastService, WithDataProvider, withDataProvider } from "../../infrastructure";
import CarList from "./components/car-list";

const MainPage = ({ availableCars, relatedCars }: WithDataProvider) => {
    const [selectedTab, setSelectedTab] = useState(0);

    return (
        <IonPage>
            <IonContent fullscreen>
                <div className={styles.pageContainer}>
                    <Box sx={{ borderBottom: 1, borderColor: "divider"}}>
                        <IonTitle className={styles.applicationTitle}>Car Rentals</IonTitle>
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
                            <CarList cars={availableCars} />
                        </div>
                        <div
                            className={styles.tabPanel}
                            role="tabpanel"
                            hidden={selectedTab !== 1}>
                            <CarList cars={relatedCars} />
                        </div>
                    </SwipeableViews>
                    <Fab className={styles.addButton}>
                        <AddIcon color="primary" />
                    </Fab>
                    <ToastService />
                </div>
            </IonContent>
        </IonPage>
    );
}

export default withDataProvider(MainPage);