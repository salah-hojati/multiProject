package snippit;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.DockerClientBuilder;
import java.util.List;

public class createDockerUbuntu {
    public static void main(String[] args) throws InterruptedException {
        //DockerClient dockerClient = DockerClientBuilder.getInstance().build();
        DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://localhost:2375").build();
/**
 *  Solution 1: Set Docker to Use TCP (instead of npipe)
 *
 *     Edit Docker Daemon Configuration: Docker on Windows can be configured to expose the Docker API over TCP instead of using the named pipe. This allows your Java client to connect via tcp://.
 *
 *     Change Docker Daemon Settings:
 *         Open the Docker Desktop settings.
 *         Go to Settings > General.
 *         Enable Expose daemon on tcp://localhost:2375 without TLS.
 *
 *     Restart Docker: After changing the configuration, restart Docker Desktop for the changes to take effect.
 *
 *     Update Your Java Client to Connect via TCP: Instead of using the default named pipe protocol, use TCP in your Java code.
 */

        // List Running Containers
        List<Container> containers = dockerClient.listContainersCmd().exec();
        for (Container container : containers) {
            System.out.println("Container ID: " + container.getId());
        }

        // Step 1: Pull the image (if not already present)
        dockerClient.pullImageCmd("ubuntu:latest").start().awaitCompletion();

        // Step 2: Create a container from the image
        CreateContainerResponse container = dockerClient.createContainerCmd("ubuntu:latest")
                .withCmd("echo", "Hello from Ubuntu!")
                .exec();

/*        // Create & Start a Container
        dockerClient.createContainerCmd("ubuntu")
                .withName("my_empty_container")
                .exec();*/
        // Step 3: Start the container
        dockerClient.startContainerCmd(container.getId()).exec();

        System.out.println("Container started with ID: " + container.getId());
        


    }
}
