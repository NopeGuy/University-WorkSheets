import random
from matplotlib.pylab import rand
from Courier import Courier
from Order import Order

def main_menu():
    print("===== Health Planet Delivery System =====")
    print("1. Add Courier")
    print("2. Register Order")
    print("3. Display Pending Orders")
    print("4. Display Sent Orders")
    print("5. Display Canceled Orders")
    print("6. Display Registered Couriers")
    print("7. Advance a day")
    print("8. Dev Menu")
    print("0. Exit")
    print("===============================")

def dev_menu():
    print("\n===== Dev Menu =====")
    print("1. Represent Delivery Points as Graph")
    print("2. Use Search Algorithms")
    print("3. Compare Search Algorithm Results")
    print("4. Import test couriers")
    print("5. Import test orders")
    print("6. Remove edge (Simulate roadblock)")
    print("0. Exit to Main Menu")
    print("===============================")
    
def get_node_final(guimaraes_graph):
    print("===== Node Selection =====")
    
    while True:
        try:
            finishing_node = input("Delivery Street: ")
            if guimaraes_graph.get_node_by_name(finishing_node) is not None:
                return finishing_node
            else:
                print("Invalid delivery street. Please enter a valid one.")
        except Exception as e:
            print(f"Error: {e}. Please enter a valid delivery street.")

def get_nodes():
    print("===== Node Selection =====")
    starting_node = input("Insert starting node: ")
    finishing_node = input("Insert finishing node: ")
    return starting_node, finishing_node


def get_user_choice():
    try:
        choice = int(input("Enter your choice: "))
        return choice
    except ValueError:
        print("Invalid input. Please enter a number.")
        return None


def search_menu(guimaraes_graph):
    print("===== Available Search Algorithms =====")
    print("1. A*")
    print("2. Greedy")
    print("3. BFS")
    print("4. DFS")
    print("0. Exit")
    print("===============================")

    sub_choice = get_user_choice()

    if sub_choice == 0:
        return

    if sub_choice in [1, 2, 3, 4]:
        starting_node, finishing_node = get_nodes()

        if sub_choice == 1:
            path, cost = guimaraes_graph.procura_aStar(starting_node, finishing_node)
        elif sub_choice == 2:
            path, cost = guimaraes_graph.greedy(starting_node, finishing_node)
        elif sub_choice == 3:
            path, cost = guimaraes_graph.procura_BFS(starting_node, finishing_node)
        elif sub_choice == 4:
            path, cost = guimaraes_graph.procura_DFS(starting_node, finishing_node)

        if path is not None:
            print("Search Algorithm Result:")
            print("Path:", path)
            print("Cost:", cost)
        else:
            print("No path found.")
    else:
        print("Invalid search algorithm choice. Please enter a valid option.")


def compare_search_algorithm_results(guimaraes_graph, starting_node, finishing_node):
    print("\n===== Compare Search Algorithm Results =====")
    try:
        path_a_star, cost_a_star = guimaraes_graph.procura_aStar(starting_node, finishing_node)
        print("\nA* Search Result:")
        print("Path:", path_a_star)
        print("Cost:", cost_a_star)
    except:
        print("\nA* Search Result:")
        print("\nNo path found.")
    
    try:
        path_greedy, cost_greedy = guimaraes_graph.greedy(starting_node, finishing_node)
        print("\nGreedy Search Result:")
        print("Path:", path_greedy)
        print("Cost:", cost_greedy)
    except:
        print("\nGreedy Search Result:")
        print("\nNo path found.")
    
    try:
        path_bfs, cost_bfs = guimaraes_graph.procura_BFS(starting_node, finishing_node)
        print("\nBFS Search Result:")
        print("Path:", path_bfs)
        print("Cost:", cost_bfs)
    except:
        print("\nBFS Search Result:")
        print("\nNo path found.")
    
    try:
        path_dfs, cost_dfs = guimaraes_graph.procura_DFS(starting_node, finishing_node)
        print("\nDFS Search Result:")
        print("Path:", path_dfs)
        print("Cost:", cost_dfs)
    except:
        print("\nDFS Search Result:")
        print("\nNo path found.")
        
    try:
        path_uc, cost_uc = guimaraes_graph.procura_uniform_cost(starting_node, finishing_node)
        print("\nUniform cost Search Result:")
        print("Path:", path_uc)
        print("Cost:", cost_uc)
    except:
        print("\nUniform cost Search Result:")
        print("\nNo path found.")
        
    try:
        path_id, cost_id = guimaraes_graph.procura_IDDFS(starting_node, finishing_node)
        print("\nIterative Deepening Search Result:")
        print("Path:", path_id)
        print("Cost:", cost_id)
    except:
        print("\nIterative Deepening Search Result:")
        print("\nNo path found.")
        

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

