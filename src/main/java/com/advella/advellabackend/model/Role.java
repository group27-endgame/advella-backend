package com.advella.advellabackend.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Roles")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "roleId")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "users"})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    @ApiModelProperty(notes = "Role Id", example = "1", required = true)
    private int roleId;
    @Column(name = "role_name")
    @ApiModelProperty(notes = "Name", example = "user")
    private String name;
    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
