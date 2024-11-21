package gov.elections.candidateservice.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {
    FAILED("FAILED"),
    SUCCESSFUL("SUCCESSFUL");

    private final String label;
}