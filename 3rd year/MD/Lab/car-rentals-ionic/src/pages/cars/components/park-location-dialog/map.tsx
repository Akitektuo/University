import { withGoogleMap, GoogleMap, Marker, withScriptjs } from "react-google-maps";
import { ParkLocation } from "../../../../accessors/types";

const Map = ({ latitude: lat, longitude: lng }: ParkLocation) => (
    <GoogleMap 
        defaultCenter={{ lat, lng }}>
        <Marker position={{ lat, lng }} />
    </GoogleMap>
);

export default withScriptjs(withGoogleMap(Map));