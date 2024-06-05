package com.mvms;
import java.lang.*;
import com.mvms.entity.Address;
import com.mvms.entity.Customer;
import com.mvms.entity.Gender;
import com.mvms.entity.Rental;
import com.mvms.persistence.dao.impl.FilmDaoImpl;
import com.mvms.utils.HibernateUtil;
import com.mvms.persistence.dao.FilmDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App_P
{
    public static void main( String[] args )
    {
        Rental rental = new Rental(LocalDate.parse("2024-06-03"), LocalDate.parse("2024-06-13"));
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();


        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        FilmDao filmDao = new FilmDaoImpl();

        //Customer customer = filmDao.queryCustomer(Long.valueOf(2));
        //rental.setCustomer(customer);
        filmDao.queryByRentalDate();


        //session.persist(rental);

        tx.commit();

        session.close();
    }
}
