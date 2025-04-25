### 1. Producer

The application or service that sends messages.

It publishes messages to an exchange in RabbitMQ.

### 2. Message

A unit of data, typically a JSON, string, or byte array.

It can include headers and properties along with the message body.

### 3. Exchange
The component that receives messages from producers and routes them to queues based on rules (bindings).

Types of exchanges:

Direct: routes messages to queues with an exact matching routing key.

Fanout: sends messages to all bound queues.

Topic: uses wildcards for routing keys, useful for pattern-based routing.

Headers: routes based on headers instead of routing keys.

### 4. Queue
A buffer that stores messages.

Consumers pull messages from queues.

Messages stay in the queue until a consumer processes and acknowledges them.

### 5. Binding
A rule that connects an exchange to a queue.

It defines how messages should be routed (based on routing key or headers).

### 🛠️ 6. Consumer
The application or service that receives messages from a queue.

Consumers can acknowledge messages after processing to remove them from the queue.

###  Acknowledgment (ACK)
A way to tell RabbitMQ that the message has been processed successfully.

If not acknowledged, RabbitMQ can requeue the message for redelivery.

### 8. Durability and Persistence
Durable Queues survive server restarts.

Persistent Messages are written to disk and not lost after a crash (if queue is durable too).

###  9. Prefetch & Fair Dispatch
Controls how many messages RabbitMQ sends to a consumer before it receives an ACK.

Helps load-balance work between consumers.