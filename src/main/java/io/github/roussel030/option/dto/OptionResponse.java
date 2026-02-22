package io.github.roussel030.option.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "OptionResponse", description = "Response returned for a house option")
public record OptionResponse(

        @Schema(description = "Unique ID of the house option", example = "1")
        Long id,

        @Schema(description = "Name of the house option", example = "Garage")
        String name,

        @Schema(description = "Icon representing the option", example = "fa-solid fa-car")
        String icon

) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String icon;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder icon(String icon) {
            this.icon = icon;
            return this;
        }

        public OptionResponse build() {
            return new OptionResponse(id, name, icon);
        }
    }

}