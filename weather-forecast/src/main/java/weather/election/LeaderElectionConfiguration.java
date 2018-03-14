package weather.election;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LeaderElectionProperties.class)
public class LeaderElectionConfiguration {

    @Autowired(required = false)
    private ServerProperties serverProperties;

    @Bean
    @ConditionalOnProperty(name = "election.enabled")
    public LeaderElection consulLeaderElection(LeaderElectionProperties electionProperties,
                                               ConsulRegistration registration,
                                               ConsulClient consul) {

        final NewService service = registration.getService();
        final Contender contender = new Contender(
                service.getId(),
                service.getName(),
                service.getAddress(),
                serverProperties.getPort()
        );


        return new LeaderElection(contender, consul, electionProperties);
    }
}
