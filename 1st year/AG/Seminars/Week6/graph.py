import random
import time


class MatrixGraph:
	"""A directed graph, represented by adjacency matrix.
	Vertices are numbers from 0 to n-1"""

	def __init__(self, n):
		"""Creates a graph with n vertices (numbered from 0 to n-1)
		and no edges"""
		self._matrix = []
		for i in range(n):
			self._matrix.append([])
			for j in range(n):
				self._matrix[i].append(False)

	def parse_x(self):
		"""Returns an iterable containing all the vertices"""
		nr_of_vertices = len(self._matrix)
		return range(nr_of_vertices)

	def parse_n_out(self, x):
		"""Returns an iterable containing the outbound neighbours of x"""
		list_n_out = []
		for i in range(len(self._matrix[x])):
			if self._matrix[x][i]:
				list_n_out.append(i)
		return list_n_out

	def parse_n_in(self, x):
		"""Returns an iterable containing the inbound neighbours of x"""
		list_n_in = []
		for i in range(len(self._matrix)):
			if self._matrix[i][x]:
				list_n_in.append(i)
		return list_n_in

	def is_edge(self, x, y):
		"""Returns True if there is an edge from x to y, False otherwise"""
		return self._matrix[x][y]

	def add_edge(self, x, y):
		"""Adds an edge from x to y.
		Precondition: there is no edge from x to y"""
		self._matrix[x][y] = True


class DictGraph:
	"""A directed graph, represented as a map from each vertex to
	the set of outbound neighbours"""

	def __init__(self, n):
		"""Creates a graph with n vertices (numbered from 0 to n-1)
		and no edges"""
		self._dict = {}
		for i in range(n):
			self._dict[i] = []

	def parse_x(self):
		"""Returns an iterable containing all the vertices"""
		return self._dict.keys()

	def parse_n_out(self, x):
		"""Returns an iterable containing the outbound neighbours of x"""
		return self._dict[x]

	def parse_n_in(self, x):
		"""Returns an iterable containing the inbound neighbours of x"""
		list_n_in = []
		for i in self._dict.keys():
			if x in self._dict[i]:
				list_n_in.append(i)
		return list_n_in

	def is_edge(self, x, y):
		"""Returns True if there is an edge from x to y, False otherwise"""
		return y in self._dict[x]

	def add_edge(self, x, y):
		"""Adds an edge from x to y.
		Precondition: there is no edge from x to y"""
		self._dict[x].append(y)


class DoubleDictGraph:
	"""A directed graph, represented as two maps,
	one from each vertex to the set of outbound neighbours,
	the other from each vertex to the set of inbound neighbours"""

	def __init__(self, n):
		"""Creates a graph with n vertices (numbered from 0 to n-1)
		and no edges"""
		self._dict_out = {}
		self._dict_in = {}
		for i in range(n):
			self._dict_out[i] = []
			self._dict_in[i] = []

	def parse_x(self):
		"""Returns an iterable containing all the vertices"""
		return self._dict_out.keys()

	def parse_n_out(self, x):
		"""Returns an iterable containing the outbound neighbours of x"""
		return self._dict_out[x]

	def parse_n_in(self, x):
		"""Returns an iterable containing the inbound neighbours of x"""
		return self._dict_in[x]

	def is_edge(self, x, y):
		"""Returns True if there is an edge from x to y, False otherwise"""
		return y in self._dict_out[x]

	def add_edge(self, x, y):
		"""Adds an edge from x to y.
		Precondition: there is no edge from x to y"""
		self._dict_out[x].append(y)
		self._dict_in[y].append(x)


def accessible(graph, vertex):
	"""Returns the set of vertices of the graph that are accessible
	from the vertex"""
	acc = []
	tree = {}
	acc.append(vertex)
	vertices = [vertex]
	while len(vertices) > 0:
		x = vertices[0]
		vertices = vertices[1:]
		for y in graph.parse_n_out(x):
			if y not in acc:
				acc.append(y)
				vertices.append(y)
				tree[y] = x
	return acc, tree


def is_valid_bank(i):
	return (i & 4) == 0 or (i & 1) == 1 or ((i & 2) == 0 and (i & 8) == 0)


