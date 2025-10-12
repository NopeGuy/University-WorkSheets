from src.Order import Order
from src.menu_functions import get_nodes


def add_order():
    print("===== Register Order =====")
    client_name = input("Enter client's name: ")
    weight = float(input("Enter product weight (in kg): "))
    volume = float(input("Enter product volume (in cm3): "))
    processing_time = int(input("Enter processing time (in minutes): "))
    starting_node, last_node = get_nodes()

    order = Order(client_name, weight, volume, processing_time, starting_node, last_node)
    return order


def display_orders(orders):
    print("===== Registered Orders =====")
    for order in orders:
        print(f"Client: {order.client_name}, Weight: {order.weight} kg, Volume: {order.volume} cm3,"
              f" Processing Time: {order.processing_time} minutes, "
              f"Start Node: {order.starting_node}, Last Node: {order.last_node}")
    print("===============================")
