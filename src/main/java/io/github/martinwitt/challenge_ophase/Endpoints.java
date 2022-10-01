package io.github.martinwitt.challenge_ophase;

import io.quarkus.oidc.IdToken;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.NoCache;

@Path("/")
public class Endpoints {

    @ConfigProperty(name = "first.challenge.name")
    String firstChallengeName;

    @Inject
    ChallengeRepository challengeRepository;

    @Inject
    @IdToken
    JsonWebToken idToken;

    @GET
    @Path("/challenges")
    @NoCache
    @Authenticated
    public List<Challenge> challenges(@Context SecurityIdentity identity) {
        var user = User.getWithUserIDId(idToken.getSubject());
        if (user == null) {
            User newUser = createUserIfMissing();
            return newUser.getSolvedChallenges();
        }
        return user.getSolvedChallenges();
    }

    private User createUserIfMissing() {
        var challenge = challengeRepository.getChallengeByName(firstChallengeName);
        User newUser = new User(idToken.getSubject(), new ArrayList<>(), challenge);
        newUser.persistOrUpdate();
        return newUser;
    }

    @POST
    @Path("/submit-flags")
    @NoCache
    @Authenticated
    public Challenge submitFlag(@Context SecurityIdentity identity, String flag) {
        var user = User.getWithUserIDId(idToken.getSubject());
        if (user == null) {
            return createUserIfMissing().getCurrentChallenge();
        }
        var challenge = challengeRepository.getChallengeByFlag(flag);
        if (challenge == null) {
            return user.getCurrentChallenge();
        }
        user.addSolvedChallenge(challenge);
        // TODO: add finish check
        user.setCurrentChallenge(challengeRepository.getChallengeByName(challenge.getNextName()));
        user.update();
        return user.getCurrentChallenge();
    }
}
