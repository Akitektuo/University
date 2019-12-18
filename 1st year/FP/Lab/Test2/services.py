from repository import Repository
from validator import validate_address

class Services:

    def __init__(self, test = False):
        if test:
            self.addresses = Repository()
            self.drivers = Repository()
            return
        self.addresses = Repository("addresses.txt", Repository.TYPE_ADDRESS)
        self.drivers = Repository("drivers.txt", Repository.TYPE_DRIVER)

    def build_addresses(self):
        if self.addresses.is_empty():
            raise Exception("No addresses found")

        addresses_str = "Addresses:\n"
        for address in self.addresses.data:
            addresses_str += str(address) + "\n"
        return addresses_str

    def build_drivers(self):
        if self.drivers.is_empty():
            raise Exception("No drivers found")

        drivers_str = "Drivers:\n"
        for driver in self.drivers.data:
            drivers_str += str(driver) + "\n"
        return drivers_str

    def get_address(self, address_name):
        """
        Returns the address for the given address name

        Input: string - address name
        Output: Address
        """
        for address in self.addresses.data:
            if address.name == address_name:
                return address

    def get_distance(self, f, t):
        """
        Returns the distance between two objects

        Input: ojects
        Output: int - distance
        """
        return abs(f.x - t.x) + abs(f.y - t.y)

    def get_closest_drivers_for_address(self, address_name):
        """
        Returns all the drivers in ascending order of the distance between the given address

        Input: string - address name
        Output: string - list of drivers
        Errors: validation errors and no drivers
        """
        if self.drivers.is_empty():
            raise Exception("No drivers found")

        validate_address(address_name, self.addresses.data)

        address = self.get_address(address_name)

        sorted_drivers = sorted(self.drivers.data, key = lambda d: self.get_distance(address, d))

        drivers_str = "\nDrivers closest to " + address_name + ":\n"
        for driver in sorted_drivers:
            drivers_str += str(driver) + " with the distance of " + str(self.get_distance(address, driver)) + "\n"
        return drivers_str        

    def get_closest_drivers(self):
        if len(self.drivers.data) < 2:
            raise Exception("Not enough drivers")

        min_distance = 100000
        closest_drivers = []

        for driver1 in self.drivers.data:
            for driver2 in self.drivers.data:
                if driver1 == driver2:
                    continue

                distance = self.get_distance(driver1, driver2)
                if distance < min_distance:
                    closest_drivers.clear()
                    min_distance = distance
                    closest_drivers.append(driver1)
                    closest_drivers.append(driver2)
        
        result = "Closest drivers are:\n"
        for driver in closest_drivers:
            result += str(driver) + "\n"
        return result
