package io.github.martinwitt.challenge_ophase;

import io.quarkus.runtime.StartupEvent;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class StartUp {

    void onStart(@Observes StartupEvent ev) {
        System.out.println("The application is starting...");
    }
}
