import { Redirect, Route } from "react-router-dom";
import { IonApp, IonRouterOutlet } from "@ionic/react";
import { IonReactRouter } from "@ionic/react-router";
import Home from "./pages/Home";

/* Core CSS required for Ionic components to work properly */
import "@ionic/react/css/core.css";

/* Basic CSS for apps built with Ionic */
import "@ionic/react/css/normalize.css";
import "@ionic/react/css/structure.css";
import "@ionic/react/css/typography.css";

/* Optional CSS utils that can be commented out */
import "@ionic/react/css/padding.css";
import "@ionic/react/css/float-elements.css";
import "@ionic/react/css/text-alignment.css";
import "@ionic/react/css/text-transformation.css";
import "@ionic/react/css/flex-utils.css";
import "@ionic/react/css/display.css";

/* Theme variables */
import "./theme/variables.css";
import Login from "./pages/account/login/login";
import { createTheme, ThemeProvider } from "@mui/material";

const App: React.FC = () => {
	const theme = createTheme({
		palette: {
			primary: {
			  main: "#111",
			},
			secondary: {
			  main: '#82b1ff',
			},
		},
		typography: {
			fontFamily: "Poppins, sans-serif"
		}
	});

	return (
		<IonApp>
			<ThemeProvider theme={theme}>
				<IonReactRouter>
					<IonRouterOutlet>
						<Route exact path="/login">
							<Login />
						</Route>
						<Route exact path="/register">
							<Login />
						</Route>
						<Route path="/">
							<Redirect to="/login" />
						</Route>
					</IonRouterOutlet>
				</IonReactRouter>
			</ThemeProvider>
		</IonApp>
	);
}

export default App;
