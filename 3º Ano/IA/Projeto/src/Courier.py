class Courier:
    def __init__(self, name, transport, weight=0, orders=None):
        self.name = name
        self.transport = transport
        self.weight = weight
        if orders is not None:
            self._deliveries.extend(orders)
        self.ratinglist = []
        self.rating = 0

    def add_delivery(self, order):
        self._deliveries.append(order)
        self.weight += order.weight

    def get_deliveries(self):
        return self._deliveries

    def verifyWeight(self, order_weight):
        if self.transport == "Bicycle":
            return order_weight + self.weight <= 5
        elif self.transport == "Moto":
            return order_weight + self.weight <= 20
        elif self.transport == "Car":
            return order_weight + self.weight <= 100
        else:
            return False
        
    def average_speed(self):
        if self.transport == "Bicycle":
            return 10
        elif self.transport == "Moto":
            return 35
        elif self.transport == "Car":
            return 50
        else:
            return 0
        
    def max_capacity(self):
        if self.transport == "Bicycle":
            return 5
        elif self.transport == "Moto":
            return 20
        elif self.transport == "Car":
            return 100
        else:
            return 0
        
    def calculateMaxTime(self, path_cost, order_weight):
            total_weight = self.weight + order_weight
            if self.transport == "Bicycle":
                return path_cost / (self.average_speed() - (0.6 * total_weight)) * 60
            elif self.transport == "Moto":
                return path_cost / (self.average_speed() - (0.5 * total_weight)) * 60
            elif self.transport == "Car":
                return path_cost / (self.average_speed() - (0.1 * total_weight)) * 60

    def verifyTime(self, path_cost, order_processing_time, order_weight):
        max_time = self.calculateMaxTime(path_cost, order_weight)
        return max_time <= order_processing_time

    def getTime(self, path_cost, order_weight):
        max_time = self.calculateMaxTime(path_cost, order_weight)
        return max_time

    def can_combine_delivery(self, new_order):
        for delivery in self._deliveries:
            if self.path_intersects(delivery, new_order):
                return True
        return False

    def path_intersects(self, order1, order2):
        last_node_order1 = order1.path[-1]
        last_node_order2 = order2.path[-1]

        return last_node_order1 in order2.path or last_node_order2 in order1.path

    def calculate_rating(self, user_rating):
        self.ratinglist.append(user_rating)
        average_rating = sum(self.ratinglist) / len(self.ratinglist)
        self.rating = round(average_rating)
        
    def calculate_price(self, order_price):
        if self.transport == "Bicycle":
            return order_price + 1
        elif self.transport == "Moto":
            return order_price + 2
        elif self.transport == "Car":
            return order_price + 5
        else:
            return 0