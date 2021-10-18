import { IonCard } from "@ionic/react";
import { Car } from "../../../../accessors/types";
import { ByteImage } from "../../../../components";
import styles from "./car-list.module.scss";

interface Props {
    cars: Car[]
    onClick?: (car: Car) => void;
}

const CarList = ({ cars, onClick }: Props) => {
    return <>
        {cars?.length ? cars.map(car => (
            <IonCard
                key={car.id}
                className={styles.itemContainer}
                onClick={() => onClick && onClick(car)}>
                <ByteImage byteSrc={car.image} />
                <div className={styles.itemDetails}>
                    <div className={styles.row}>
                        <div>
                            <strong>Brand:</strong> {car.brand}
                        </div>
                        <div>
                            <strong>Model:</strong> {car.model}
                        </div>
                    </div>
                    <div className={styles.row}>
                        <div>
                            <strong>Id:</strong> {car.id}
                        </div>
                        <div>
                            <strong>Color:</strong> {car.color}
                        </div>
                    </div>
                    <div className={styles.row}>
                        <div>
                            <strong>Fabrication year:</strong> {car.fabricationYear}
                        </div>
                        <div>
                            <strong>Is automatic?</strong> {car.isAutomatic ? "Yes" : "No"}
                        </div>
                    </div>
                </div>
            </IonCard>
        )) : (
            <div className={styles.notFound}>No cars found...</div>
        )}
    </>
}

export default CarList;