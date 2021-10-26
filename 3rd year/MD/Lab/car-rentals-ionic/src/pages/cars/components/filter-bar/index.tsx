import { MenuItem, Select, TextField } from "@mui/material";
import classNames from "classnames";
import { observer } from "mobx-react";
import { WithDataProvider, withDataProvider } from "../../../../infrastructure";
import styles from "./filter-bar.module.scss";

interface Props extends WithDataProvider {
    show: boolean;
}

const FilterBar = ({ show, search, automaticFilter, setSearch, setAutomaticFilter }: Props) => {
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
        <div className={classNames(styles.filterBar, {
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