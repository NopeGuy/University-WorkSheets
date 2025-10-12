
# Health Planet Delivery System

<p align="center">
  <img src="https://github.com/NopeGuy/IA-2324/blob/main/circuit-animation.gif?raw=true" />
</p>

## Table of Contents
- [Overview](#overview)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Problem Formulation](#problem-formulation)
- [Tasks](#tasks)
- [Usage](#usage)
  - [Generating a Street Graph](#1-generating-a-street-graph)
  - [Adding Orders](#2-adding-orders)
  - [Comparing Search Algorithms](#3-comparing-search-algorithms)
- [Class Descriptions](#class-descriptions)
  - [Node](#node)
  - [Grafo](#grafo)
  - [Order](#order)
  - [Courier](#courier)
- [Graph Search Algorithms](#graph-search-algorithms)
- [Graph Visualization](#graph-visualization)
- [Contributors](#contributors)

## Overview

This project is part of the evaluation instrument for the "Search Algorithms" topic in the Artificial Intelligence course at the University of Minho. The objective is to address sustainability concerns by developing search algorithms for the Health Planet delivery system. The company aims to optimize its delivery routes using different modes of transportation, considering energy consumption and ecological impact.
This project provides functionality for working with street graphs and comparing various search algorithms. It includes classes for representing nodes, orders, and graphs, as well as functions for graph search algorithms such as A*, Greedy, BFS, DFS, etc.

## Getting Started

### Prerequisites
- Python 3.x
- NetworkX (for graph visualization)

### Installation
``bash
pip install networkx``


## Problem Formulation

The problem is formulated as a search problem, considering delivery routes in a city. Key components include:

- **Initial State:** The starting point of delivery routes.
- **Goal State:** The target delivery location or conditions.
- **Operators:** Actions or decisions influencing the state of the delivery system.
- **Cost Function:** Evaluation of the cost associated with a particular solution.

Different algorithms, both informed and uninformed, are implemented and compared. Strategies involve generating delivery circuits, representing delivery points as a graph, and utilizing search algorithms to find optimal routes. The impact of different heuristics on informed search algorithms is analyzed.

## Tasks

1. **Problem Formulation:**
    - Define the problem as a search problem.
    - Specify the representation of the initial state, goal state, operators, and solution cost.

2. **Delivery Circuits:**
    - Generate delivery circuits covering specific territories (streets or districts).

3. **Graph Representation:**
    - Represent delivery points as a graph.

4. **Search Algorithms:**
    - Implement various search strategies, both informed and uninformed.

5. **Results Comparison:**
    - Compare and analyze the results of different search algorithms.
    - Provide justification for the heuristics used in informed search algorithms.

6. **Path Presentation:**
    - Display the shortest path and its associated cost for each solution.
    - Present the path taken during the execution of each algorithm.

7. **Choice of the most Eco-Friendly Solution:**
    - The vehicle chosen is always the one with the smallest carbon footprint.











## Usage

1.  **Generating a Street Graph:**
    
The graph is automatically generated and can be edited by changing the "GuimaraesStreetGraphGenerator.py"
    
-   **Adding Orders and Couriers:**
    
Simply choose the respective option on the menu.
    
-   **Comparing Search Algorithms:**
    
Choose Dev Menu on the Main Menu and then Compare Search Algorithms.

## Class Descriptions

### Node

Represents a node in the street graph.

### Grafo

Represents a graph with functions for adding edges, performing graph search algorithms, and visualization.

### Order

Represents an order with client information and product details.

### Courier

Represents a courier with a specific transportation method and it's orders.


## Graph Search Algorithms

-   A* Search
-   Greedy Search
-   Breadth-First Search (BFS)
-   Depth-First Search (DFS)
-   Uniform cost Search
-   nIterative Deepening Search

## Graph Visualization

The graph can be visualized using NetworkX and Matplotlib.

`guimaraes_graph.desenha()` 

## Contributors

[@NopeGuy](https://github.com/NopeGuy) \
[@SenseiBott](https://github.com/SenseiBott) \
[@Gnomo03](https://github.com/Gnomo03) \
[@Bernazad](https://github.com/HBernaH) 
