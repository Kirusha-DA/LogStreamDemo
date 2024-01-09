package com.logstream.segmentation.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.logstream.segmentation.segment.Segment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinTable(
        name="customer_segment",
        joinColumns=@JoinColumn(
            name="customer_id", 
            referencedColumnName="id"
        ),
        inverseJoinColumns=@JoinColumn(
            name="segment_id",
            referencedColumnName="id"
        )
    )
    @JsonIgnore
    private Set<Segment> segments = new HashSet<>();
    
}
