import os
import mysql.connector
from tabulate import tabulate
import csv

cnx = mysql.connector.connect(
    host="localhost",
    user="root",
    password="password",
    charset="utf8"  
)
cursor = cnx.cursor()


def execute_query(query):
    cursor.execute(query)
    results = cursor.fetchall()
    if len(results) > 0:
        headers = [desc[0] for desc in cursor.description]
        print(tabulate(results, headers, tablefmt="fancy_grid"))
    else:
        print("No results found.")


def executeQuery(file_path):
    # Set the encoding when opening the SQL file
    with open(file_path, 'r', encoding='utf8') as sql_file:
        sql_script = sql_file.read()
        statements = sql_script.split(';')
        for statement in statements:
            if statement.strip():
                cursor.execute(statement)
        cnx.commit()
        print(f"Script {file_path} executed successfully.")

def insert_data_to_csv(csv_file, table_name, column_names, new_entries):
    with open(csv_file, 'a', newline="") as file:
        writer = csv.writer(file, delimiter=";")
        for entry in new_entries:
            writer.writerow(entry)
    cnx.commit()


def insert_data_from_csv(csv_file, table_name, column_names):
    with open(csv_file, newline="") as file:
        reader = csv.DictReader(file, delimiter=";")

        for row in reader:
            values = [row[column] for column in column_names]
            placeholders = ', '.join(['%s'] * len(column_names))
            sql = f"INSERT INTO {table_name} ({', '.join(column_names)}) VALUES ({placeholders})"
            cursor.execute(sql, values)
        cnx.commit()

def cleanDB():
    cursor.execute("DROP DATABASE IF EXISTS GameBlockbuster")

def execute_procedure(procedure_name, params=None):
    if params:
        cursor.callproc(procedure_name, params)
    else:
        cursor.callproc(procedure_name)

    for result in cursor.stored_results():
        results = result.fetchall()
        if len(results) > 0:
            headers = [desc[0] for desc in result.description]
            print(tabulate(results, headers, tablefmt="fancy_grid"))
        else:
            print("No results found.")

    cnx.commit()
    print("Procedure executed successfully.")

def populate_database_from_csv():
    executeQuery("scripts/CreateDB.sql")
    print("Populating from csv data files.")
    for file in csv_files:
        insert_data_from_csv(file['file'], file['table'], file['columns'])
    executeQuery("scripts/Views.sql")
    executeQuery("scripts/Procedures.sql")

def populate_database_from_sql():
    executeQuery("scripts/CreateDB.sql")
    print("Populating from SQL population script.")
    executeQuery("scripts/PopulateScript.sql")
    executeQuery("scripts/Views.sql")
    executeQuery("scripts/Procedures.sql")


# variables _______________________________________________________________________________________________________________

csv_files = [
    {
        'file': 'data/vendedor.csv',
        'table': 'Vendedor',
        'columns': ['ID', 'Nome']
    },
    {
        'file': 'data/jogo.csv',
        'table': 'Jogo',
        'columns': ['ID', 'Valor_De_Aluguer', 'Critics_Score', 'Pedidos_De_Aluguer', 'Plataforma_De_Lançamento', 'Nome', 'Data_Lançamento', 'Sinopse', 'Unidades_Em_Stock']
    },
    {
        'file': 'data/cliente.csv',
        'table': 'Cliente',
        'columns': ['Num_telem', 'Nome', 'Idade', 'NIF']
    },
    {
        'file': 'data/inquerito.csv',
        'table': 'Inquerito',
        'columns': ['ID', 'Jogo_que_quer_jogar', 'Jogo_que_mais_jogou', 'Jogo_que_menos_jogou', 'Vendedor_ID', 'Cliente_NIF']
    },
    {
        'file': 'data/AluguerCab.csv',
        'table': 'AluguerCab',
        'columns': ['ID', 'PrecoTotal', 'Periodo_Aluguer', 'data', 'Cliente_NIF', 'Vendedor_ID', 'Metodo_Pagamento']
    },
    {
        'file': 'data/AluguerLinhas.csv',
        'table': 'AluguerLinhas',
        'columns': ['Quantidade', 'PrecoUnit', 'PrecoTotal', 'AluguerCab_ID', 'Jogo_ID']
    }
]

menu = """
Menu:
1. Most rented games by customers
2. Least rented games by customers
3. Most rented platforms by customers
4. Release dates with the highest volume of rentals
5. Top spending customers in the store
6. Low spending customers in the store
7. Customers who have rented the same game more than once
8. Games that customers have enjoyed playing the most
9. Games that customers have enjoyed playing the least
10. Games that customers want to play
11. Sellers who have made the most rentals in the store
12. Sellers who have made the least rentals in the store
13. Sellers who have issued the most surveys in the store
14. Sellers who have not issued any surveys
15. Full content of the Jogo table
16. Full content of the Inquerito table
17. Full content of the Cliente table
18. Get games based on the desired launch platform
19. Sort games by critics' score, price, and release date
20. Sort games by the number of rentals
21. Insert a new game into Jogo table
22. Insert a new client into Cliente table
0. Exit
99. Clean Database
"""

