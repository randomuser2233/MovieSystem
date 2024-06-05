package com.mvms;

import com.mvms.persistence.dao.impl.FilmDaoImpl;

import java.util.Scanner;

public class TestFilmDao {
    public static void main(String[] args) {
        FilmDaoImpl filmService = new FilmDaoImpl();

        Scanner scanner = new Scanner(System.in);
        filmService.setScanner(scanner);

        filmService.printSessionFactory();

        while (true) {
            System.out.println("1. Create 2. Query 3. Delete 4. Exit\nEnter choice:");
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 1) {
                filmService.create();
            } else if (choice == 2) {
                filmService.query();
            } else if (choice == 3) {
                filmService.delete();
            } else {
                System.out.println("Thank you and good bye!");
                return;
            }
        }
    }
}