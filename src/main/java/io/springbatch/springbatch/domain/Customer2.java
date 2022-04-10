package io.springbatch.springbatch.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer2 {
    @Id
    @GeneratedValue
    private Long id;
    private String firstname;
    private String lastname;
    private String birthdate;
}
