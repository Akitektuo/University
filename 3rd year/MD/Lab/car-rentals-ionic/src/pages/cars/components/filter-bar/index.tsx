import { createAnimation } from "@ionic/react";
import { MenuItem, Select, TextField } from "@mui/material";
import classNames from "classnames";
import { observer } from "mobx-react";
import { useEffect, useRef, useState } from "react";
import { WithDataProvider, withDataProvider } from "../../../../infrastructure";
import styles from "./filter-bar.module.scss";

interface Props extends WithDataProvider {
    show: boolean;
}

const FilterBar = ({ show, search, automaticFilter, setSearch, setAutomaticFilter }: Props) => {
    const elementReference = useRef<HTMLDivElement>(null);
    const [isInitialized, setInitialized] = useState(false);

    useEffect(() => {
        const playAnimation = (reversed = false) => {
            if (!elementReference.current)
                return;

            if (!isInitialized)
                return setInitialized(true);
        
            const animation = createAnimation()
                .addElement(elementReference.current)
                .keyframes([{
                    offset: 0,
                    maxHeight: 0,
                    opacity: 0
                }, {
                    offset: 0.5,
                    maxHeight: "5rem",
                    opacity: 1
                }, {
                    offset: 1,
                    maxHeight: "10rem",
                    opacity: 1
                }]).duration(reversed ? 700 : 1000)
                .easing("ease-out");
            
            if (reversed)
                animation.direction("reverse");
        
            animation.play();
        }

        playAnimation(!show);
    }, [show]);

    const handleAutomaticFilter = (value: string) => {
        if (value === "null") {
            return setAutomaticFilter(null);
        }
        setAutomaticFilter(value === "true");
    }

    const getSelectedValue = () => {
        if (automaticFilter === null) {
            return "null";
        }
        return automaticFilter.toString();
    }

    return (
        <div 
            ref={elementReference}
            className={classNames(styles.filterBar, {
                [styles.show]: show,
            })}>
            <TextField
                label="Search"
                className={styles.input}
                value={search}
                variant="filled"
                size="small"
                onChange={e => setSearch(e.target.value)} />
            <Select
                value={getSelectedValue()}
                size="small"
                onChange={e => handleAutomaticFilter(e.target.value as string)}>
                <MenuItem value="null">Show all</MenuItem>
                <MenuItem value="true">Show automatic only</MenuItem>
                <MenuItem value="false">Show manual only</MenuItem>
            </Select>
        </div>
    );
}

export default withDataProvider(observer(FilterBar));