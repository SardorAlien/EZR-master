package com.sendi.v1.exception.custom;

import com.sendi.v1.util.ErrorMessages;

public class NoSuchRoleException extends NoSuchObjectException {
    private static final long serialVersionUID = 5236152620302275653L;

    public NoSuchRoleException(String name) {
        super(ErrorMessages.NO_SUCH_ROLE.getMessage() + name);
    }

    public NoSuchRoleException(long id) {
        super(ErrorMessages.NO_SUCH_ROLE_ID.getMessage() + id);
    }
}
