package com.michal.students.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "item")
@SequenceGenerator(name="seqIdGen",initialValue =  2000,allocationSize = 1)
@Getter
@Setter
public class Student {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqIdGen")
    private Long id;

    @NotBlank
    private String firstName;

    @NotEmpty
    @Size(min = 3)
    private String lastName;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    private Status status;

    public enum Status {
        ACTIVE,
        INACTIVE;
    }

    public Student() {
    }


}
