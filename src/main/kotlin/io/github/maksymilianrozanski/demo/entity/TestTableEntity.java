package io.github.maksymilianrozanski.demo.entity;

import javax.persistence.*;

@Entity
@Table(name="testtable")
public class TestTableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="title")
    private String title;

    @Column(name = "description")
    private String description;


    public TestTableEntity() {
    }

    public TestTableEntity(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TestTableEntity{" + "id=" + id + ", title='" + title + '\'' + ", description='" + description + '\'' + '}';
    }
}
