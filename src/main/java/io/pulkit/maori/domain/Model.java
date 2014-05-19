package io.pulkit.maori.domain;

import javax.persistence.*;

@Entity
@Table(name = "models")
public class Model {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "version")
    private String version;

    @Column(name = "active")
    private boolean active;

    @Column(name = "archive")
    private boolean archive;

    @Column(name = "payload")
    private byte[] payload;

    public Model() {
    }

    public Model(String name, String version, boolean active, boolean archive, byte[] payload) {
        this.name = name;
        this.version = version;
        this.active = active;
        this.archive = archive;
        this.payload = payload;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "{ " +
                "name=\"" + name + '\"' +
                ", version=\"" + version + '\"' +
                ", active=" + active +
                " }";
    }
}
