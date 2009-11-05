package org.hibernate.example.compositeid;

public class EntityWithCompositeId {
    private Long id1;
    private Long id2;
    private String data;

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

    public String getData() {

        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