def add_order(guimaraes_graph):
    print("===== Register Order =====")

    while True:
        client_name = input("Enter client's name: ")

        try:
            weight = float(input("Enter product weight (in kg): "))
        except ValueError:
            print("Invalid input for weight. Please enter a numeric value.")
            continue

        try:
            volume = float(input("Enter product volume (in cm3): "))
        except ValueError:
            print("Invalid input for volume. Please enter a numeric value.")
            continue

        while True:
            time_input = input("Enter processing time (Days, Hours, Minutes): ")
            try:
                days, hours, minutes = map(int, time_input.split(','))
                processing_time = days * 24 * 60 + hours * 60 + minutes
                break
            except ValueError:
                print("Invalid input for processing time. Please enter numeric values for days, hours, and minutes separated by commas.")
                continue

        starting_node = "Rua de Camoes"
        last_node = get_node_final(guimaraes_graph)

        order = Order(client_name, weight, volume, processing_time, starting_node, last_node)

        return order


def display_pending_orders(orders):
    print("===== Registered Orders =====")
    for order in orders:
        if(order.status == "Waiting"):
            print(f"Client: {order.client_name}, Weight: {order.weight} kg, Volume: {order.volume} cm3,"
                f" Processing Time: {order.processing_time} minutes, "
                f"Start Node: {order.starting_node}, Last Node: {order.last_node}")
    print("===============================")
    
def display_sent_orders(orders):
    print("===== Registered Orders =====")
    for order in orders:
        if(order.status == "Delivered"):
            print(f"Client: {order.client_name}, Weight: {order.weight} kg, Volume: {order.volume} cm3,"
                f" Processing Time: {order.processing_time} minutes, "
                f"Start Node: {order.starting_node}, Last Node: {order.last_node}")
    print("===============================")

def display_canceled_orders(orders):
    print("===== Registered Orders =====")
    for order in orders:
        if(order.status == "Cancelled"):
            print(f"Client: {order.client_name}, Weight: {order.weight} kg, Volume: {order.volume} cm3,"
                f" Processing Time: {order.processing_time} minutes, "
                f"Start Node: {order.starting_node}, Last Node: {order.last_node}")


def display_couriers(couriers):
    couriers.sort(key=lambda courier: courier.rating, reverse=True)
    print("===== Registered Couriers =====")
    for courier in couriers:
        print(f"Courier: {courier.name}, Transport: {courier.transport}, Rating: {courier.rating}")
        print(f"Rating List: {courier.ratinglist}")
    print("===============================")
    
def choose_best_algorithm(graph, starting_node, finishing_node, order_client_name):
    guimaraes_graph = graph
    cost = 0
    path = []
    
    print("\n\n===== Compare Search Algorithm Results =====")
    print("=====  Client: " + order_client_name + "  =====")
    
    try:
        path_a_star, cost_a_star = guimaraes_graph.procura_aStar(starting_node, finishing_node)
        print("\nA* Search Result:")
        print("Path:", path_a_star)
        print("Cost:", cost_a_star)
        cost = cost_a_star
        path = path_a_star
    except:
        print("\nNo A* path found.")
        
    print("__________________________________________________________")
    
    try:
        path_greedy, cost_greedy = guimaraes_graph.greedy(starting_node, finishing_node)
        print("\nGreedy Search Result:")
        print("Path:", path_greedy)
        print("Cost:", cost_greedy)
        if cost_greedy < cost:
            cost = cost_greedy
            path = path_greedy
    except:
        print("\nNo Greedy path found.")
        
    print("__________________________________________________________")
    
    
    try:
        path_bfs, cost_bfs = guimaraes_graph.procura_BFS(starting_node, finishing_node)
        print("\nBFS Search Result:")
        print("Path:", path_bfs)
        print("Cost:", cost_bfs)
        if cost_bfs < cost:
            cost = cost_bfs
            path = path_bfs
    except:
        print("\nNo BFS path found.")
        
    print("__________________________________________________________")
    
    
    try:
        path_dfs, cost_dfs = guimaraes_graph.procura_DFS(starting_node, finishing_node)
        print("\nDFS Search Result:")
        print("Path:", path_dfs)
        print("Cost:", cost_dfs)
        if cost_dfs < cost:
            cost = cost_dfs
            path = path_dfs
    except:
        print("\nNo DFS path found.")
        
    print("__________________________________________________________")
    
    
    try:
        path_uc, cost_uc = guimaraes_graph.procura_uniform_cost(starting_node, finishing_node)
        print("\nUniform cost Search Result:")
        print("Path:", path_uc)
        print("Cost:", cost_uc)
        if cost_uc < cost:
            cost = cost_uc
            path = path_uc
    except:
        print("\nNo Uniform cost path found.")
        
    print("__________________________________________________________")
    
    
    try:
        path_id, cost_id = guimaraes_graph.procura_IDDFS(starting_node, finishing_node)
        print("\nIterative Deepening Search Result:")
        print("Path:", path_id)
        print("Cost:", cost_id)
        if cost_id < cost:
            cost = cost_id
            path = path_id
    except:
        print("\nNo Iterative Deepening path found.")
        
    print("__________________________________________________________")
    
        
    return path, cost
        

