package com.zakhar;

import java.util.Scanner;

public class Main{
    static class Wagon {
        String name;
        Wagon next = this;
        Wagon prev = this;

        Wagon (String n) {
            name = n;
        }

        void print() {
            System.out.print(name);
        }
    }

    static class Pociag {
        String name;
        Wagon first;
        Pociag next;

        Pociag(String n, Wagon f) {
            name = n;
            first = f;
        }

        void print() {
            Wagon p = first;
            Wagon l = first.prev;
            Wagon prev = first.prev;
            System.out.print(name+": ");
            while (p != l) {
                if (p.next != prev) {
                    p.print();
                    System.out.print(" ");
                    prev = p;
                    p = p.next;
                } else {
                    p.print();
                    System.out.print(" ");
                    prev = p;
                    p = p.prev;
                }
            }
            p.print();
            System.out.println();
        }
    }

    static class ListaPociagow {
        Pociag first = null;

        ListaPociagow() {}

        void Usun(Pociag u) {
            if (first.name.equals(u.name)) {
                first = first.next;
            } else {
                Pociag f = first;
                    while (!f.next.name.equals(u.name)) {
                        f = f.next;
                    }
                f.next = f.next.next;
            }
        }

        boolean DoesExist(String nazwaPociagu) {
            Pociag ten = first;
            while ( ten != null && !ten.name.equals(nazwaPociagu)) {
                ten = ten.next;
            }
            return ten != null;
        }

        Pociag FindPociag(String nazwaPociagu) {
            Pociag ten = first;
            while ( ten != null && !ten.name.equals(nazwaPociagu)) {
                ten = ten.next;
            }
            return ten;
        }

        void Display(String nazwaPociagu) {
            Pociag ten = FindPociag(nazwaPociagu);
            if ( ten == null ) {
                System.out.println("Train "+nazwaPociagu+" does not exist");
            } else {
                ten.print();
            }
        }

        void nowyPociag(String nazwaPociagu, String nazwaWagonu) {
            Pociag f = FindPociag(nazwaPociagu);
            if ( f == null) {
                Wagon w = new Wagon(nazwaWagonu);
                Pociag p = new Pociag(nazwaPociagu, w);
                if (first == null) {
                    first = p;
                    first.next = null;
                } else {
                    p.next = first;
                    first = p;
                }
            } else {
                System.out.println("Train "+nazwaPociagu+" already exists");
            }
        }

        void InsertFirst(String nazwaPociagu, String nazwaWagonu) {
            Pociag ten = FindPociag(nazwaPociagu);
            if (ten == null) {
                System.out.println("Train "+nazwaPociagu+" does not exist");
            } else {
                Wagon wagon = new Wagon(nazwaWagonu);
                if (ten.first.next == ten.first.next.next) {
                    wagon.prev = ten.first.prev;
                    wagon.next = ten.first;
                    ten.first.prev.next = wagon;
                    ten.first.prev = ten.first.next;
                    ten.first.next = wagon;
                } else {
                    Wagon f = ten.first.prev;
                    wagon.next = ten.first;
                    wagon.prev = f;
                    ten.first.prev = wagon;
                    f.next = wagon;
                }
                ten.first = wagon;
            }
        }

        void InsertLast(String nazwaPociagu, String nazwaWagonu) {
            Pociag ten = FindPociag(nazwaPociagu);
            if (ten == null) {
                System.out.println("Train "+nazwaPociagu+" does not exist");
            } else {
                Wagon wagon = new Wagon(nazwaWagonu);
                if (ten.first.next == ten.first.next.next && ten.first.prev != ten.first) {
                    wagon.next = ten.first;
                    wagon.prev = ten.first.prev;
                    ten.first.prev.next = ten.first.prev.prev;
                    ten.first.prev.prev = wagon;
                } else {
                    Wagon f = ten.first.prev;
                    wagon.next = ten.first;
                    wagon.prev = f;
                    f.next = wagon;
                }
                ten.first.prev = wagon;
            }
        }

        void DelFirst(String T1, String T2) {
            if (DoesExist(T1) && !DoesExist(T2)) {
                Pociag p = FindPociag(T1);
                String tmp = p.first.name;
                if (p.first.next == p.first) {
                    p.first = null;
                    Usun(p);
                } else {
                    if (p.first.next.next == p.first) {
                        p.first.next.next = p.first.next.prev;
                    }
                    p.first.next.prev = p.first.prev;
                    p.first.prev.next = p.first.next;
                    p.first = p.first.next;
                }
                nowyPociag(T2, tmp);
            } else if (!DoesExist(T1)) {
                System.out.println("Train " +T1+ " does not exist");
            } else if (DoesExist(T2)) {
                System.out.println("Train " +T2+ " already exists");
            }
        }

