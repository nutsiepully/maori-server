package io.pulkit.maori.domain;

import javax.persistence.*;

@Entity
@Table(name = "model_devices")
public class ModelDevice {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "device_id")
    private String deviceId;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    public ModelDevice() {
    }

    public ModelDevice(String deviceId, Model model) {
        this.deviceId = deviceId;
        this.model = model;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
