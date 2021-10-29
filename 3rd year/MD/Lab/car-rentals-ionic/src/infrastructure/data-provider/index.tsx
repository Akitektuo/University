import { observer } from "mobx-react";
import { FC, useContext, useEffect } from "react";
import { Car } from "../../accessors/types";
import { DataProviderContext } from "./data-provider-store";

type CarParameterFunction = (car: Car) => Promise<void>;

export interface WithDataProvider {
    availableCars: Car[];
    relatedCars: Car[];
    hasMore: boolean;
    search: string;
    automaticFilter: boolean | null;
    addCar: CarParameterFunction;
    updateCar: CarParameterFunction;
    deleteCar: CarParameterFunction;
    fetchRelatedCars: () => Promise<void>;
    setSearch: (search: string) => void;
    setAutomaticFilter: (isAutomatic: boolean | null) => void;
}

function withDataProvider<T extends WithDataProvider>(WrappedComponent: FC<T>) {
    const ComponentWithDataProvider = (props: Omit<T, keyof WithDataProvider>) => {
        const dataProviderStore = useContext(DataProviderContext);

        useEffect(() => {
            dataProviderStore.initialize();
            
            return dataProviderStore.unsubscribeToChanges();
        }, [dataProviderStore]);

        return <WrappedComponent {...(props as T)} {...dataProviderStore} />
    }

    return observer(ComponentWithDataProvider);
}

export default withDataProvider;