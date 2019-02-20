package org.nju.mycourses.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Document {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "did",length = 32, nullable = false)
    @ToString.Exclude
    private String id;

    @Basic
    @Column(nullable = false)
    private String path;

    @Basic
    @Column(nullable = false)
    private String name;

    private String type;
}
