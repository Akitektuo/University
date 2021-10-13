import { IonCard } from "@ionic/react";
import { Car } from "../../../../accessors/types";
import { ByteImage } from "../../../../components";
import styles from "./car-list.module.scss";

interface Props {
    cars: Car[]
}

const CarList = ({ cars }: Props) => {
    return <>
        {cars?.length ? cars.map(car => (
            <IonCard key={car.id}>
                <ByteImage byteSrc={car.image} />
                <div></div>
            </IonCard>
        )) : (
            <div className={styles.notFound}>No cars found...</div>
        )}
    </>
}

export default CarList;