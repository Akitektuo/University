import java.io.Serializable

abstract class Message : Serializable

class SubscribeMessage(val variable: String, val rank: Int) : Message()

class UpdateMessage(val variable: String, val value: Int) : Message()

class CloseMessage : Message()