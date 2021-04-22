import csv

from solution import run_solver


def read_data_from_csv(file_name, first_value, second_value, output_label):
    data = []
    with open(file_name) as csv_file:
        rows = list(csv.reader(csv_file))

        data_names = rows[0]
        for row in rows[1:]:
            data.append(row)

    inputs = []
    outputs = []
    for row in data:
        def get_value(label):
            return row[data_names.index(label)]

        inputs.append((float(get_value(first_value)), float(get_value(second_value))))
        outputs.append(str(get_value(output_label)))

    return inputs, outputs


def main():
    inputs, outputs = read_data_from_csv("dataset.csv", "val1", "val2", "label")

    run_solver(inputs, outputs)


if __name__ == '__main__':
    main()
