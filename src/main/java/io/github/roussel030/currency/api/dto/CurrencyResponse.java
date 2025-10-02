package io.github.roussel030.currency.api.dto;

public record CurrencyResponse(
        Long id,
        String code,
        String name,
        String symbol
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String code;
        private String name;
        private String symbol;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder symbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public CurrencyResponse build() {
            return new CurrencyResponse(id, code, name, symbol);
        }
    }
}

