package io.github.roussel030.user.dto;

import io.github.roussel030.user.enumeration.Role;
import io.github.roussel030.user.enumeration.UserStatus;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        Role role,
        UserStatus status
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private Role role;
        private UserStatus status;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public Builder status(UserStatus status) {
            this.status = status;
            return this;
        }

        public UserResponse build() {
            return new UserResponse(
                    id,
                    firstName,
                    lastName,
                    email,
                    role,
                    status
            );
        }
    }
}

