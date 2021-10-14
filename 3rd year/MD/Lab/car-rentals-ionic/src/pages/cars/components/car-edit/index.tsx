import {
    AppBar,
    Button,
    Checkbox,
    Dialog,
    Fab,
    FormControl,
    FormControlLabel,
    IconButton,
    InputLabel,
    MenuItem,
    Select,
    Slide,
    TextField,
    Toolbar,
    Typography
} from "@mui/material";
import { forwardRef, useContext, useEffect } from "react";
import { withDataProvider, WithDataProvider } from "../../../../infrastructure";
import CloseIcon from "@mui/icons-material/CloseSharp";
import CheckIcon from "@mui/icons-material/CheckSharp";
import UploadIcon from "@mui/icons-material/UploadSharp";
import CameraIcon from "@mui/icons-material/CameraAltSharp";
import DeleteIcon from "@mui/icons-material/DeleteOutlineSharp";
import { Car } from "../../../../accessors/types";
import styles from "./car-edit.module.scss";
import { CarEditContext } from "./car-edit-store";
import { observer } from "mobx-react";
import { ConfirmationDialog } from "../../../../components";

interface Props extends WithDataProvider {
    initialCar: Car | null;
    onClose: () => void;
}

const Transition = forwardRef(function Transition(props: any, ref) {
    return <Slide direction="up" ref={ref} {...props} />;
});

const CarEdit = ({ initialCar, onClose }: Props) => {
    const {
        car,
        isAdd,
        showCloseConfirmation,
        showDeleteConfirmation,

        initializeCar,
        setBrand,
        setModel,
        setFabricationYear,
        setColor,
        takePicture,
        setAutomatic,
        canSave,
        saveCar,
        deleteCar,
        setCloseConfirmation,
        setDeleteConfirmation
    } = useContext(CarEditContext);

    useEffect(() => initializeCar(initialCar),
        [initializeCar, initialCar]);

    const handleSave = async () => {
        if (await saveCar()) {
            onClose();
        }
    }

    const handleDelete = async () => {
        if (await deleteCar()) {
            onClose();
        }
    }

    return (
        <Dialog
            fullScreen
            open={initialCar !== null}
            onClose={() => setCloseConfirmation(true)}
            TransitionComponent={Transition}>
            <AppBar
                sx={{ position: "relative" }}
                color="inherit">
                <Toolbar>
                    <IconButton
                        onClick={() => setCloseConfirmation(true)}
                        color="primary">
                        <CloseIcon />
                    </IconButton>
                    <Typography
                        sx={{
                            ml: 2,
                            flex: 1
                        }}
                        variant="h6"
                        color="black"
                        component="div">
                        Add new car
                    </Typography>
                    <IconButton
                        onClick={handleSave}
                        color="secondary"
                        disabled={!canSave()}>
                        <CheckIcon />
                    </IconButton>
                </Toolbar>
            </AppBar>
            <div className={styles.container}>
                <TextField
                    label="Brand"
                    required
                    className={styles.input}
                    value={car.brand}
                    onChange={e => setBrand(e.target.value)} />
                <TextField
                    label="Model"
                    required
                    className={styles.input}
                    value={car.model}
                    onChange={e => setModel(e.target.value)} />
                <TextField
                    label="Fabrication year"
                    type="number"
                    required
                    inputProps={{
                        min: 1970,
                        max: 2021
                    }}
                    className={styles.input}
                    value={car.fabricationYear}
                    onChange={e => setFabricationYear(e.target.value)} />
                <FormControl fullWidth className={styles.input}>
                    <InputLabel id="car-edit-select-color">Color</InputLabel>
                    <Select
                        labelId="car-edit-select-color"
                        value={car.color}
                        label="Color"
                        required
                        onChange={e => setColor(e.target.value)}>
                        <MenuItem value="white">White</MenuItem>
                        <MenuItem value="black">Black</MenuItem>
                        <MenuItem value="gray">Gray</MenuItem>
                        <MenuItem value="silver">Silver</MenuItem>
                        <MenuItem value="red">Red</MenuItem>
                        <MenuItem value="blue">Blue</MenuItem>
                        <MenuItem value="brown">Brown</MenuItem>
                        <MenuItem value="green">Green</MenuItem>
                        <MenuItem value="beige">Beige</MenuItem>
                        <MenuItem value="orange">Orange</MenuItem>
                        <MenuItem value="gold">Gold</MenuItem>
                        <MenuItem value="yellow">Yellow</MenuItem>
                        <MenuItem value="purple">Purple</MenuItem>
                    </Select>
                </FormControl>
                <div className={styles.row}>
                    <Button
                        color="secondary"
                        variant="contained"
                        startIcon={<UploadIcon />}>
                        Upload picture
                    </Button>
                    <Button
                        color="secondary"
                        variant="contained"
                        onClick={takePicture}
                        startIcon={<CameraIcon />}>
                        Take picture
                    </Button>
                </div>
                <FormControlLabel
                    label="Is automatic"
                    control={
                        <Checkbox
                            color="secondary"
                            checked={car.isAutomatic}
                            onChange={e => setAutomatic(e.target.checked)} />
                    } />
            </div>
            {!isAdd && (
                <Fab
                    className={styles.deleteButton}
                    onClick={() => setDeleteConfirmation(true)}>
                    <DeleteIcon />
                </Fab>
            )}
            <ConfirmationDialog
                isOpen={showCloseConfirmation}
                onCancel={() => setCloseConfirmation(false)}
                onConfirm={() => {
                    onClose();
                    setCloseConfirmation(false);
                }}
                title="Closing the edit form"
                message="Are you sure you want to close the form? All the introduced data will be lost." />
            <ConfirmationDialog
                isOpen={showDeleteConfirmation}
                onCancel={() => setDeleteConfirmation(false)}
                onConfirm={() => {
                    handleDelete();
                    setDeleteConfirmation(false);
                }}
                title={`Deleting car ${initialCar?.brand} ${initialCar?.model}`}
                message="Are you sure you want to delete this car? The action cannot be undone and the data will be lost." />
        </Dialog>
    );
}

export default withDataProvider(observer(CarEdit));