package org.zalando.pazuzu.container;

import org.zalando.pazuzu.feature.Feature;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class Container {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "container_name", nullable = false, length = 32)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "container_feature", joinColumns = @JoinColumn(name = "container_id"), inverseJoinColumns = @JoinColumn(name = "feature_id"))
    private Set<Feature> features;

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

    public Set<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(Set<Feature> features) {
        this.features = features;
    }
}
