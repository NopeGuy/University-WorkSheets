class Order:
    def __init__(self, client_name, weight, volume, processing_time, starting_node, last_node):
        self.client_name = client_name
        self.weight = weight
        self.volume = volume
        self.processing_time = processing_time
        self.starting_node = starting_node
        self.last_node = last_node
        self.path = []
        self.cost = 0
        self.status = "Waiting"
        self.tries = 0
        
    #calculates price of order by weight, volume and time   
    def calculate_price(self):
        return self.weight * 0.3 + self.volume * 0.1 + self.processing_time * 0.2