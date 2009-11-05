package org.hibernate.example.compositeid;

import java.io.Serializable;

/**
 * Composite identity for {@link EntityWithCompositeId}.
 */
public final class CompositeId implements Serializable {
    private Long id1;
    private Long id2;

    public CompositeId() {
    }

    public CompositeId(Long id1, Long id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

    public Long getId1() {
        return id1;
    }

    public void setId1(Long id1) {
        this.id1 = id1;
    }

    public Long getId2() {
        return id2;
    }

    public void setId2(Long id2) {
        this.id2 = id2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompositeId that = (CompositeId) o;

        if (id1 != null ? !id1.equals(that.id1) : that.id1 != null) {
            return false;
        }
        if (id2 != null ? !id2.equals(that.id2) : that.id2 != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id1 != null ? id1.hashCode() : 0;
        result = 31 * result + (id2 != null ? id2.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CompositeId");
        sb.append("{id1=").append(id1);
        sb.append(", id2=").append(id2);
        sb.append('}');
        return sb.toString();
    }
}
