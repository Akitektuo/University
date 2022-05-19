import { observer } from "mobx-react";
import { FC, useContext, useEffect } from "react";
import { DataProviderContext } from "./data-provider-store";


export interface WithDataProvider {
}

function withDataProvider<T extends WithDataProvider>(WrappedComponent: FC<T>) {
    const ComponentWithDataProvider = (props: Omit<T, keyof WithDataProvider>) => {
        const dataProviderStore = useContext(DataProviderContext);

        useEffect(() => dataProviderStore.initialize(), [dataProviderStore]);

        return <WrappedComponent {...(props as T)} {...dataProviderStore} />
    }

    return observer(ComponentWithDataProvider);
}

export default withDataProvider;