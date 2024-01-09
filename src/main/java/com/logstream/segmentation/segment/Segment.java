package com.logstream.segmentation.segment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.logstream.segmentation.customer.Customer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
public class Segment {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name="slug", unique=true)
    private String slug;

    @Column(name="created_at")
    private Date createdAt = new Date();

    @ManyToMany(mappedBy="segments", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JsonIgnore
    private Set<Customer> customers = new HashSet<>();

}
