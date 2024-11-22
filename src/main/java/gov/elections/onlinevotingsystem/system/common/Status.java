package gov.elections.onlinevotingsystem.system.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {
    FAILED("FAILED"),
    SUCCESSFUL("SUCCESSFUL");

    private final String label;
}
