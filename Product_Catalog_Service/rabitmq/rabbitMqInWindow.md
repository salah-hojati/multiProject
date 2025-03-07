
To install RabbitMQ using Chocolatey, run the following command from the command line or from PowerShell:

        choco install rabbitmq

# CLI Tools


lists commands provided by rabbitmqctl.bat

rabbitmqctl.bat help

lists commands provided by rabbitmq-diagnostics.bat

rabbitmq-diagnostics.bat help

you guessed it

rabbitmq-plugins.bat help

*********************


To learn about a specific command, pass its name as an argument to help:

rabbitmqctl.bat help add_user

****

# Cookie File Location
On Windows, the cookie file location depends on whether the HOMEDRIVE and HOMEPATH environment variables are set.

If RabbitMQ is installed using a non-administrative account, a shared secret file used by nodes and CLI tools will not be placed into a correct location, leading to authentication failures when rabbitmqctl.bat and other CLI tools are used.

One of these options can be used to [mitigate]():

Re-install RabbitMQ using an administrative user
Copy the file .erlang.cookie manually from %SystemRoot% or %SystemRoot%\system32\config\systemprofile to %HOMEDRIVE%%HOMEPATH%.
Node Configuration
The service starts using its default settings, listening for connections on default interfaces and ports.
****

Node configuration is primarily done using a configuration file.
A number of available environment variables can be used to control node's data location, configuration file path and so on.

This is covered in more detail in the Configuration guide
*****************
# Environment Variable Changes on Windows
Important: after setting environment variables, it is necessary to re-install the Windows service. Restarting the service will not be sufficient.

Managing a RabbitMQ Node
Managing the Service
Links to RabbitMQ directories can be found in the Start Menu.



There is also a link to a command prompt window that will start in the sbin dir, in the Start Menu. This is the most convenient way to run the command line tools.

Note that CLI tools will have to authenticate to the target RabbitMQ node.
***

# Stopping a Node
To stop the broker or check its status, use rabbitmqctl.bat in sbin (as an administrator).

    rabbitmqctl.bat stop

Checking Node Status
The following CLI command runs a basic health check and displays some information about the node if it is running.

# A basic health check of both the node and CLI tool connectivity/authentication
rabbitmqctl.bat status

For it to work, two conditions must be true:

The node must be running
rabbitmqctl.bat must be able to authenticate with the node
See the CLI tools section and the Monitoring and Health Checks guide to learn more.

Log Files and Management
Server logs are critically important in troubleshooting and root cause analysis. See Logging and File and Directory Location guides to learn about log file location, log rotation and more.
***
# Firewalls and Security Tools
Firewalls and security tools can prevent RabbitMQ Windows service and CLI tools from operating correctly.

Such tools should be configured to whitelist access to ports used by RabbitMQ.
***
# Default User Access
The broker creates a user guest with password guest. 
Unconfigured clients will in general use these credentials. By default, these credentials can only be used when connecting to the broker as localhost so you will need to take action before connecting from any other machine.

See the documentation on access control for information on how to create more users and delete the guest user.
***
# Port Access
#### _RabbitMQ nodes bind to ports (open server TCP sockets) in order to accept client and CLI tool connections._ 
#### _Other processes and tools such as anti-virus software may prevent RabbitMQ from binding to a port._
#### _When that happens, the node will fail to start._

CLI tools, client libraries and RabbitMQ nodes also open connections (client TCP sockets)
Firewalls can prevent nodes and CLI tools from communicating with each other. Make sure the following ports are accessible:

     4369: epmd, a peer discovery service used by RabbitMQ nodes and CLI tools
    5672, 5671: used by AMQP 0-9-1 and 1.0 clients without and with TLS
    25672: used for inter-node and CLI tools communication (Erlang distribution server port) and is allocated from a dynamic range (limited to a single port by default, computed as AMQP port + 20000). Unless external connections on these ports are really necessary (e.g. the cluster uses federation or CLI tools are used on machines outside the subnet), these ports should not be publicly exposed. See networking guide for details.
    35672-35682: used by CLI tools (Erlang distribution client ports) for communication with nodes and is allocated from a dynamic range (computed as server distribution port + 10000 through server distribution port + 10010). See networking guide for details.
    15672: HTTP API clients, management UI and rabbitmqadmin (only if the management plugin is enabled)
    61613, 61614: STOMP clients without and with TLS (only if the STOMP plugin is enabled)
    1883, 8883: MQTT clients without and with TLS, if the MQTT plugin is enabled
    15674: STOMP-over-WebSockets clients (only if the Web STOMP plugin is enabled)
    15675: MQTT-over-WebSockets clients (only if the Web MQTT plugin is enabled)


It is possible to configure RabbitMQ to use different ports and specific network interfaces.