package com.ClaimsManagement.PolicyMicroservice.Model;

import jakarta.persistence.*;

@Entity
@Table(name="provider_policy")
public class ProviderPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="hospital_id", unique = true)
    private Long hospitalId;

    @Column(name="hospital_name")
    private String hospitalName;

    @Column(name="group_id")
    private Long groupId;

    @Column(name="location")
    private String location;

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ProviderPolicy(){}
}
