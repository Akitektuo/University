import { ParkLocation } from "../../../../accessors/types";

const EmbeddedMap = ({ latitude, longitude }: ParkLocation) => (
    <iframe
        src={`https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2732.455005383141!2d${latitude}!3d${longitude}!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x47490c6e7cd63397%3A0x94c326309218b16e!2sCluj-Napoca%20400000!5e0!3m2!1sen!2sro!4v1636824368637!5m2!1sen!2sro`}
        width="100%"
        height="450"
        style={{border: 0}}
        loading="lazy" />
);

export default EmbeddedMap;