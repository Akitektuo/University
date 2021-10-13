import { IonImg } from "@ionic/react";

interface Props {
    byteSrc: string;
}

const ByteImage = ({ byteSrc }: Props) => (
    <IonImg src={`data:image/png;base64,${byteSrc}`} draggable="false" />
);

export default ByteImage;