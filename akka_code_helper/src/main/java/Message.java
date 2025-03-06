// Message class to define the communication between Actors
class Message {
    public final String sender;
    public final String receiver;

    public Message(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }
}
