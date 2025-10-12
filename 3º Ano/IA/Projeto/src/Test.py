import random
from Courier import Courier
from Order import Order

class Tests:
    def import_couriers(couriers):
        couriers.extend([
            Courier("Joao", "Bicycle"),
            Courier("Pedro", "Moto"),
            Courier("Maria", "Car"),
            Courier("Ana", "Bicycle"),
            Courier("Rui", "Moto"),
            Courier("Catarina", "Car"),
            Courier("Miguel", "Bicycle"),
            Courier("Sofia", "Moto"),
            Courier("Daniel", "Car"),
            Courier("Ines", "Bicycle"),
        ])

    def import_orders(orders):
        orders.extend([
            Order("Order01", 1, 10, random.randint(1, 60), "Rua de Camoes", "Rua da Saudade"),
            Order("Order02", 90, 20, random.randint(1, 120), "Rua de Camoes", "Avenida Conde Margaride"),
            Order("Order03", 40, 30, random.randint(1, 120), "Rua de Camoes", "Rua da Abadia"),
            Order("Order04", 23, 15, random.randint(1, 60), "Rua de Camoes", "Rua de Santo Antonio"),
            Order("Order05", 32, 2, random.randint(1, 120), "Rua de Camoes", "Avenida D Joao IV"),
            Order("Order06", 15, 12, random.randint(1, 60), "Rua de Camoes", "Rua da Olaria"),
            Order("Order07", 6, 1, random.randint(1, 60), "Rua de Camoes", "Rua dos Artistas"),
            Order("Order08", 2, 2, random.randint(1, 60), "Rua de Camoes", "Rua do Mercado"),
            Order("Order09", 1, 1, random.randint(1, 60), "Rua de Camoes", "Rua dos Platanos"),
            Order("Order10", 3, 2, random.randint(1, 60), "Rua de Camoes", "Rua da Caldeiroa"),
            Order("Order11", 11, 1, random.randint(1, 60), "Rua de Camoes", "Rua da Ribeira"),
            Order("Order12", 14, 1, random.randint(1, 60), "Rua de Camoes", "Avenida Alberto Sampaio"),
            Order("Order13", 20, 2, random.randint(1, 120), "Rua de Camoes", "Largo do Trovador"),
            Order("Order14", 1, 1, random.randint(1, 60), "Rua de Camoes", "Rua de Santa Maria"),
            Order("Order15", 7, 1, random.randint(1, 60), "Rua de Camoes", "Avenida D Afonso Henriques"),
            Order("Order16", 4, 1, random.randint(1, 60), "Rua de Camoes", "Rua da Penha"),
            Order("Order17", 2, 1, random.randint(1, 60), "Rua de Camoes", "Avenida D X"),
            Order("Order18", 3, 2, random.randint(1, 60), "Rua de Camoes", "Rua da Abadia"),
            Order("Order19", 10, 1, random.randint(1, 60), "Rua de Camoes", "Rua de Fernao Mendes Pinto"),
            Order("Order20", 19, 1, random.randint(1, 60), "Rua de Camoes", "Largo da Feira"),
            Order("Order21", 21, 2, random.randint(1, 60), "Rua de Camoes", "Largo do Trovador"),
            Order("Order22", 5, 1, random.randint(1, 60), "Rua de Camoes", "Rua de Santa Maria"),
        ])

