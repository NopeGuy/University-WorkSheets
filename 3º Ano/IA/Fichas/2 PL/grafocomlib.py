import math
from queue import Queue

import networkx as nx           # biblioteca de tratamento de grafos
import matplotlib.pyplot as plt # idem

from nodo import Nodo

#construtor

#number of edges

#adjacency matrix
#methods for adding edges
#methods for removing edges
#methods for searching a graph
def start():
    vertices = int(input("Enter the number of vertices: "))
    graph = Graph(vertices)
    
    while True:
        print("\nMenu:")
        print("1. Add edge")
        print("2. Display graph")
        print("3. Get node by name")
        print("4. Exit")
        choice = int(input("Enter your choice: "))

        if choice == 1:
            src = int(input("Enter source vertex: "))
            dest = int(input("Enter destination vertex: "))
            if 0 <= src < vertices and 0 <= dest < vertices:
                graph.add_edge(src, dest)
            else:
                print("Invalid vertex. Please try again.")

        elif choice == 2:
            print(graph.__str__())

        elif choice == 3:
            name = int(input("Enter the name of the node: "))  
            print(graph.get_node_by_name(1))
                  
        elif choice == 4:
            break

        else:
            print("Invalid choice. Please try again.")
    
class Graph:
    def __init__(self,directed=False):
        self.m_nodes=[]
        self.m_directed = directed
        self.m_graph ={}
        self.m_h = {}
        
    def __str__(self):
        out = ""
        for key in self.m_graph.keys():
            out = out + "node" + str(key) + ": " + str(self.m_graph[key]) + "\n"   
        return out
    
    def get_node_by_name(self, name):
        search_node = Nodo(name)
        for node in self.m_nodes:
            if node.value == search_node:
                return node
        return None
    
    def add_edge(self, node1, node2, weight=1):
        # Check if node1 exists, if not create it
        n1 = self.get_node_by_name(node1)
        if n1 is None:
            n1 = Nodo(node1)
            n1.set_id(len(self.m_nodes))
            self.m_nodes.append(n1)
            self.m_graph[node1] = set()

        # Check if node2 exists, if not create it
        n2 = self.get_node_by_name(node2)
        if n2 is None:
            n2 = Nodo(node2)
            n2.set_id(len(self.m_nodes))
            self.m_nodes.append(n2)
            self.m_graph[node2] = set()

        # Add edge between node1 and node2
        self.m_graph[node1].add((node2, weight))

        if not self.m_directed:
            # For undirected graph, add the edge in the other direction too
            self.m_graph[node2].add((node1, weight))

    #    if src in self.m_graph.keys():
    #        self.m_graph[src].append(dest)
    #    else:
    #        self.m_graph[src] = [dest]
    #    
    #    if not self.m_directed:
    #        if dest in self.m_graph.keys():
    #            self.m_graph[dest].append(src)
    #        else:
    #            self.m_graph[dest] = [src]
            

if __name__ == "__main__":
    start()
