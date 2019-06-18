package capter02.mybatis.bean;

import java.util.Date;


public class Dept {
    private String id;
    private String name;
    private Date created;
    private Boolean flag;
    private String contry;

    public Date getCreated() {
        return created;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getContry() {
        return contry;
    }

    public void setContry(String contry) {
        this.contry = contry;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", created=" + created +
                ", flag=" + flag +
                ", contry='" + contry + '\'' +
                '}';
    }
}
