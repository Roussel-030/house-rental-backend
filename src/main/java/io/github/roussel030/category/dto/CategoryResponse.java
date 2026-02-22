package io.github.roussel030.category.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "CategoryResponse", description = "Response returned for a house category")
public record CategoryResponse(

        @Schema(description = "Unique ID of the house category", example = "1")
        Long id,

        @Schema(description = "Name of the house category", example = "Living Room")
        String name

) {

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
