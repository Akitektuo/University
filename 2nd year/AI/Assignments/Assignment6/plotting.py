from matplotlib import pyplot


def plot_data(inputs, outputs, colors):
    for color in colors:
        ox = []
        oy = []
        for index, (x, y) in enumerate(inputs):
            if outputs[index] == color:
                ox.append(x)
                oy.append(y)

        pyplot.scatter(ox, oy, label="stars", color=colors[color], marker="*", s=10)


def plot_centroids(centroids, color="purple"):
    ox = []
    oy = []
    for x, y in centroids:
        ox.append(x)
        oy.append(y)

    return pyplot.scatter(ox, oy, label="stars", color=color, marker="*", s=100)


def plot_computed_data(centroid_to_inputs, centroid_to_class, colors):
    for centroid, inputs in centroid_to_inputs.items():
        ox = []
        oy = []
        for x, y in inputs:
            ox.append(x)
            oy.append(y)

        pyplot.scatter(ox, oy, label="stars", color=colors[centroid_to_class[centroid]], marker="*", s=10)

    return plot_centroids(centroid_to_inputs.keys())


def generate_centroid_to_class(centroid_to_inputs):
    centroids = list(centroid_to_inputs.keys())
    left_most = min(centroids, key=lambda x: x[0])
    centroids.remove(left_most)

    right_most = max(centroids, key=lambda x: x[0])
    centroids.remove(right_most)

    bottom_most = min(centroids, key=lambda x: x[1])
    centroids.remove(bottom_most)

    top_most = centroids[0]
    return {left_most: 'A', top_most: 'B', right_most: 'C', bottom_most: 'D'}


def plot_all(inputs, outputs, centroid_to_inputs, colors):
    centroid_to_class = generate_centroid_to_class(centroid_to_inputs)
    pyplot.subplot(1, 2, 2)
    plot_data(inputs, outputs, colors)
    pyplot.title('Ideal outputs')

    pyplot.subplot(1, 2, 1)
    points = plot_computed_data(centroid_to_inputs, centroid_to_class, colors)
    pyplot.title('Computed outputs')
    pyplot.pause(0.5)

    return points