class GoatStatus:
	def __init__(self, i):
		self._status = i

	def __str__(self):
		return self.str_x(~self._status) + "/" + self.str_x(self._status)

	def __eq__(self, other):
		if isinstance(other, self.__class__):
			return self.__dict__ == other.__dict__
		else:
			return False

	def __ne__(self, other):
		return not self.__eq__(other)

	def __hash__(self):
		return self._status

	def is_valid(self):
		"""True if nobody eats nobody in this state"""
		return is_valid_bank(self._status) and is_valid_bank(~self._status)

	def parse_n(self):
		ret = []
		for i in range(4):
			if (self._status & 1) == ((self._status >> i) & 1):
				ns = self._status ^ ((1 << i) | 1)
				status = GoatStatus(ns)
				if status.is_valid():
					ret.append(status)
		return ret

	def str_x(self, i):
		ret = "("
		for j in range(4):
			if (i & (1 << j)) != 0:
				ret = ret + " " + self.names[j]
		return ret + ")"

	names = ("boat", "cabbage", "goat", "wolf")


class GoatGraph:
	@staticmethod
	def parse_x():
		ret = []
		for i in range(16):
			status = GoatStatus(i)
			if status.is_valid():
				ret.append(status)
		return ret

	@staticmethod
	def parse_n_out(status):
		return status.parse_n()

	@staticmethod
	def parse_n_in(status):
		return status.parse_n()


def init_graph(ctor):
	"""Constructs and returns a hard-coded sample graph.
	ctor must be a callable that gets the number of vertices and
	creates a graph with the given number of vertices and with no edges"""
	g = ctor(5)
	g.add_edge(0, 1)
	g.add_edge(1, 0)
	g.add_edge(1, 1)
	g.add_edge(1, 2)
	g.add_edge(4, 0)
	g.add_edge(4, 2)
	return g


def init_random_graph(ctor, n, m):
	"""Constructs and returns a randomly generated graph
	with n vertices and m edges.
	ctor must be a callable that gets the number of vertices and
	creates a graph with the given number of vertices and with no edges"""
	g = ctor(n)
	added_edges = 0
	while added_edges < m:
		x = random.randrange(0, n)
		y = random.randrange(0, n)
		if not g.is_edge(x, y):
			g.add_edge(x, y)
			added_edges += 1
	return g


def run(graph):
	for x in graph.parse_x():
		for _ in graph.parse_n_in(x):
			pass
		for _ in graph.parse_n_out(x):
			pass


def print_result(type_of_representation, n, start_time):
	print(str(type_of_representation) + " with n = " + str(n) + " executed in " + str(
		time.time() - start_time) + " seconds")


def run_simulation(type_of_representation, n):
	start_time = time.time()
	matrix_graph = init_random_graph(type_of_representation, n, n * 10)
	run(matrix_graph)
	print_result(type_of_representation, n, start_time)


def get_tree(graph, start_vertex):
	tree = {}
	root = start_vertex
	visited_vertices = set()
	queue = []

	tree[root] = []
	visited_vertices.add(start_vertex)
	queue.append(start_vertex)

	while len(queue):
		source_vertex = queue.pop(0)
		for neighbor_vertex in graph.parse_n_out(source_vertex):
			if neighbor_vertex not in visited_vertices:
				tree[neighbor_vertex] = []
				visited_vertices.add(neighbor_vertex)
				queue.append(neighbor_vertex)
				tree[source_vertex].append(neighbor_vertex)

	return tree, root


def print_tree(tree, root, tab):
	print(tab + str(root))
	for children in tree[root]:
		print_tree(tree, children, tab + "    ")


def find_path(graph, start, end):
	tree = get_tree(graph, start)[0]
	if end not in tree.keys():
		print("No path found between " + str(start) + " and " + str(end))
	else:
		print("There is a path between " + str(start) + " and " + str(end))


if __name__ == "__main__":
	graph = init_graph(DoubleDictGraph)
	tree = get_tree(graph, 4)
	print(tree[0], tree[1])
	print_tree(tree[0], tree[1], "")
	find_path(graph, 4, 1)
