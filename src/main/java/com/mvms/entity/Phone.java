package com.mvms.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Phone")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_id")
    private Long id;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "phone_group")
    private PhoneGroup phoneGroup;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhoneGroup getPhoneGroup() {
        return phoneGroup;
    }

    public void setPhoneGroup(PhoneGroup phoneGroup) {
        this.phoneGroup = phoneGroup;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return Objects.equals(phoneNumber, phone.phoneNumber) && phoneGroup == phone.phoneGroup && Objects.equals(customer, phone.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber, phoneGroup, customer);
    }
}
