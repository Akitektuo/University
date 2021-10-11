import { IonContent, IonPage, IonTitle } from "@ionic/react";
import { Alert, AlertTitle, Button, TextField } from "@mui/material";
import classNames from "classnames";
import { observer } from "mobx-react";
import styles from "./register.module.scss";

const Register = () => {
    return (
        <IonPage>
            <IonContent fullscreen>
                <div className={styles.centeredContainer}>
                    <IonTitle className={classNames(styles.logInText, styles.largeText)}>
                        Log in
                    </IonTitle>
                    <TextField
                        label="Email"
                        type="email"
                        className={styles.inputEmail}
                        value={user.email}
                        onChange={e => setEmail(e.target.value)} />
                    <TextField
                        label="Password"
                        type="password"
                        className={styles.inputPassword}
                        value={user.password}
                        onChange={e => setPassword(e.target.value)} />
                    <TextField
                        label="Confirm password"
                        type="password"
                        className={styles.inputPassword}
                        value={user.password}
                        onChange={e => setPassword(e.target.value)} />
                    {errorMessage && (
                        <Alert
                            severity="error"
                            className={styles.alertContainer}>
                            <AlertTitle><strong>Error</strong></AlertTitle>
                            {errorMessage}
                        </Alert>
                    )}
                    <div className={classNames(styles.row)}>
                        <Button className={styles.registerButton}>Register</Button>
                        <Button
                            variant="contained"
                            color="secondary"
                            disabled={!user.email || !user.password}
                            onClick={() => login(authorizedStore)}>
                            Log in
                        </Button>
                    </div>
                </div>
            </IonContent>
        </IonPage>
    );
}

export default observer(Register);