        void DelLast(String T1, String T2) {
            if (DoesExist(T1) && !DoesExist(T2)) {
                Pociag p = FindPociag(T1);
                String tmp = p.first.prev.name;
                if (p.first.next == p.first) {
                    p.first = null;
                    Usun(p);
                } else {
                    if (p.first.prev.prev.prev == p.first.prev) {
                        p.first.prev.prev.prev = p.first.prev.prev.next;
                    }
                    p.first.prev = p.first.prev.prev;
                    p.first.prev.next = p.first;
                }
                nowyPociag(T2, tmp);
            } else if (!DoesExist(T1)) {
                System.out.println("Train " +T1+ " does not exist");
            } else if (DoesExist(T2)) {
                System.out.println("Train " +T2+ " already exists");
            }
        }

        void Reverse(String T1) {
            Pociag ten = FindPociag(T1);
            ten.first.prev.next = ten.first.prev.prev;
            ten.first.prev.prev = ten.first;
            Wagon tmp = ten.first.next;
            ten.first.next = ten.first.prev;
            ten.first.prev = tmp;
            ten.first = ten.first.next;
        }

        void Union(String T1, String T2) {
            if (DoesExist(T1) && DoesExist(T2)) {
                Pociag pierwszy = FindPociag(T1);
                Pociag drugi = FindPociag(T2);
                pierwszy.first.prev.next = drugi.first;
                drugi.first.prev.next = pierwszy.first;
                Wagon tmp = drugi.first.prev;
                drugi.first.prev = pierwszy.first.prev;
                pierwszy.first.prev = tmp;
                Usun(drugi);
            }
        }

        void Trains() {
            System.out.print("Trains: ");
            Pociag p = first;
            while (p.next != null) {
                System.out.print(p.name+" ");
                p = p.next;
            }
            System.out.println(p.name+" ");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int iloscZestawow = scanner.nextInt();
        scanner.nextLine();
        if (iloscZestawow>=0) {
            for (int numerZestawu=0; numerZestawu < iloscZestawow; ++numerZestawu) {
                int iloscPolecen = scanner.nextInt();
                scanner.nextLine();
                if (iloscPolecen >= 0) {
                    ListaPociagow L = new ListaPociagow();
                    for (int numerPolecenia=0; numerPolecenia < iloscPolecen; ++numerPolecenia) {
                        String command = scanner.nextLine();
                        String [] slowo = command.split(" ");
                        if (slowo[0].equals("New")) {
                            String nazwaPociagu = slowo[1];
                            String nazwaWagonu = slowo[2];
                            L.nowyPociag(nazwaPociagu, nazwaWagonu);
                        }
                        if (slowo[0].equals("InsertFirst")) {
                            String nazwaPociagu = slowo[1];
                            String nazwaWagonu = slowo[2];
                            L.InsertFirst(nazwaPociagu, nazwaWagonu);
                        }
                        if (slowo[0].equals("InsertLast")) {
                            String nazwaPociagu = slowo[1];
                            String nazwaWagonu = slowo[2];
                            L.InsertLast(nazwaPociagu, nazwaWagonu);
                        }
                        if (slowo[0].equals("Display")) {
                            String nazwaPociagu = slowo[1];
                            L.Display(nazwaPociagu);
                        }
                        if (slowo[0].equals("Trains")) {
                            L.Trains();
                        }
                        if (slowo[0].equals("Reverse")) {
                            String nazwaPociagu = slowo[1];
                            L.Reverse(nazwaPociagu);
                        }
                        if (slowo[0].equals("Union")) {
                            String pierwszy = slowo[1];
                            String drugi = slowo[2];
                            L.Union(pierwszy, drugi);
                        }
                        if (slowo[0].equals("DelFirst")) {
                            String pierwszy = slowo[1];
                            String drugi = slowo[2];
                            L.DelFirst(pierwszy, drugi);
                        }
                        if (slowo[0].equals("DelLast")) {
                            String pierwszy = slowo[1];
                            String drugi = slowo[2];
                            L.DelLast(pierwszy, drugi);
                        }
                    }
                }
            }
        }
    }
}
