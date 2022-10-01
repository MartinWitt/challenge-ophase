package io.github.martinwitt.challenge_ophase;

import io.quarkus.runtime.StartupEvent;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class StartUp {

    @ConfigProperty(name = "first.challenge.name")
    String firstChallengeName;

    void onStart(@Observes StartupEvent ev) {
        System.out.println("The application is starting...");
        Challenge challenge = new Challenge(firstChallengeName, "aaa", "cccc", List.of("lol"), List.of(), false, "bbb");
        challenge.persistOrUpdate();
    }
}
