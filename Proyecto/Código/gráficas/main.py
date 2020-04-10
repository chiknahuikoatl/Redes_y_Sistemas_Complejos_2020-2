import csv


def parseBoolCSV(b):
    return b == " True"

def leerPersonaje(personaje):
    suma = 0
    with open(personaje, newline='\n') as f:
        reader = csv.reader(f)
        for row in reader:
            suma += int(row[1])
            print(str(parseBoolCSV(row[2]) and True))
            print(row)
    return suma

# def extraerDatos():


v = leerPersonaje('../archivos/prueba.csv')

print("Suma: "+ str(v))

e = input()

print(e)

print("Escribe un número s'il vous plait")
e2 = 2 + int(input())

print("Ton numèro n'est pas: " + str(e2))


# Import matplotlib.pyplot
import matplotlib.pyplot as plt

# Compute the degree centrality of the Twitter network: deg_cent
deg_cent = nx.degree_centrality(T)

# Plot a histogram of the degree centrality distribution of the graph.
plt.figure()
plt.hist(list(deg_cent.values()))
plt.show()

# Plot a histogram of the degree distribution of the graph
plt.figure()
plt.hist(degrees)
plt.show()

# Plot a scatter plot of the centrality distribution and the degree distribution
plt.figure()
plt.scatter(degrees, list(deg_cent.values()))
plt.show()
