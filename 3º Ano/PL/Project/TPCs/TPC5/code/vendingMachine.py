import re
import csv
import os

def read_moedas_txt():
    current_directory = os.getcwd()
    file_path = current_directory + '/TPC5/input/moedas.txt'
    if os.path.exists(file_path):
        with open(file_path, 'r') as file:
            moedas = file.read().split(',')
            moedas = [float(moeda) for moeda in moedas]
        return moedas
    else:
        print(f"Ficheiro de moedas não encontrado.")
        return []

saldo = 0.0
moedasaceites = {'1c': 0.01, '2c': 0.02, '5c': 0.05, '10c': 0.10, '20c': 0.20, '50c': 0.50, '1e': 1.0, '2e': 2.0}
moedas_maquina = read_moedas_txt()
stock = {}

def carregar_stock():
    current_directory = os.getcwd()
    file_path = current_directory + '/TPC5/input/stock.csv'
    if os.path.exists(file_path):
        with open(file_path, newline='') as file:
            reader = csv.reader(file)
            next(reader)
            for row in reader:
                stock[row[0]] = {'Nome': row[1], 'Preço': float(row[2]), 'Stock': int(row[3])}
    else:
        print(f"Ficheiro de stock não encontrado.")

def listar():
    print("\nID  | Nome                   | Preço   | Stock")
    print("----|------------------------|---------|------")
    for item_id, item_info in stock.items():
        print(f"{item_id:<4}| {item_info['Nome']:<23}| {item_info['Preço']:<6.2f}€ | {item_info['Stock']}")
    print(f"\nSaldo atual: {saldo}€")

def moeda(moedas_inseridas):
    global saldo
    for moeda in moedas_inseridas:
        if moeda in moedasaceites:
            valor = moedasaceites[moeda]
            saldo += valor
            moedas_maquina.append(valor)
            print(f"Saldo atual: {saldo}€\n")
        else:
            print(f"Moeda inválida: {moeda}. Moedas aceites: {list(moedasaceites.keys())}\n")

def calcular_moedas_maquina(valor):
    moedas_disponiveis = []
    moedas_maquina.sort(reverse=True)
    for moeda in moedas_maquina:
        while valor >= moeda:
            moedas_disponiveis.append(moeda)
            valor -= moeda
            moedas_maquina.remove(moeda)
            file_path = os.getcwd() + '/TPC5/input/moedas.txt'
            with open(file_path, 'w') as file:
                file.write(','.join([str(moeda) for moeda in moedas_maquina]))
    return moedas_disponiveis

def selecionar(item_id):
    global saldo
    if str(item_id) in stock:
        item_info = stock[str(item_id)]
        if item_info['Stock'] > 0 and saldo >= item_info['Preço']:
            print(f"\nItem selecionado: {item_info['Nome']} - {item_info['Preço']}€")
            item_info['Stock'] -= 1
            saldo -= item_info['Preço']
            print(f"\n| Novo saldo: {saldo}€ |")
            moedas_maquina.append(item_info['Preço'])
            file_path = os.getcwd() + '/TPC5/input/stock.csv'
            with open(file_path, 'w', newline='') as file:
                writer = csv.writer(file)
                writer.writerow(['ID', 'Nome', 'Preço', 'Stock'])
                for item_id, item_info in stock.items():
                    writer.writerow([item_id, item_info['Nome'], item_info['Preço'], item_info['Stock']])
        elif item_info['Stock'] <= 0:
            print(f"Produto {item_id} sem stock.\n")
        else:
            print("Saldo insuficiente.\n")
    else:
        print("Produto não encontrado.\n")

def sair():
    global saldo
    print(f"\nO seu troco é de {saldo}€")
    print(f"Moedas a devolver: {calcular_moedas_maquina(saldo)}\n")
    saldo = 0.0
    print("Obrigado por utilizar a máquina de venda automática. Volte Sempre!")
    exit()

def process_input(input_str):
    if re.match(r'LISTAR', input_str, flags=re.I):
        listar()
    elif re.match(r'MOEDA \S+', input_str, flags=re.I):
        moedas_inseridas = re.findall(r'(\d+[ec])', input_str)
        moeda(moedas_inseridas)
    elif re.match(r'MOEDA', input_str, flags=re.I):
        print("Input Inválido. Moedas possíveis: 1c, 2c, 5c, 10c, 20c, 50c, 1e, 2e.")
    elif re.match(r'SELECIONAR (\d+)', input_str, flags=re.I):
        item_id = re.search(r'\d+', input_str).group()
        selecionar(item_id)
    elif re.match(r'SELECIONAR', input_str, flags=re.I):
        print("Por favor, insira um ID de item válido")
    elif re.match(r'SAIR', input_str, flags=re.I):
        sair()
    else:
        print("Input Inválido. Tente novamente.")

while True:
    carregar_stock()
    user_input = input("Escreva o comando: \n-> LISTAR\n-> MOEDA (separadas por espaços)\n-> SELECIONAR (ID do item a comprar)\n-> SAIR\n")
    process_input(user_input)
