from random import uniform

import numpy

from plotting import generate_centroid_to_class, plot_all

COLORS = {
    "A": "blue",
    "B": "green",
    "C": "red",
    "D": "yellow"
}

MIN = -99999
MAX = 99999


def get_domain(inputs):
    first_min = MAX
    first_max = MIN
    second_min = MAX
    second_max = MIN

    for first, second in inputs:
        if first < first_min:
            first_min = first
        if first > first_max:
            first_max = first
        if second < second_min:
            second_min = second
        if second > second_max:
            second_max = second

    return (first_min, second_min), (first_max, second_max)


def generate_centroids(domain, number_of_centroids=4):
    centroids = set()
    (x_min, y_min), (x_max, y_max) = domain

    while len(centroids) < number_of_centroids:
        centroids.add((uniform(x_min, x_max), uniform(y_min, y_max)))

    return centroids


def euclidean_distance(left_position, right_position):
    return numpy.linalg.norm(numpy.array(left_position) - numpy.array(right_position))


def evaluate_classification(real, computed, label_names, count):
    accuracy_count = 0
    for realLabel, computedLabel in zip(real, computed):
        if realLabel == computedLabel:
            accuracy_count += 1
    accuracy = accuracy_count / count

    precisions = {}
    rappel_data = {}
    score = {}

    for label in label_names:
        correct_count = 0
        precision_count = 0
        rappel_count = 0

        for realLabel, computedLabel in zip(real, computed):
            if realLabel == computedLabel == label:
                correct_count += 1
            if computedLabel == label:
                precision_count += 1
            if realLabel == label:
                rappel_count += 1

        precision = correct_count / precision_count
        rappel = correct_count / rappel_count

        precisions[label] = precision
        rappel_data[label] = rappel
        score[label] = 2 * precision * rappel / (precision + rappel)

    return {"accuracy": accuracy, "precision": precisions, "rappel": rappel_data, "score": score}


def display_results(centroid_to_inputs, inputs, outputs):
    centroid_to_class = generate_centroid_to_class(centroid_to_inputs)

    input_to_computed = {}
    for centroid, sets in centroid_to_inputs.items():
        for row in sets:
            input_to_computed[row] = centroid_to_class[centroid]
    computed_labels = [input_to_computed[row] for row in inputs]

    for val, result in evaluate_classification(outputs, computed_labels, COLORS.keys(), len(inputs)).items():
        print(f"{val}: {result}")


def solver(centroids_set, inputs, outputs):
    centroids = list(centroids_set)
    did_change = True
    centroid_to_inputs = {centroid: [] for centroid in centroids}

    while did_change:
        did_change = False
        input_to_centroid = {}
        for row in inputs:
            input_to_centroid[row] = min(centroids, key=lambda centroid: euclidean_distance(row, centroid))

        centroid_to_inputs = {}
        new_centroids = []
        for centroid in centroids:
            x_sum = 0
            y_sum = 0
            count = 0

            centroid_to_inputs[centroid] = []
            for row, inputCentroid in input_to_centroid.items():
                if inputCentroid == centroid:
                    x_sum += row[0]
                    y_sum += row[1]
                    count += 1

                    centroid_to_inputs[centroid].append(row)
            new_centroids.append((x_sum / count, y_sum / count))

        invalid_count = len(centroids_set) - len(new_centroids)
        if invalid_count > 0:
            new_centroids += list(generate_centroids(get_domain(inputs), invalid_count))

        for newCentroid in new_centroids:
            did_change = did_change or newCentroid not in centroids

        if did_change:
            centroids = new_centroids
        else:
            display_results(centroid_to_inputs, inputs, outputs)

        points = plot_all(inputs, outputs, centroid_to_inputs, COLORS)
        if did_change:
            points.remove()

    return centroid_to_inputs


def run_solver(inputs, outputs):
    solver(generate_centroids(get_domain(inputs)), inputs, outputs)
