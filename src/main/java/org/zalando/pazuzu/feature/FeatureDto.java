package org.zalando.pazuzu.feature;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FeatureDto {
    @JsonProperty("name")
    private String name;
    @JsonProperty("docker_data")
    private String dockerData;
    @JsonProperty("test_instruction")
    private String testInstruction;

    public String getName() {
        return name;
    }

    public String getDockerData() {
        return dockerData;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDockerData(String dockerData) {
        this.dockerData = dockerData;
    }

    public static FeatureDto ofShort(Feature feature) {
        if (null == feature) {
            return null;
        }
        final FeatureDto result = new FeatureDto();
        fillShort(feature, result);
        return result;
    }

    protected static void fillShort(Feature feature, FeatureDto result) {
        result.name = feature.getName();
        result.dockerData = feature.getDockerData();
        result.testInstruction =  feature.getTestInstruction();
    }

    public String getTestInstruction() {
        return testInstruction;
    }

    public void setTestInstruction(String testInstruction) {
        this.testInstruction = testInstruction;
    }
}
