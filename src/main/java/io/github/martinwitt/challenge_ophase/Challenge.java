package io.github.martinwitt.challenge_ophase;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.util.List;

@MongoEntity(collection = "challenges")
public class Challenge extends PanacheMongoEntity implements java.io.Serializable {

    private String name;
    private String description;
    private String flag;
    private List<String> hints;
    private List<ChallengeFile> files;
    private boolean solved;
    private String nextName;

    public Challenge(
            String name,
            String description,
            String flag,
            List<String> hints,
            List<ChallengeFile> files,
            boolean solved,
            String nextName) {
        this.name = name;
        this.description = description;
        this.flag = flag;
        this.hints = hints;
        this.files = files;
        this.solved = solved;
        this.nextName = nextName;
    }

    public Challenge() {}
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return the flag
     */
    public String getFlag() {
        return flag;
    }
    /**
     * @param flag the flag to set
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }
    /**
     * @return the hints
     */
    public List<String> getHints() {
        return hints;
    }
    /**
     * @param hints the hints to set
     */
    public void setHints(List<String> hints) {
        this.hints = hints;
    }
    /**
     * @return the files
     */
    public List<ChallengeFile> getFiles() {
        return files;
    }
    /**
     * @param files the files to set
     */
    public void setFiles(List<ChallengeFile> files) {
        this.files = files;
    }
    /**
     * @return the solved
     */
    public boolean isSolved() {
        return solved;
    }
    /**
     * @param solved the solved to set
     */
    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    /**
     * @return the nextName
     */
    public String getNextName() {
        return nextName;
    }
    /**
     * @param nextName the nextName to set
     */
    public void setNextName(String nextName) {
        this.nextName = nextName;
    }
}
