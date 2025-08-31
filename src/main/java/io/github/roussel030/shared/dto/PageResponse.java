package io.github.roussel030.shared.dto;

import java.util.List;

public record PageResponse<T>(
        List<T> items,
        int page,
        int size,
        long total
) {

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> {
        private List<T> items;
        private int page;
        private int size;
        private long total;

        public Builder<T> items(List<T> items) {
            this.items = items;
            return this;
        }

        public Builder<T> page(int page) {
            this.page = page;
            return this;
        }

        public Builder<T> size(int size) {
            this.size = size;
            return this;
        }

        public Builder<T> total(long total) {
            this.total = total;
            return this;
        }

        public PageResponse<T> build() {
            return new PageResponse<>(items, page, size, total);
        }
    }
}
