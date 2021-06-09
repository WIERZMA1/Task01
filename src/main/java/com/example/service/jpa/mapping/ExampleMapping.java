package com.example.service.jpa.mapping;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "Example")
public class ExampleMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    public Long id;

    @Size(max = 30)
    @Column(name = "description")
    public String description;
}
