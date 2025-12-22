package io.github.roussel030.option.dto;

public record OptionResponse(
        Long id,
        String name,
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
