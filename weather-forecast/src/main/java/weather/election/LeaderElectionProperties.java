package weather.election;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("election")
public class LeaderElectionProperties {

    private boolean enabled;
    private String key;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
