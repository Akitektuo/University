from random import randrange

from src.util.preferences import read_preferences


def get_random_range_by_width(width=read_preferences().get_width()):
    return randrange(width)


def get_random_range_by_height(height=read_preferences().get_height()):
    return randrange(height)


def get_random_range_tuple():
    return get_random_range_by_width(), get_random_range_by_height()