#script_______________________________________________________________________________________________________________________

# Execute SQL
cursor.execute("CREATE DATABASE IF NOT EXISTS GameBlockbuster")
cursor.execute("USE GameBlockbuster")

# Check if the table exists
# Execute the query to retrieve table names
cursor.execute("SHOW TABLES")

# Fetch all the rows returned by the query
tables = cursor.fetchall()


# Check if any tables are returned
while True:
    
    if len(tables) > 0:
        print("There are tables in the database.")
        break
    else:
        print("Menu:")
        print("(1) Populate database from CSV files?")
        print("(2) Populate database from SQL script?")
        choice = input("Enter your choice: ")

        if choice == "1":
            populate_database_from_csv()
            break
        elif choice == "2":
            populate_database_from_sql()
            break
        else:
            print("Invalid choice.")
    
    

while True:
    print(menu)
    choice = input("Enter your choice: ")

    if choice == "1":
        execute_query("SELECT * FROM MostRentedGames")
    elif choice == "2":
        execute_query("SELECT * FROM LeastRentedGames")
    elif choice == "3":
        execute_query("SELECT * FROM MostRentedPlatforms")
    elif choice == "4":
        execute_query("SELECT * FROM ReleaseDatesWithMostRentals")
    elif choice == "5":
        execute_query("SELECT * FROM TopSpendingCustomers")
    elif choice == "6":
        execute_query("SELECT * FROM LowSpendingCustomers")
    elif choice == "7":
        execute_query("SELECT * FROM CustomersWithMultipleGameRentals")
    elif choice == "8":
        execute_query("SELECT * FROM MostLikedGames")
    elif choice == "9":
        execute_query("SELECT * FROM LeastLikedGames")
    elif choice == "10":
        execute_query("SELECT * FROM DesiredGames")
    elif choice == "11":
        execute_query("SELECT * FROM TopRentingSellers")
    elif choice == "12":
        execute_query("SELECT * FROM LowRentingSellers")
    elif choice == "13":
        execute_query("SELECT * FROM TopSurveyIssuingSellers")
    elif choice == "14":
        execute_query("SELECT * FROM SellersWithoutSurveys")
    elif choice == "15":
        execute_query("SELECT * FROM FullJogo")
    elif choice == "16":
        execute_query("SELECT * FROM FullInquerito")
    elif choice == "17":
        execute_query("SELECT * FROM FullCliente")
    elif choice == "18":
        platform = input("Enter the desired launch platform: ")
        execute_procedure("GetGamesByPlatform", (platform,))
    elif choice == "19":
        execute_procedure("SortGamesByCriteria")
    elif choice == "20":
        execute_procedure("SortGamesByRentalCount")
    elif choice == "21":
        game_id = input("Enter the ID of the game: ")
        rental_value = float(input("Enter the rental value of the game: "))
        critics_score = input("Enter the critic's score of the game: ")
        rental_requests = input(
            "Enter the number of rental requests for the game: ")
        launch_platform = input("Enter the launch platform of the game: ")
        game_name = input("Enter the name of the game: ")
        release_date = input(
            "Enter the release date of the game (YYYY-MM-DD): ")
        synopsis = input("Enter the synopsis of the game: ")
        stock_units = input("Enter the number of stock units for the game: ")
        params = (game_id, rental_value, critics_score, rental_requests,
                  launch_platform, game_name, release_date, synopsis, stock_units)
        execute_procedure("AddJogo", params)
        print("Game inserted successfully.")
        new_entry = [str(val) for val in params]
        insert_data_to_csv('data/jogo.csv', 'Jogo', ['ID', 'Valor_De_Aluguer', 'Critics_Score', 'Pedidos_De_Aluguer', 'Plataforma_De_Lançamento', 'Nome', 'Data_Lançamento', 'Sinopse', 'Unidades_Em_Stock'], [new_entry])
    elif choice == "22":
        client_phone = input("Enter the client's phone number: ")
        client_name = input("Enter the name of the client: ")
        client_age = input("Enter the age of the client: ")
        client_nif = input(
            "Enter the NIF (tax identification number) of the client: ")
        params = (client_phone, client_name, client_age, client_nif)
        execute_procedure("AddCliente", params)
        print("Client inserted successfully.")
        new_entry = [str(val) for val in params]
        insert_data_to_csv('data/cliente.csv', 'Cliente', ['Num_telem', 'Nome', 'Idade', 'NIF'], [new_entry])
    elif choice == "0":
        break
    elif choice == "99":
        cleanDB()
        break
    else:
        print("Invalid choice. Please try again.")


cursor.close()
cnx.close()
