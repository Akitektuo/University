import { getWebSocketUrl } from "../../accessors/helper-functions";
import { Change, ChangeType, WebSocketListener } from "./types";

const WebSocketHandler = (
    createListener: WebSocketListener,
    updateListener: WebSocketListener,
    deleteListener: WebSocketListener
): () => void => {
    const webSocket = new WebSocket(getWebSocketUrl()); 

    const keepAlive = () => webSocket.send("");

    const mapListener = (type: ChangeType) => {
        switch (type) {
            case ChangeType.Create:
                return createListener;
            case ChangeType.Update:
                return updateListener;
            case ChangeType.Delete:
                return deleteListener;
        }
    }

    const callListener = ({ type, payload }: Change) => mapListener(type)(payload);

    webSocket.onmessage = ({ data }: MessageEvent<string>) => {
        keepAlive();
        callListener(JSON.parse(data) as Change);
    }

    return webSocket.close;
}

export default WebSocketHandler;