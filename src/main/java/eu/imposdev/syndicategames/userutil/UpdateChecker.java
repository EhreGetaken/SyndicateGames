package eu.imposdev.syndicategames.userutil;

import eu.imposdev.syndicategames.SyndicateGames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class UpdateChecker {

    private final Logger logger;

    private String url = "https://imposdev.eu/repo/org/spigotmc/syndicategames/";
    private String id = "version";

    private boolean isAvailable;

    public UpdateChecker() {
        this.logger = LoggerFactory.getLogger(UpdateChecker.class);
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void check() {
        isAvailable = checkUpdate();
    }

    private boolean checkUpdate() {
        this.logger.info("Check for updates...");
        try {
            String localVersion = SyndicateGames.getInstance().getVersion();
            HttpsURLConnection connection = (HttpsURLConnection) new URL(url + id).openConnection();
            connection.setRequestMethod("GET");
            String raw = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();

            String remoteVersion;
            if (raw.contains("-")) {
                remoteVersion = raw.split("-")[0].trim();
            } else {
                remoteVersion = raw;
            }

            if (!localVersion.equalsIgnoreCase(remoteVersion))
                return true;

        } catch (IOException e) {
            return false;
        }
        return false;
    }
}
