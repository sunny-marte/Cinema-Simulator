/*
A simple command line Java program that
simulates a
ticket buying interface
for a cinema room.

*The maximum limit for number of rows/columns is 99, otherwise formatting problems will occur

S = seat(available for purchase)
B = bought

*/

import java.util.Scanner;

class Cinema {

    public static int nOfTickets;
    public static int income;
    public static int totalIncome;
    public static boolean seatTaken;
    public static boolean rowIsBought;
    public static boolean roomIsBought;

    public static void main(String[] args) {
        // Write your code here
        printMenu();
    }

    public static void printMenu() {
        Scanner scanner = new Scanner(System.in);

        int nOfRows;
        int nOfSeats;

        do {
            System.out.println("Enter the number of rows:");
            nOfRows = scanner.nextInt();
            if (nOfRows < 1 || nOfRows > 99) {
                System.out.println("Number needs to be between 1 and 99");
            }
        } while (nOfRows < 1 || nOfRows > 99);

        do {
            System.out.println("Enter the number of seats in each row:");
            nOfSeats = scanner.nextInt();
            if (nOfSeats < 1 || nOfSeats > 99) {
                System.out.println("Number needs to be between 1 and 99");
            }
        } while (nOfSeats < 1 || nOfSeats > 99);

        System.out.println();

        char[][] coordinates = new char[nOfRows][nOfSeats]; //creates the 2fD coordinates array based on user input

        totalIncome = nOfRows / 2 * nOfSeats * 10 + (nOfRows / 2 + 1) * nOfSeats * 8; //first half of the rows cost $10, other half costs $8

        for (int j = 0; j < nOfRows; j++) {
            for (int k = 0; k < nOfSeats; k++) {
                coordinates[j][k] = 'S'; //populates every single index with 'S'
            }
        }

        Cinema cinema = new Cinema();
        while (true) {
            System.out.printf("1. Show the seats%n2. Buy a ticket%n3. Buy an entire row of seats%n4. Buy the entire room%n5. Statistics%n0. Exit%n");

            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    showSeats(nOfSeats, nOfRows, coordinates);
                    break;
                case 2:
                    cinema.buyTicket(nOfSeats, nOfRows, coordinates);
                    break;
                case 3:
                    buyRow(nOfSeats, nOfRows, coordinates);
                    break;
                case 4:
                    buyAll(nOfSeats, nOfRows, coordinates);
                    break;
                case 5:
                    cinema.showStatistics(nOfSeats, nOfRows);
                    break;
                case 0:
                    return;
            }
        }
    }

    public static void showSeats(int nOfSeats, int nOfRows, char[][] coordinates) {
        System.out.println();
        System.out.println("Cinema:");
        System.out.print("    "); //adds 4 spaces for formatting of seats column

        for (int i = 1; i <= nOfSeats; i++) { // prints the columns
            if (i > 8) {
                System.out.print(i + " "); // prints number of column plus 1 space
            } else {
                System.out.print(i + "  "); // prints number of column plus 2 spaces
            }
        }
        System.out.println();
        for (int j = 0; j < nOfRows; j++) { //prints the rows
            if (j < 9) {
                System.out.printf("%d   ", j + 1); // prints number of the row plus 3 spaces
            } else {
                System.out.printf("%d  ", j + 1); // prints number of the row plus 2 spaces
            }
            for (int k = 0; k < nOfSeats; k++) {
                System.out.print(coordinates[j][k] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void buyTicket(int nOfSeats, int nOfRows, char[][] coordinates) {
        Scanner scanner = new Scanner(System.in);

        System.out.println();
        int ticketRow;
        int ticketSeat;

        do {
            seatTaken = false;
            System.out.println("Enter a row number:");
            ticketRow = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            ticketSeat = scanner.nextInt();
            System.out.println();

            if (ticketRow > nOfRows || ticketSeat > nOfSeats) {
                System.out.println("Wrong input!");
                System.out.println();
                seatTaken = true;
            } else if (ticketRow > 0 && ticketSeat > 0) {
                seatTaken = coordinates[ticketRow - 1][ticketSeat - 1] == 'B'; //marks the chosen seat as taken

                if (seatTaken) {
                    System.out.println("That ticket has already been purchased!");
                    System.out.println();
                } else {
                    coordinates[ticketRow - 1][ticketSeat - 1] = 'B';
                    nOfTickets++;
                }
            }
        } while (seatTaken);

        // calculates the price of the ticket
        int price = 10;
        if (nOfRows * nOfSeats > 60 && ticketRow >= nOfRows / 2 + 1) {
            price = 8;
        }
        income += price;

        System.out.println("Ticket price: $" + price);
        System.out.println();
    }

    public static void buyRow(int nOfSeats, int nOfRows, char[][] coordinates) {
        System.out.println();
        Scanner scanner = new Scanner(System.in);

        int row;
        int nBoughtSeats;

        do {
            rowIsBought = true;
            nBoughtSeats = 0;
            do {
                System.out.println("Which row would you like to buy?");
                row = scanner.nextInt();
                if (row < 1 || row > 99) {
                    System.out.println("Number needs to be between 1 and 99");
                    System.out.println();
                }
            } while (row < 1 || row > 99);

            for (int i = 0; i < nOfSeats; i++) { //this loop checks if the seat is bought
                if (coordinates[row - 1][i] == 'B') {
                    nBoughtSeats++;
                }
            }

            if (nBoughtSeats == nOfSeats) {
                System.out.println("This row has already been purchased.");
                System.out.println();
            } else {
                nOfTickets += nOfSeats - nBoughtSeats;

                for (int j = 0; j < nOfSeats; j++) {
                    coordinates[row - 1][j] = 'B'; // marks the entire row of seats as bought
                }

                System.out.println("Row has been purchased");
                System.out.println();
                rowIsBought = false;
            }
        } while (rowIsBought); //while row has already been bought

        //updates the income
        int price = 10 * (nOfSeats - nBoughtSeats);
        if (nOfRows * nOfSeats > 60 && row >= nOfRows / 2 + 1) {
            price = 8 * (nOfSeats - nBoughtSeats);
        }
        income += price;
    }

    public static void buyAll(int nOfSeats, int nOfRows, char[][] coordinates) {
        do {
            roomIsBought = true;

            if (nOfTickets == nOfSeats * nOfRows) {  //if the current nr of tickets is equal to the total possible number of tickets
                System.out.println("The room has already been bought!");
                System.out.println();
                return;
            } else {
                for (int i = 0; i < nOfRows; i++) {
                    for (int j = 0; j < nOfSeats; j++) {
                        coordinates[i][j] = 'B'; // populates the entire array with 'B' as in entire room bought
                    }
                }
                System.out.println("Room purchased successfully.");
                System.out.println();

                nOfTickets += (nOfRows * nOfSeats - nOfTickets);

                income = totalIncome;

                roomIsBought = false;
            }
        } while (roomIsBought); // while room has already been bought
    }

    public void showStatistics(int nOfSeats, int nOfRows) {
        System.out.println();
        float percentage;

        System.out.printf("Number of purchased tickets: %d%n", nOfTickets);

        if (nOfTickets == 0) {
            System.out.println("Percentage of room occupied: 0.00%");
        } else {
            percentage = (float) nOfTickets / (nOfRows * nOfSeats) * 100; //calculates what percentage of rows are purchased
            System.out.printf("Percentage of room occupied: %.2f%%%n", percentage);
        }

        System.out.printf("Current income: $%d%n", income);

        System.out.printf("Total possible income: $%d%n", totalIncome); //shows the amount of possible income if all seats in the room were bought
        System.out.println();
    }
}
