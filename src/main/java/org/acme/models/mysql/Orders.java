package org.acme.models.mysql;

import java.io.Serializable;
import java.sql.Date;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name="Orders")
@Table(name="orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Orders extends PanacheEntityBase implements Serializable {

    @Id
    private Integer orderNumber;

    private Date orderDate;

    private Date requiredDate;

    private Date shippedDate;

    private String status;

    private String comments;

    private Integer customerNumber;
}
