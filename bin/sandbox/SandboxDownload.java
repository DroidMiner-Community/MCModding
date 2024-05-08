import java.io.*;
import java.net.*;
import java.nio.file.*;

public class SandboxDownload {
    private static final int BUFFER_SIZE = 4096; // buffer size for reading data

    public static void main(String[] args) {
        String[] urls = {
            "https://mcmodding.org/sandbox/minecraft_development_kit/mdk-1.0.0/mdk-1.0.0.jar",
            "https://mcmodding.org/sandbox/minecraft_development_kit/mdk-1.0.0/mdk-repair.jar",
            "https://mcmodding.org/sandbox/minecraft_development_kit/mmk-1.0.0/mmk-1.0.0.jar",
            "https://mcmodding.org/sandbox/minecraft_development_kit/mdk-1.0.0/mmk-repair"
        };
        
        for (String url : urls) {
            try {
                downloadFileWithProgressBar(url);
            } catch (IOException e) {
                System.err.println("Error downloading file from " + url + ": " + e.getMessage());
            }
        }
    }

    /**
     * Downloads a file from a given URL with a progress bar.
     */
    public static void downloadFileWithProgressBar(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // set request headers
        conn.setRequestMethod("GET");
        conn.setUseCaches(false);
        conn.setDefaultUseCaches(false);
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 Edge/16.16299");

        // open input stream
        InputStream in = conn.getInputStream();

        // get file name and path from URL
        Path outputPath = Files.createTempFile("temp-download-", null);
        String fileName = outputPath.getFileName().toString();

        // calculate total bytes to read
        long totalBytesToRead = conn.getContentLengthLong();

        // create output stream
        OutputStream out = new BufferedOutputStream(Files.newOutputStream(outputPath));

        // print progress info
        System.out.printf("Downloading %s (%d bytes)...\n", fileName, totalBytesToRead);

        // read input stream and write to output stream in chunks
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;
        long bytesReadTotal = 0;
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
            bytesReadTotal += bytesRead;
            double percentComplete = ((double) bytesReadTotal / (double) totalBytesToRead) * 100.0;
            System.out.printf("\r%3.2f%% complete", percentComplete);
        }

        // close streams
        out.flush();
        out.close();
        in.close();

        // change file permissions to allow execution
        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxr-xr-x");
        FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(perms);
        Files.setPosixFilePermissions(outputPath, perms);

        // print completion message
        System.out.printf("\nDone! Saved to %s.\n", outputPath);
    }
}