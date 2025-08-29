package snippit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DockerManager {

    // Method to run shell commands
    private static void runCommand(String command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        // Read output
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        process.waitFor();
    }

    public static void main(String[] args) {
        try {
            // Step 1: Pull Ubuntu Image
            System.out.println("Pulling Ubuntu Image...");
            runCommand("docker pull ubuntu");

            // Step 2: Run Empty Container
            System.out.println("Creating an Empty Container...");
            runCommand("docker run -dit --name my_empty_container ubuntu");

            // Step 3: Check Running Containers
            System.out.println("Checking Running Containers...");
            runCommand("docker ps");

            // Step 4: Stop and Remove Container
//            System.out.println("Stopping and Removing Container...");
//            runCommand("docker stop my_empty_container");
//            runCommand("docker rm my_empty_container");

            // Step 5: Start Container using Docker Compose
            System.out.println("Starting Container using Docker Compose...");
            runCommand("docker-compose up -d --build");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
