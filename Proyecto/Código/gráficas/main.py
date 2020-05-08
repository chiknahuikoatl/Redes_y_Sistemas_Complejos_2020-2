import csv
import networkx as nx
import itertools as it
import matplotlib.pyplot as plt


def parseBoolCSV(b):
    return b == " true"

def extraerDatos(direccion):
    with open(direccion, newline='\n') as f:
        reader = csv.reader(f)
        for row in reader:
            print(row)

def dineros(direccion):
    with open(direccion, newline='\n') as f:
        reader = csv.reader(f)
        itera = iter(reader)
        print(next(itera))
        suma = 0
        for row in reader:
            print(row)
            if(parseBoolCSV(row[2])):
                suma += int(row[3])
                print(row[3])
                print(str(suma))

def creaGrafica(direccion):
    G = nx.Graph()
    val = list()
    with open(direccion, newline='\n') as f:
        reader = csv.reader(f)
        its = iter(reader)
        print(next(its))
        i = 0
        for row in reader:
            print(row)
            G.add_node(i, coord="["+row[0]+", "+row[1]+"]")
            i += 1
            n = 0;
            if parseBoolCSV(row[2]): n += 1
            if parseBoolCSV(row[4]): n += 1
            if parseBoolCSV(row[5]): n += 1
            if parseBoolCSV(row[6]): n += 1
            if parseBoolCSV(row[7]): n += 1
            val.append(n)
        print(val)
        for p in list(it.combinations(range(0,len(val)),2)):
            print(p)
            if(val[p[0]] == val[p[1]]):
                G.add_edge(p[0], p[1])
    nx.draw_networkx(G, labels=coord)
    plt.savefig("cells.png")







print("Escribe la dirección de tu archivo")
prueba = "../archivos/Cell-00.csv" #input()

# extraerDatos(prueba)
# dineros(prueba)
creaGrafica(prueba)

# Import matplotlib.pyplot
# import matplotlib.pyplot as plt
#
# # Compute the degree centrality of the Twitter network: deg_cent
# deg_cent = nx.degree_centrality(T)
#
# # Plot a histogram of the degree centrality distribution of the graph.
# plt.figure()
# plt.hist(list(deg_cent.values()))
# plt.show()
#
# # Plot a histogram of the degree distribution of the graph
# plt.figure()
# plt.hist(degrees)
# plt.show()
#
# # Plot a scatter plot of the centrality distribution and the degree distribution
# plt.figure()
# plt.scatter(degrees, list(deg_cent.values()))
# plt.show()