def process_orders(orders, couriers, guimaraes_graph):
    starting_node = "Rua de Camoes"
    orders_processed = False

    print("\n===== Processing Orders =====")

    for order in orders:
        if order.status == "Waiting":
            path, cost = choose_best_algorithm(guimaraes_graph, starting_node, order.last_node, order.client_name)
            order.path = path
            order.cost = cost
            print(f"\nOrder for {order.client_name} has been processed.\nCost: {order.cost} km.\nPath: {order.path}.")
            orders_processed = True

    if not orders_processed:
        print("\nNo orders to process.")
        return

    print("\n===== Assigning Orders to Couriers =====")
    orders.sort(key=lambda order: order.tries, reverse=True)
    for order in orders:
        try:
            if order.path is not None and order.status == "Waiting":
                sorted_couriers = sorted(couriers, key=lambda courier: (
                courier.transport != "Bicycle", courier.transport != "Moto", courier.transport != "Car"))

                selected_courier = None
                for courier in sorted_couriers:
                    if courier.verifyWeight(order.weight) and courier.verifyTime(cost, order.processing_time, order.weight):
                        estimated_time = courier.getTime(cost, order.weight)
                        demanded_time = order.processing_time
                        if courier.can_combine_delivery(order) or len(courier.get_deliveries()) == 0:
                            selected_courier = courier
                            break

                if selected_courier is not None:
                    selected_courier.add_delivery(order)
                    price = order.calculate_price()
                    price = courier.calculate_price(price)
                    print(f"\nOrder for {order.client_name} added to courier {selected_courier.name}, with a cost of {price}.")
                    rating = calculate_rating_based_on_percentage_difference(estimated_time, demanded_time)
                    print(f"User gave courier {selected_courier.name} a rating of {rating}.")
                    selected_courier.calculate_rating(rating)
                    print(f"Courier {selected_courier.name} has now a rating of {selected_courier.rating}.")
                    order.status = "Delivered"
                else:
                    print(f"\nNo suitable courier found for order to {order.client_name}.")
                    order.tries += 1
                    if order.tries == 5:
                        print(f"Order to {order.client_name} has been cancelled. (Probably time set is impossible for delivery)")
                        order.status = "Cancelled"
        except:
            print(f"\nNo path found for order to {order.client_name}, better luck next time (Let's hope there aren't any roadblocks in the future).")


def calculate_rating_based_on_percentage_difference(estimated_time, demanded_time):
    percentage_difference = ((estimated_time - demanded_time) / demanded_time) * 100
    if percentage_difference <= -75:
        return 5
    if percentage_difference <= -45:
        return 4
    if percentage_difference <= -20:
        return 3
    if percentage_difference <= -5:
        return 2
    else:
        return 1
    
#checks if the days have passed and add them again to the graph
def verify_removed_edges(graph, removed_edges, current_date):
    for edge in removed_edges:
        node1 = edge[0]
        node2 = edge[1]
        weight = edge[2]
        release_date = edge[3]
        if release_date == current_date:
            graph.add_edge(node1, node2, weight)
            print("________________________________________________________________________________")
            print("                               \\\\\\\\ INFO ////                                   ")
            print("\n          Roadblock ended between " + node1 + " and " + node2 + ".")
            print("________________________________________________________________________________")
            return True
        else:
            return False