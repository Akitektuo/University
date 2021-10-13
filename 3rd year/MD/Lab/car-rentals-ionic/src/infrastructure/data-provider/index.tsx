import { observer } from "mobx-react";
import { FC, useContext, useEffect } from "react";
import { Car } from "../../accessors/types";
import { DataProviderContext } from "./data-provider-store";

type CarParameterFunction = (car: Car) => Promise<void>;

export interface WithDataProvider {
    availableCars: Car[];
    relatedCars: Car[];
    addCar: CarParameterFunction;
    updateCar: CarParameterFunction;
    deleteCar: CarParameterFunction;
}

function withDataProvider<T extends WithDataProvider>(WrappedComponent: FC<T>) {
    const ComponentWithDataProvider = (props: Omit<T, keyof WithDataProvider>) => {
        const dataProviderStore = useContext(DataProviderContext);

        useEffect(() => {
            return dataProviderStore.initialize();
        }, [dataProviderStore]);

        return <WrappedComponent {...(props as T)} {...dataProviderStore} />
    }

    return observer(ComponentWithDataProvider);
}

export default withDataProvider;