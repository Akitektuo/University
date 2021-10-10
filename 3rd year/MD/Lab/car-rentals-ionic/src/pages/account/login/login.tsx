import { IonContent, IonImg, IonPage, IonTitle } from "@ionic/react";
import styles from "./login.module.scss";
import carGif from "../../../assets/car.gif";
import classNames from "classnames";
import { Button, TextField } from "@mui/material";

const Login = () => {
    return (
        <IonPage>
            <IonContent fullscreen>
                <div className={styles.centeredContainer}>
                    <div className={classNames(styles.row, styles.largeText)}>
                        <IonImg src={carGif} className={styles.carGif} />
                        <IonTitle className={styles.applicationTitle}>Car Rentals</IonTitle>
                    </div>
                    <IonTitle className={classNames(styles.logInText, styles.largeText)}>
                        Log in
                    </IonTitle>
                    <TextField
                        label="Email"
                        type="email"
                        className={styles.inputEmail} />
                    <TextField
                        label="Password"
                        type="password"
                        className={styles.inputPassword} />
                    <div className={classNames(styles.row)}>
                        <Button className={styles.registerButton}>Register</Button>
                        <Button
                            variant="contained"
                            color="secondary">
                            Log in
                        </Button>
                    </div>
                </div>
            </IonContent>
        </IonPage>
    );
}

export default Login;