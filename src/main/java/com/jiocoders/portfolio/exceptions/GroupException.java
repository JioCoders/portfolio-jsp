package com.jiocoders.portfolio.exceptions;

public class GroupException extends RuntimeException {

    public GroupException(String message) {
        super(message);
    }

    public GroupException(String message, Throwable cause) {
        super(message, cause);
    }

    // Specific exception types for better error categorization
    public static class GroupNotFoundException extends GroupException {

        public GroupNotFoundException(Long groupId) {
            super("Group not found with ID: " + groupId);
        }

        public GroupNotFoundException(String groupName) {
            super("Group not found with name: " + groupName);
        }

    }

    public static class DuplicateGroupNameException extends GroupException {

        public DuplicateGroupNameException(String groupName) {
            super("A group with name '" + groupName + "' already exists");
        }

    }

    public static class InvalidGroupOperationException extends GroupException {

        public InvalidGroupOperationException(String message) {
            super(message);
        }

    }

}