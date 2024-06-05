package com.mvms.persistence.dao.impl;

import com.mvms.entity.*;
import com.mvms.persistence.dao.FilmDao;
import com.mvms.utils.HibernateUtil;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FilmDaoImpl implements FilmDao {
    private static Scanner scanner = new Scanner(System.in);
    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        FilmDaoImpl.scanner = scanner;
    }

    public void printSessionFactory() {
        System.out.println(sessionFactory);
    }

    @Override
    public void create() {
        Transaction tx = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // Get film information
            System.out.println("Film name:");
            String filmName = scanner.nextLine();
            System.out.println("Film description:");
            String filmDescription = scanner.nextLine();

            // Get film language
            System.out.println("Film language:");
            String languageName = scanner.nextLine();

            // Get film categories, assume user doesn't list same categories
            System.out.print("Number of categories: ");
            int categoryNumber = Integer.parseInt(scanner.nextLine());
            List<String> categoryNames = new ArrayList<>();
            for (int i = 0; i < categoryNumber; i++) {
                System.out.print("Category #" + (i + 1) + ": ");
                String categoryName = scanner.nextLine();
                categoryNames.add(categoryName);
            }

            // Get film actor(s)
            System.out.print("Number of actors: ");
            int actorNumber = Integer.parseInt(scanner.nextLine());
            List<String> actorFirstNames = new ArrayList<>();
            List<String> actorLastNames = new ArrayList<>();
            for (int i = 0; i < actorNumber; i++) {
                System.out.print("Actor #" + (i + 1) + " First Name: ");
                String actorName = scanner.nextLine();
                actorFirstNames.add(actorName);

                System.out.println("Actor #" + (i + 1) + " Last Name: ");
                actorName = scanner.nextLine();
                actorLastNames.add(actorName);
            }

            // Insert new categories into table (assume categories don't have same name)
            List<Category> filmCategoryList = new ArrayList<>();
            for (String categoryName: categoryNames) {
                Query query = session.createQuery("From Category WHERE name = :name", Category.class);
                query.setParameter("name", categoryName);
                List<Category> categories = query.getResultList();

                if (categories.isEmpty()) {
                    Category category = new Category(categoryName);
                    filmCategoryList.add(category);
                    session.persist(category);
                } else {
                    filmCategoryList.add(categories.get(0));
                }
            }

            // Insert new actors into table (assume actors don't have same name)
            List<Actor> filmActorList = new ArrayList<>();
            for (int i = 0; i < actorNumber; i++) {
                Query query = session.createQuery("FROM Actor WHERE firstName = :firstName AND lastName = :lastName", Actor.class);
                query.setParameter("firstName", actorFirstNames.get(i));
                query.setParameter("lastName", actorLastNames.get(i));
                List<Actor> actors = query.getResultList();

                if (actors.isEmpty()) {
                    Actor actor = new Actor(actorFirstNames.get(i), actorLastNames.get(i));
                    filmActorList.add(actor);
                    session.persist(actor);
                } else {
                    filmActorList.add(actors.get(0));
                }
            }

            // Insert language into table
            Language filmLanguage = null;
            Query query = session.createQuery("From Language WHERE name = :name", Language.class);
            query.setParameter("name", languageName);
            List<Language> languages = query.getResultList();

            if (languages.isEmpty()) {
                filmLanguage = new Language(languageName);
                session.persist(filmLanguage);
            } else {
                filmLanguage = languages.get(0);
            }

            // Insert FilmCategory / FilmActor -> Film / Actor / Category -> Language
            Film film = new Film(filmName, filmDescription);

            for (Category category: filmCategoryList) {
                FilmCategory filmCategory = new FilmCategory(film, category);
                category.getFilmCategories().add(filmCategory);
                film.getFilmCategories().add(filmCategory);
                session.persist(category);
            }

            for (Actor actor: filmActorList) {
                FilmActor filmActor = new FilmActor(film, actor);
                actor.getFilmActors().add(filmActor);
                film.getFilmActors().add(filmActor);
                session.persist(actor);
            }

            film.setLanguage(filmLanguage);
            filmLanguage.getFilms().add(film);
            session.persist(filmLanguage);
            tx.commit();
            session.close();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void query() {
        Transaction tx = null;
        Session session = null;
        List<Film> films = new ArrayList<>();

        try {
            // Initialize session and transaction
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            StringBuilder hql = new StringBuilder("WHERE 1=1");

            System.out.println("Enter film title (Press Enter if skip):");
            String filmTitle = scanner.nextLine();
            System.out.println("Enter film language ID (Press 0 if skip):");
            Long filmLanguageId = Long.parseLong(scanner.nextLine());

            // Build StringBuilder variable
            Map<String, Object> params = new HashMap<>();
            if (!filmTitle.isEmpty()) {
                hql.append(" AND title LIKE :title");
                params.put("title", "%" + filmTitle + "%");
            }

            if (filmLanguageId > 0) {
                List<Language> languages = session.createQuery("WHERE languageId = :languageId", Language.class)
                        .setParameter("languageId", filmLanguageId)
                                .getResultList();
                if (!languages.isEmpty()) {
                    hql.append(" AND language = :language");
                    params.put("language", languages.get(0));
                }
            }

            // Create query
            Query query = session.createQuery(hql.toString(), Film.class);
            for (Map.Entry<String, Object> entry: params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }

            List<Film> result = query.getResultList();
            if (result.isEmpty()) {
                System.out.println("No film matches the query.");
            } else {
                for (Film film: result) {
                    System.out.println(
                        film.getTitle() + ", " +
                        film.getDescription() + ", " +
                        (film.getLanguage() != null ? film.getLanguage().getName() : "No Language")
                    );
                }
            }

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }
    @Override
    public Customer queryCustomer(Long id){
        Session session = null;
        Customer customer = null;
        try{
            session = sessionFactory.openSession();
            StringBuilder hql = new StringBuilder("FROM Customer WHERE id = :customerId");
            //String hql = "from Stock s where s.stockCode = :stockCode";
            List<Customer> result = session.createQuery(hql.toString(), Customer.class)
                    .setParameter("customerId", id).getResultList();
            customer = result.get(0);
        }catch(Exception e){
            e.printStackTrace();
        }
        return customer;
    }
    @Override
    public void queryByCustomerName(){
        //Transaction tx = null;
        Session session = null;
        List<Customer> customers = new ArrayList<>();

        try {
            // Initialize session and transaction
            session = sessionFactory.openSession();
            //tx = session.beginTransaction();
            StringBuilder hql = new StringBuilder("WHERE 1=1");

            System.out.println("Enter name: ");
            String customerName = scanner.nextLine();

            // Build StringBuilder variable
            Map<String, Object> params = new HashMap<>();
            if (!customerName.isEmpty()) {
                hql.append(" AND (firstName LIKE :first_name or lastName LIKE :last_name)");
                params.put("first_name", "%" + customerName + "%");
                params.put("last_name", "%" + customerName + "%");

            }

            // Create query
            Query query = session.createQuery(hql.toString(), Customer.class);
            for (Map.Entry<String, Object> entry: params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }

            List<Customer> result = query.getResultList();
            if (result.isEmpty()) {
                System.out.println("No name matches the query.");
            } else {
                for (Customer customer: result) {
                    System.out.println(
                            customer.getFirstName() + ", " +
                                    customer.getLastName() + ", " +
                                    customer.getAddress().getAddress1() + ", " +
                                    customer.getAddress().getAddress2() );
                    List<Rental> lrental = customer.getRentals();
                    for (Rental rental: lrental) {
                        System.out.println(rental.getRentalDate());
                    }
                }
            }

        } catch (Exception e) {
            /*if (tx != null) {
                tx.rollback();
            }

             */
            throw e;
        }

    }
    @Override
    public void queryByRentalDate(){
        Session session = null;
        List<Rental> rentals = new ArrayList<>();
        try{
            //LocalDateTime now = LocalDateTime.now();
            //System.out.println(now);
            session = sessionFactory.openSession();
            StringBuilder hql = new StringBuilder("WHERE 1=1");
            System.out.println("Enter rental date (YYYY-MM-DD): ");
            String rentalDateStr = scanner.nextLine();
            //DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
            //LocalDate rentalDate = dateFormat.parse(rentalDateStr);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate rentalDate = LocalDate.parse(rentalDateStr, dtf);
            //System.out.println(rentalDateStr);
            //LocalDateTime rentalDate = LocalDateTime.parse(rentalDateStr, dateFormat);
            Map<String, Object> params = new HashMap<>();
                hql.append(" AND rentalDate = :rentalDate");
                params.put("rentalDate", rentalDate);
            Query query = session.createQuery(hql.toString(), Rental.class);
            for (Map.Entry<String, Object> entry: params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            List<Rental> result = query.getResultList();
            if (result.isEmpty()) {
                System.out.println("No rental matches the query.");
            }
            else{
                for (Rental rental: result) {
                    System.out.println("Rental Id is: " + rental.getRentalId());
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void delete() {
        Transaction tx = null;
        Session session = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            System.out.println("Enter Film ID:");
            Long filmId = Long.parseLong(scanner.nextLine());

            if (filmId > 0) {
                Film film = session.get(Film.class, filmId);
                session.remove(film);
                tx.commit();
            }
            session.close();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }
}
