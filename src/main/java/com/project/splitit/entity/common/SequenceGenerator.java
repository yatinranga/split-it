package com.project.splitit.entity.common;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@SuppressWarnings("serial")
@Entity
@Table(name = "sequence_generator", uniqueConstraints = { @UniqueConstraint(columnNames = { "type" }) })
@DynamicInsert(value = true)
@DynamicUpdate(value = true)
public class SequenceGenerator extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "sequence can't be null")
    @Column(name = "sequence", columnDefinition = "int default 1")
    private Integer sequence = 1;

    @Column(name = "type")
    private String type;

    public SequenceGenerator() {
        super();
    }

    public SequenceGenerator(String type, Integer sequence) {
        super();
        this.type = type;
        this.sequence = sequence;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
