from asyncio import PriorityQueue
import copy
import math
from queue import Queue

import matplotlib.pyplot as plt
import networkx as nx

from graph.Node import Node 


class Grafo:

    def __init__(self, directed=False):
        self.m_nodes = []
        self.m_directed = directed
        self.m_graph = {}
        self.m_h = {}

    def __str__(self):
        out = ""
        for key in self.m_graph.keys():
            out = out + "node" + str(key) + ": " + str(self.m_graph[key]) + "\n"
        return out

    def get_node_by_name(self, name):
        search_node = Node(name)
        for node in self.m_nodes:
            if node == search_node:
                return node
        return None

    def imprime_aresta(self):
        listaA = ""
        lista = self.m_graph.keys()
        for nodo in lista:
            for (nodo2, custo) in self.m_graph[nodo]:
                listaA = listaA + nodo + " ->" + nodo2 + " custo:" + str(custo) + "\n"
        return listaA

    def add_edge(self, node1, node2, weight):
        n1 = Node(node1)
        n2 = Node(node2)
        if n1 not in self.m_nodes:
            self.m_nodes.append(n1)
            self.m_graph[node1] = list()
        else:
            n1 = self.get_node_by_name(node1)

        if n2 not in self.m_nodes:
            self.m_nodes.append(n2)
            self.m_graph[node2] = list()
        else:
            n2 = self.get_node_by_name(node2)

        self.m_graph[node1].append((node2, weight))

        if not self.m_directed:
            self.m_graph[node2].append((node1, weight))

    def getNodes(self):
        return self.m_nodes

    def get_arc_cost(self, node1, node2):
        custoT = math.inf
        a = self.m_graph[node1]
        for (nodo, custo) in a:
            if nodo == node2:
                custoT = custo
        return custoT

    def calcula_custo(self, caminho):
        teste = caminho
        custo = 0
        i = 0
        while i + 1 < len(teste):
            custo = custo + self.get_arc_cost(teste[i], teste[i + 1])
            i = i + 1
        return custo

    def getNeighbours(self, nodo):
        lista = []
        for (adjacente, peso) in self.m_graph[nodo]:
            lista.append((adjacente, peso))
        return lista
    
    def remove_edge(self, node1, node2):
        if node1 in self.m_graph and node2 in self.m_graph:
            for i, (n, weight) in enumerate(self.m_graph[node1]):
                if n == node2:
                    removed_weight = weight
                    del self.m_graph[node1][i]
                    break
            else:
                print(f"Edge not found: {node1}-{node2}")
                return None
                
            if not self.m_directed:
                for i, (n, weight) in enumerate(self.m_graph[node2]):
                    if n == node1:
                        del self.m_graph[node2][i]
                        break
            return removed_weight
        else:
            print(f"Nodes {node1} or {node2} not found in the graph.")
            return None


    def desenha(self):
        lista_v = self.m_nodes
        lista_a = []
        g = nx.Graph()
        for nodo in lista_v:
            n = nodo.getName()
            g.add_node(n)
            for (adjacente, peso) in self.m_graph[n]:
                lista = (n, adjacente)
                g.add_edge(n, adjacente, weight=peso)

        pos = nx.kamada_kawai_layout(g, scale=7.0)
            
        plt.figure(figsize=(20, 16))

        nx.draw_networkx(g, pos, with_labels=True, font_weight='bold', font_size=8)

        labels = nx.get_edge_attributes(g, 'weight')
        nx.draw_networkx_edge_labels(g, pos, edge_labels=labels)

        plt.draw()
        plt.show()





    def add_heuristica(self, n, estima):
        n1 = Node(n)
        if n1 in self.m_nodes:
            self.m_h[n] = estima

    def heuristica(self):
        nodos = self.m_graph.keys
        for n in nodos:
            self.m_h[n] = 1
        return True

    def calcula_est(self, estima):
        l = list(estima.keys())
        min_estima = estima[l[0]]
        node = l[0]
        for k, v in estima.items():
            if v < min_estima:
                min_estima = v
                node = k
        return node

    def getH(self, nodo):
        if nodo not in self.m_h.keys():
            return 1000
        else:
            return self.m_h[nodo]
        
    def calculate_turns_heuristic(self, current_node, goal_node):
        current_node_neighbors = set([neighbor for neighbor, _ in self.m_graph[current_node]])
        goal_node_neighbors = set([neighbor for neighbor, _ in self.m_graph[goal_node]])

        turns = len(current_node_neighbors.intersection(goal_node_neighbors))
        return turns


    def procura_aStar(self, start, end):
        open_list = {start}
        closed_list = set([])
        g = {}
        g[start] = 0
        parents = {}
        parents[start] = start
        n = None
        while len(open_list) > 0:
            calc_heurist = {}
            flag = 0
            for v in open_list:
                if n is None:
                    n = v
                else:
                    flag = 1
                    calc_heurist[v] = g[v] + self.calculate_turns_heuristic(v, end)
            if flag == 1:
                n = self.calcula_est(calc_heurist)
            if n is None:
                print('Path does not exist!')
                return None
            if n == end:
                reconst_path = []
                while parents[n] != n:
                    reconst_path.append(n)
                    n = parents[n]
                reconst_path.append(start)
                reconst_path.reverse()
                return (reconst_path, self.calcula_custo(reconst_path))
            for (m, weight) in self.getNeighbours(n):
                if m not in open_list and m not in closed_list:
                    open_list.add(m)
                    parents[m] = n
                    g[m] = g[n] + weight
                else:
                    if g[m] > g[n] + weight:
                        g[m] = g[n] + weight
                        parents[m] = n
                        if m in closed_list:
                            closed_list.remove(m)
                            open_list.add(m)
            open_list.remove(n)
            closed_list.add(n)
        print('Path does not exist!')
        return None

    def greedy(self, start, end):
        open_list = set([start])
        closed_list = set([])
        parents = {}
        parents[start] = start
        while len(open_list) > 0:
            n = None
            for v in open_list:
                if n is None or self.calculate_turns_heuristic(v, end) < self.calculate_turns_heuristic(n, end):
                    n = v
            if n is None:
                print('Path does not exist!')
                return None
            if n == end:
                reconst_path = []
                while parents[n] != n:
                    reconst_path.append(n)
                    n = parents[n]
                reconst_path.append(start)
                reconst_path.reverse()
                return (reconst_path, self.calcula_custo(reconst_path))
            for (m, weight) in self.getNeighbours(n):
                if m not in open_list and m not in closed_list:
                    open_list.add(m)
                    parents[m] = n
            open_list.remove(n)
            closed_list.add(n)
        print('Path does not exist!')
        return None

    def procura_BFS(self, start, end):
        visited = set()
        fila = Queue()
        fila.put(start)
        visited.add(start)
        parent = dict()
        parent[start] = None
        path_found = False
        while not fila.empty() and path_found == False:
            nodo_atual = fila.get()
            if nodo_atual == end:
                path_found = True
            else:
                for (adjacente, peso) in self.m_graph[nodo_atual]:
                    if adjacente not in visited:
                        fila.put(adjacente)
                        parent[adjacente] = nodo_atual
                        visited.add(adjacente)

        path = []
        if path_found:
            path.append(end)
            while parent[end] is not None:
                path.append(parent[end])
                end = parent[end]
            path.reverse()
            custo = self.calcula_custo(path)

        return (path, custo)

    def procura_DFS(self, start, end, path=None, visited = None):
        if visited is None:
            visited = set()
        if path is None:
            path = []
        path.append(start)
        visited.add(start)

        if start == end:
            custoT = self.calcula_custo(path)
            return (path, custoT)
        for (adjacente, peso) in self.m_graph[start]:
            if adjacente not in visited:
                resultado = self.procura_DFS(adjacente, end, path, visited)
                if resultado is not None:
                    return resultado
        path.pop()
        return None
        
    def procura_uniform_cost(self, start, end):
        open_list = {start}
        closed_list = set([])
        g = {}
        g[start] = 0
        parents = {}
        parents[start] = start

        while len(open_list) > 0:
            n = None
            for v in open_list:
                if n is None or g[v] + self.calculate_turns_heuristic(v, end) < g[n] + self.calculate_turns_heuristic(n, end):
                    n = v
            if n is None:
                print('Path does not exist!')
                return None

            if n == end:
                reconst_path = []
                while parents[n] != n:
                    reconst_path.append(n)
                    n = parents[n]
                reconst_path.append(start)
                reconst_path.reverse()
                return reconst_path, self.calcula_custo(reconst_path)

            for (m, weight) in self.getNeighbours(n):
                if m not in open_list and m not in closed_list:
                    open_list.add(m)
                    parents[m] = n
                    g[m] = g[n] + weight
                else:
                    if g[m] > g[n] + weight:
                        g[m] = g[n] + weight
                        parents[m] = n
                        if m in closed_list:
                            closed_list.remove(m)
                            open_list.add(m)

            open_list.remove(n)
            closed_list.add(n)

        print('Path does not exist!')
        return None

    def procura_IDDFS(self, start, end, max_depth=100):
        for depth in range(max_depth):
            result = self.depth_limited_DFS(start, end, depth)
            if result is not None:
                return result
        print('Path does not exist within the specified depth!')
        return None

    def depth_limited_DFS(self, start, end, depth, path=None, visited=None):
        if visited is None:
            visited = set()
        if path is None:
            path = []
        path.append(start)
        visited.add(start)

        if depth == 0 and start == end:
            custoT = self.calcula_custo(path)
            return path, custoT
        elif depth > 0:
            for (adjacente, peso) in self.m_graph[start]:
                if adjacente not in visited:
                    result = self.depth_limited_DFS(adjacente, end, depth - 1, path, visited)
                    if result is not None:
                        return result
        path.pop()
        return None
