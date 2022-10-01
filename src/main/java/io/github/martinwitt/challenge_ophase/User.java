package io.github.martinwitt.challenge_ophase;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.util.List;

@MongoEntity(collection = "users")
public class User extends PanacheMongoEntity {
    private String userID;
    private List<Challenge> solvedChallenges;
    private Challenge currentChallenge;

    public User(String userID, List<Challenge> solvedChallenges, Challenge currentChallenge) {
        this.userID = userID;
        this.solvedChallenges = solvedChallenges;
        this.currentChallenge = currentChallenge;
    }

    public User() {}

    public static User getWithUserIDId(String userID) {
        return find("userID", userID).firstResult();
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * @return the challenges
     */
    public List<Challenge> getSolvedChallenges() {
        return solvedChallenges;
    }

    /**
     * @param challenges the challenges to set
     */
    public void setSolvedChallenges(List<Challenge> challenges) {
        this.solvedChallenges = challenges;
    }

    /**
     * @return the currentChallenge
     */
    public Challenge getCurrentChallenge() {
        return currentChallenge;
    }

    /**
     * @param currentChallenge the currentChallenge to set
     */
    public void setCurrentChallenge(Challenge currentChallenge) {
        this.currentChallenge = currentChallenge;
    }

    public void addSolvedChallenge(Challenge challenge) {
        this.solvedChallenges.add(challenge);
    }
}
