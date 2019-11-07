package capter02.mybatis.bean;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Dept {
    private String id;
    private String name;
    private Date created;
    private Boolean flag;
    private String contry;
    private String year;

    public Dept() {

    }

    public Dept(Date created) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        this.year = simpleDateFormat.format(created);
        System.out.println(year);
    }

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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
