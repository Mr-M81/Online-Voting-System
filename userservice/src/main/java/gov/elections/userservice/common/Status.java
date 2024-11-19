package gov.elections.userservice.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {
    FAILED("FAILED"),
    SUCCESSFUL("SUCCESSFUL");

    private final String label;
}
