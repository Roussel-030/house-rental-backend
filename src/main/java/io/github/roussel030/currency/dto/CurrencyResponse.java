package io.github.roussel030.currency.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "CurrencyResponse", description = "Response returned for a currency")
public record CurrencyResponse(

        @Schema(description = "Unique ID of the currency", example = "1")
        Long id,

        @Schema(description = "ISO 4217 currency code", example = "USD")
        String code,

        @Schema(description = "Full name of the currency", example = "United States Dollar")
        String name,

        @Schema(description = "Symbol of the currency", example = "$")
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