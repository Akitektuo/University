import { observer } from "mobx-react";
import { PropsWithChildren, useContext } from "react"
import { AuthorizedContext } from "./authorized-store"

export const Authorized = observer(({ children }: PropsWithChildren<any>) => {
    const { isAuthorized } = useContext(AuthorizedContext);

    return isAuthorized ? children : null;
});

export const NotAuthorized = observer(({ children }: PropsWithChildren<any>) => {
    const { isAuthorized } = useContext(AuthorizedContext);

    return isAuthorized ? null : children;
});