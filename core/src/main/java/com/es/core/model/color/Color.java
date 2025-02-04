package com.es.core.model.color;

import java.util.Objects;

public class Color {
    private Long id;
    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Color color = (Color) o;

        if (id != null ? !id.equals(color.id) : color.id != null) return false;
        return code != null ? code.equals(color.code) : color.code == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCode());
    }

    @Override
    public String toString() {
        return code;
    }
}
