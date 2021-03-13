from src.util.constants import Properties


class Preferences:
    def __init__(self):
        self._loaded = False
        self.__preferences = {}

    def _load(self):
        with open("settings.properties", "r") as settings:
            parsed_preferences = map(lambda preference: preference.strip().split(" = "),
                                     filter(lambda preference: " = " in preference,
                                            settings.readlines()))
            self.__preferences = {preference[0]: preference[1] for preference in parsed_preferences}
            self._loaded = True

    def get_logo_image(self) -> str:
        return self.__preferences[Properties.LOGO_IMAGE]

    def get_drone_image(self) -> str:
        return self.__preferences[Properties.DRONE_IMAGE]

    def get_title(self) -> str:
        return self.__preferences[Properties.TITLE]

    def get_width(self) -> int:
        return int(self.__preferences[Properties.WIDTH])

    def get_height(self) -> int:
        return int(self.__preferences[Properties.HEIGHT])

    def get_map_file(self) -> str:
        return self.__preferences[Properties.MAP_FILE]

    def __str__(self):
        return str(self.__preferences)


preferences = Preferences()


# noinspection PyProtectedMember
def read_preferences() -> Preferences:
    if not preferences._loaded:
        preferences._load()

    return preferences
