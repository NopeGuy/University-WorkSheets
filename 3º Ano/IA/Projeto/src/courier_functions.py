from src.Courier import Courier
from src.menu_functions import get_user_choice


def add_courier():
    print("===== Add Courier =====")
    name = input("Enter courier's name: ")
    print("Select transport mode:")
    print("1. Bicycle")
    print("2. Moto")
    print("3. Car")

    transport_choice = get_user_choice()

    if transport_choice in [1, 2, 3]:
        transport_modes = ["Bicycle", "Moto", "Car"]
        transport = transport_modes[transport_choice - 1]
        courier = Courier(name, transport)
        return courier
    else:
        print("Invalid transport mode choice. Courier not added.")
        return None


def display_couriers(couriers):
    print("===== Registered Couriers =====")
    for courier in couriers:
        print(f"Courier: {courier.name}, Transport: {courier.transport}")
    print("===============================")
