package io.github.martinwitt.challenge_ophase;

import io.quarkus.oidc.IdToken;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.jwt.build.Jwt;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

@Path("/api")
@RequestScoped
public class Endpoints {

    private static final Logger LOG = Logger.getLogger(Endpoints.class);

    @ConfigProperty(name = "first.challenge.name")
    String firstChallengeName;

    @Inject
    ChallengeRepository challengeRepository;

    @Inject
    @IdToken
    JsonWebToken idToken;

    @GET
    @Path("/challenges")
    @Authenticated
    @Produces("application/json")
    public List<Challenge> challenges(@Context SecurityIdentity identity) {
        var user = User.getWithUserIDId(idToken.getSubject());
        if (user == null) {
            LOG.info("User with %s ID not found, creating new user".formatted(idToken.getSubject()));
            user = createUserIfMissing();
        }
        List<Challenge> challenges = user.getSolvedChallenges();
        challenges.add(Challenge.hashSecrets(user.getCurrentChallenge()));
        return challenges;
    }

    private User createUserIfMissing() {
        var challenge = challengeRepository.getChallengeByName(firstChallengeName);
        User newUser = new User(idToken.getSubject(), new ArrayList<>(), challenge);
        newUser.persistOrUpdate();
        return newUser;
    }

    @POST
    @Path("/submit-flags")
    @Authenticated
    public Challenge submitFlag(@Context SecurityIdentity identity, String flag) {

        var user = User.getWithUserIDId(idToken.getSubject());
        if (user == null) {
            return Challenge.hashSecrets(createUserIfMissing().getCurrentChallenge());
        }
        var challenge = challengeRepository.getChallengeByFlag(flag);
        if (challenge == null) {
            return Challenge.hashSecrets(user.getCurrentChallenge());
        }
        user.addSolvedChallenge(challenge);
        // TODO: add finish check
        user.setCurrentChallenge(challengeRepository.getChallengeByName(challenge.getNextName()));
        user.update();
        return Challenge.hashSecrets(user.getCurrentChallenge());
    }

    @GET
    @Path("/login")
    @Authenticated
    public Response login(@Context SecurityIdentity identity) throws URISyntaxException {
        return Response.seeOther(new URI("/logged-in?key="
                        + Jwt.sign(Map.of("sub", identity.getPrincipal().getName()))))
                .build();
    }
}
