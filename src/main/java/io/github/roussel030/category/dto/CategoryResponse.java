package io.github.roussel030.category.dto;

public record CategoryResponse(Long id, String name) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public CategoryResponse build() {
            return new CategoryResponse(id, name);
        }
    }

}
