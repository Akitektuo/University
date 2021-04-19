# -*- coding: utf-8 -*-
"""
In this file your task is to write the solver function!
"""

theta_ranges = {
    "NVB": (None, -40, -25),
    "NB": (-40, -25, -10),
    "N": (-20, -10, 0),
    "ZO": (-5, 0, 5),
    "P": (0, 10, 20),
    "PB": (10, 25, 40),
    "PVB": (25, 40, None)
}

omega_ranges = {
    "NB": (None, -8, -3),
    "N": (-6, -3, 0),
    "ZO": (-1, 0, 1),
    "P": (0, 3, 6),
    "PB": (3, 8, None)
}

fRanges = {
    "NVVB": (None, -32, -24),
    "NVB": (-32, -24, -16),
    "NB": (-24, -16, -8),
    "N": (-16, -8, 0),
    "Z": (-4, 0, 4),
    "P": (0, 8, 16),
    "PB": (8, 16, 24),
    "PVB": (16, 24, 32),
    "PVVB": (24, 32, None)
}

bValues = {key: value[1] for key, value in fRanges.items()}

fuzzy_table = {
    "NB": {
        "NB": "NVVB",
        "N": "NVB",
        "ZO": "NB",
        "P": "N",
        "PB": "Z"
    },
    "N": {
        "NB": "NVB",
        "N": "NB",
        "ZO": "N",
        "P": "Z",
        "PB": "P"
    },
    "ZO": {
        "NB": "NB",
        "N": "N",
        "ZO": "Z",
        "P": "P",
        "PB": "PB"
    },
    "P": {
        "NB": "N",
        "N": "Z",
        "ZO": "P",
        "P": "PB",
        "PB": "PVB"
    },
    "PB": {
        "NB": "Z",
        "N": "P",
        "ZO": "PB",
        "P": "PVB",
        "PB": "PVVB"
    },
    "PVB": {
        "NB": "P",
        "N": "PB",
        "ZO": "PVB",
        "P": "PVVB",
        "PB": "PVVB"
    },
    "NVB": {
        "N": "NVVB",
        "ZO": "NVB",
        "P": "NB",
        "PB": "N",
        "NB": "NVVB"
    }
}


def fuzz(x, left, middle, right):
    if left is not None and left <= x < middle:
        return (x - left) / (middle - left)
    if right is not None and middle <= x < right:
        return (right - x) / (right - middle)
    if (left is None and x <= middle) or (right is None and x >= middle):
        return 1
    return 0


def compute_values(value, ranges):
    to_return = dict()

    for key in ranges:
        to_return[key] = fuzz(value, *ranges[key])

    return to_return


def solver(theta, omega):
    theta_values = compute_values(theta, theta_ranges)
    omega_values = compute_values(omega, omega_ranges)

    f_values = dict()
    for theta_key in fuzzy_table:
        for omega_key, f_value in fuzzy_table[theta_key].items():
            value = min(theta_values[theta_key], omega_values[omega_key])
            if f_value not in f_values:
                f_values[f_value] = value
            else:
                f_values[f_value] = max(value, f_values[f_value])

    s = sum(f_values.values())
    if s == 0:
        return None

    return sum(f_values[fSet] * bValues[fSet] for fSet in f_values.keys()) / s
