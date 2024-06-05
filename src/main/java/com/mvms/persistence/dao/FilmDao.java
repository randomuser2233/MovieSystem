package com.mvms.persistence.dao;

import java.sql.SQLException;
import java.util.List;

import com.mvms.entity.Customer;
import com.mvms.entity.Film;

public interface FilmDao {
    public void create();
    public void query();
    public void delete();


    public Customer queryCustomer(Long id);
    public void queryByCustomerName();
    public void queryByRentalDate();
}
