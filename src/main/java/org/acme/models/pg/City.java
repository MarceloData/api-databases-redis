package org.acme.models.pg;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class City extends PanacheEntityBase {

    @Id
    public Integer id;

    public String name;

    @Column(name="countrycode", columnDefinition="char(3)")
    public Character countryCode;

    public String district;

    public Integer population;
}
