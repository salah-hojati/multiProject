

### Example Scenario:

Let’s say you’re building an e-commerce site:

Producer: Order Service (sends a message when an order is placed).

Exchange: Routes based on order events.

Queue: emailQueue, inventoryQueue

Consumers: Email Service and Inventory Service read from their respective queues.