import {
    IonContent,
    IonPage,
    IonSpinner,
    IonTitle
} from "@ionic/react";
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
import NetworkStatusBar from "./components/network-status-bar";
import FilterBar from "./components/filter-bar";
import InfiniteScroll from "react-infinite-scroll-component";
import { getParkLocation } from "../../accessors/online/park-location-accessor";
import ParkLocationDialog from "./components/park-location-dialog";

const MainPage = ({ availableCars, relatedCars, hasMore, fetchRelatedCars }: WithDataProvider) => {
    const {
        selectedTab,
        carToEdit,
        showParkLocationDialog,

        setSelectedTab,
        showAddDialog,
        showEditDialog,
        closeAddDialog,
        openParkLocationDialog,
        closeParkLocationDialog,
        signOut
    } = useContext(MainPageContext);

    return (
        <IonPage>
            <IonContent fullscreen>
                <div className={styles.pageContainer}>
                    <Box sx={{ borderBottom: 1, borderColor: "divider"}}>
                        <div className={styles.header}>
                            <IconButton onClick={openParkLocationDialog}>
                                <InfoIcon color="primary" />
                            </IconButton>
                            <IonTitle className={styles.applicationTitle}>Car Rentals</IonTitle>
                            <IconButton onClick={signOut}>
                                <SignOutIcon color="primary" />
                            </IconButton>
                        </div>
                        <FilterBar show={selectedTab === 1} />
                        <Tabs
                            variant="fullWidth"
                            value={selectedTab}
                            onChange={(_, tab) => setSelectedTab(tab)}>
                                <Tab label="Available cars" value={0} />
                                <Tab label="My renting offers" value={1} />
                        </Tabs>
                    </Box>
                    <NetworkStatusBar />
                    <SwipeableViews
                        id="scrollable-div"
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
                            <InfiniteScroll
                                scrollableTarget="scrollable-div"
                                dataLength={relatedCars.length}
                                next={fetchRelatedCars}
                                loader={<div className={styles.center}><IonSpinner /></div>}
                                hasMore={hasMore}>
                                <CarList
                                    cars={relatedCars}
                                    onClick={showEditDialog} />
                            </InfiniteScroll>
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
                        onClose={closeAddDialog} />
                    <ParkLocationDialog
                        isOpen={showParkLocationDialog}
                        onClose={closeParkLocationDialog} />
                </div>
            </IonContent>
        </IonPage>
    );
}

export default withDataProvider(observer(MainPage));