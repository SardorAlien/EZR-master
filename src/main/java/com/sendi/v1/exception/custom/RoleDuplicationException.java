package com.sendi.v1.exception.custom;

import com.sendi.v1.util.ErrorMessages;

public class RoleDuplicationException extends DuplicationException {
    private static final long serialVersionUID = -7255663927912634937L;

    public RoleDuplicationException(String roleName) {
        super(ErrorMessages.ROLE_DUPLICATION.getMessage() + roleName);
    }
}
