package backend.academy.service.impl.reader;

import backend.academy.service.ReadFromSource;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

@SuppressFBWarnings({"URLCONNECTION_SSRF_FD", "OS_OPEN_STREAM"})
public class HttpFetcher implements ReadFromSource {
    private static final int TIMEOUT = 5000;

    @Override
    public Stream<String> readSource(String urlString) throws IOException {

        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException("Error loading data. Server response: " + connection.getResponseCode());
            }

            return new BufferedReader(
                new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)).lines();

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}
