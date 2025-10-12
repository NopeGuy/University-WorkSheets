from hypothesis import given, strategies as st, settings
from hypothesis.strategies import composite
from datetime import datetime

class LogEntry:
    def __init__(self, date, user, action, details):
        self.date = date
        self.user = user
        self.action = action
        self.details = details

    def __str__(self):
        return f"{self.date},{self.user},{self.action},{','.join(self.details)}"

@composite
def arbitrary_date(draw):
    year = draw(st.integers(min_value=2000, max_value=2023))
    month = draw(st.integers(min_value=1, max_value=12))
    day = draw(st.integers(min_value=1, max_value=28))  # Simplified to avoid invalid dates
    return f"{day:02}:{month:02}:{year}"

@composite
def arbitrary_user(draw):
    user_id = draw(st.integers(min_value=1000, max_value=9999))
    return str(user_id)

@composite
def arbitrary_compra_details(draw):
    item_id = draw(st.integers(min_value=1000, max_value=9999))
    return [str(item_id)]

@composite
def arbitrary_venda_details(draw):
    item_type = draw(st.sampled_from(["Sapatilha", "Mala", "T-shirt"]))
    if item_type == "Sapatilha":
        cor = draw(st.sampled_from(["vermelho", "azul", "verde"]))
        ano_colecao = draw(st.integers(min_value=2015, max_value=2023))
        premium = draw(st.sampled_from(["true", "false"]))
        desconto = draw(st.integers(min_value=0, max_value=50))
        return ["Sapatilha", cor, str(ano_colecao), premium, str(desconto)]
    elif item_type == "Mala":
        material = draw(st.sampled_from(["couro", "sintético"]))
        return ["Mala", material]
    elif item_type == "T-shirt":
        padrao = draw(st.sampled_from(["liso", "riscas", "palmeiras"]))
        return ["T-shirt", padrao]

@composite
def arbitrary_log_entry(draw):
    date = draw(arbitrary_date())
    user = draw(arbitrary_user())
    action = draw(st.sampled_from(["Comprar", "Vender"]))
    if action == "Comprar":
        details = draw(arbitrary_compra_details())
    else:
        details = draw(arbitrary_venda_details())
    return LogEntry(date, user, action, details)

# Funçao para gerar log entries
entries = []

@settings(max_examples=50)
@given(arbitrary_log_entry())
def collect_entries(entry):
    entries.append(entry)

def generate_log_entries(n):
    for _ in range(n):
        collect_entries()

if __name__ == "__main__":
    generate_log_entries(10)
    with open("logTP2.txt", "w") as f:
        for entry in entries:
            f.write(str(entry) + "\n")
    print("Ficheiro de logs gerado com sucesso!")
