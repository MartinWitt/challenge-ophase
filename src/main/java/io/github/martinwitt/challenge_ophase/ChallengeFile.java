package io.github.martinwitt.challenge_ophase;

import io.quarkus.mongodb.panache.common.MongoEntity;
import java.io.Serializable;

@MongoEntity(collection = "challengeFiles")
public record ChallengeFile(String name, String url) implements Serializable {}
