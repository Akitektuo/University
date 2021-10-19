import { useEffect, useState } from "react";

const usePlayAnimation = <T>(value: T, condition: (value: T) => boolean = _ => true) => {
    const [play, setPlay] = useState(false);
    
    useEffect(() => {
        if (condition(value)) {
            setPlay(false);
            setTimeout(() => setPlay(true), 10);
        }
    }, [value]);

    return [ play ];
}

export default usePlayAnimation;