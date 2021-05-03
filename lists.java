package com.zakhar;

import java.util.Scanner;

public class Main {
    static class Wagon {
        String name;
        Wagon next = this;
        Wagon prev = this;

        Wagon (String n) {
            name = n;
        }

        void print() {
            System.out.print(name+" ");
        }
    }

    static class ListaWagonow {
        Wagon first;

        ListaWagonow(Wagon f) {
            first = f;
            first.next = first;
            first.prev = first;
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
                    prev = p;
                    p = p.next;
                } else {
                    p.print();
                    prev = p;
                    p = p.prev;
                }
            }
            p.print();
            System.out.println();
        }

        int liczbaWagonow() {
            int licznik = 0;
            Wagon p = first;
            Wagon l = first.prev;
            Wagon prev = first.prev;
            while (p != l) {
                if (p.next != prev) {
                    licznik++;
                    prev = p;
                    p = p.next;
                } else {
                    licznik++;
                    prev = p;
                    p = p.prev;
                }
            }
            licznik++;
            return licznik;
        }
    }

    static class ListaPociagow {
        Pociag first = null;

        ListaPociagow() {};

        ListaPociagow(Pociag f) {
            first = f;
        }

        void Usun(Pociag u) {
            if (first.name.equals(u.name)) {
                first = first.next;
            } else {
                Pociag f = first;
                if (!f.name.equals(u.name)) {
                    while (!f.next.name.equals(u.name)) {
                        f = f.next;
                    }
                }
                f.next = f.next.next;
            }
        }

        boolean DoesExist(String nazwaPociagu) {
            Pociag ten = first;
            while ( ten != null && !ten.name.equals(nazwaPociagu)) {
                ten = ten.next;
            }
            if (ten == null) {
                return false;
            } else {
                return true;
            }
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
                ListaWagonow l = new ListaWagonow(w);
                Pociag p = new Pociag(nazwaPociagu, w);
                if (first == null) {
                    first = p;
                    first.next = null;
                } else {
                    Pociag n = first;
                    p.next = n;
                    first = p;
                }
            } else {
                System.out.println("Train "+nazwaPociagu+" already exists");
            }
        }

        void InsertFirst(String nazwaPociagu, String nazwaWagonu) {
            Pociag ten = FindPociag(nazwaPociagu);
            if (ten == null) {
                System.out.println("train "+nazwaPociagu+" does not exist");
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
                System.out.println("train "+nazwaPociagu+" does not exist");
            } else {
                Wagon wagon = new Wagon(nazwaWagonu);
                if (ten.first.next == ten.first.next.next && ten.first.prev != ten.first) {
                    wagon.next = ten.first;
                    wagon.prev = ten.first.prev;
                    ten.first.prev.next = ten.first.prev.prev;
                    ten.first.prev.prev = wagon;
                    ten.first.prev = wagon;
                } else {
                    Wagon f = ten.first.prev;
                    wagon.next = ten.first;
                    wagon.prev = f;
                    f.next = wagon;
                    ten.first.prev = wagon;
                }
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
                    if (p.first == p.first.next.next) {
                        p.first.next.next = p.first.next.prev;
                        p.first.next.prev = p.first.prev;
                        p.first.prev.next = p.first.next;
                        p.first = p.first.next;
                    } else {
                        p.first.next.prev = p.first.prev;
                        p.first.prev.next = p.first.next;
                        p.first = p.first.next;
                    }
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
                    if (p.first == p.first.next.next && p.liczbaWagonow() > 2) {
                        p.first.prev = p.first.prev.prev;
                        p.first.prev.prev = p.first.prev.next;
                        p.first.prev.next = p.first;
                    } else {
                        p.first.prev = p.first.prev.prev;
                        p.first.prev.next = p.first;
                    }
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
                if (pierwszy.first.next.next == pierwszy.first) {
                    if (drugi.first.next.next == drugi.first) {
                        // pirewszy reversed drugi tez
                        pierwszy.first.prev.next  = drugi.first;
                        Wagon tmp  = pierwszy.first.prev;
                        pierwszy.first.prev = drugi.first.prev;
                        drugi.first.prev = tmp;
                        pierwszy.first.prev = pierwszy.first;
                        Usun(drugi);
                    } else {
                        // pierwszy reversed drugi nie
                        pierwszy.first.prev.next = pierwszy.first.prev.prev;
                        pierwszy.first.prev.prev = drugi.first;
                        Wagon tmp = drugi.first.prev;
                        drugi.first.prev = pierwszy.first.prev;
                        pierwszy.first.prev = tmp;
                        pierwszy.first.prev.next = pierwszy.first;
                        Usun(drugi);
                    }
                } else {
                    if (drugi.first.next.next == drugi.first) {
                        //pierwszy norm drugi reversed
                        pierwszy.first.prev.next = drugi.first;
                        drugi.first.prev.next = pierwszy.first;
                        Wagon tmp = pierwszy.first.prev;
                        pierwszy.first.prev = drugi.first.prev;
                        drugi.first.prev = tmp;
                        Usun(drugi);
                    } else {
                        // oba norm
                        Wagon a = drugi.first;
                        pierwszy.first.prev.next = a;
                        a.prev.next = pierwszy.first;
                        Wagon b = pierwszy.first.prev;
                        pierwszy.first.prev = a.prev;
                        a.prev = b;
                        Usun(drugi);
                    }
                }
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
        ListaPociagow L = new ListaPociagow();
        L.nowyPociag("T1","W0");
        L.InsertLast("T1","W1");
        L.InsertLast("T1","W2");
        Pociag p = L.FindPociag("T1");
        System.out.println(p.first.name);
        System.out.println(p.first.next.name);
        System.out.println(p.first.prev.name);
        System.out.println(p.first.next.prev.name);
        System.out.println(p.first.next.next.name);
        System.out.println();
        L.Reverse("T1");
        p = L.FindPociag("T1");
        System.out.println(p.first.name);
        System.out.println(p.first.next.name);
        System.out.println(p.first.prev.name);
        System.out.println(p.first.next.prev.name);
        System.out.println(p.first.next.next.name);
    }
}
