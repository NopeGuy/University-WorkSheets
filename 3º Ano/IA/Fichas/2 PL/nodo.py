class Nodo:
    def __init__(self, value):
        self.value = value
        self.next = None
        self.id = None

    def set_id(self, node_id):
        self.id = node_id