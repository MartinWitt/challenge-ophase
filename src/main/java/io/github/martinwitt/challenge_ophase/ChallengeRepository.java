package io.github.martinwitt.challenge_ophase;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ChallengeRepository implements PanacheMongoRepository<Challenge> {

    public Challenge getChallengeByFlag(String flag) {
        return find("flag", flag).firstResult();
    }

    public Challenge getChallengeByName(String name) {
        return find("name", name).firstResult();
    }
}
