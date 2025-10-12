import os

def parse_data():
    script_directory = os.path.dirname(os.path.abspath(__file__))
    file_path = os.path.join(script_directory, '..', 'data', 'emd.csv')
    full_path = os.path.abspath(file_path)

    athletes = []

    try:
        with open(full_path, 'r', encoding='utf-8') as data:
            data.readline()
            for line in data:
                split = line.strip().split(',')
                athlete = {
                    "id": split[0],
                    "index": split[1],
                    "dataEMD": split[2],
                    "nome": split[3],
                    "idade": split[5],
                    "genero": split[6],
                    "morada": split[7],
                    "modalidade": split[8],
                    "clube": split[9],
                    "email": split[10],
                    "federado": split[11],
                    "resultado": split[12]
                }
                athletes.append(athlete)
    except FileNotFoundError:
        print(f"File not found: {full_path}")

    return athletes
