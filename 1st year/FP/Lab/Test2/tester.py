from services import Services
from domains import Driver, Address
import unittest

class TestClosestDriversToAddress(unittest.TestCase):
    
    def test_no_drivers(self):
        test_services = Services(True)

        self.assertRaises(Exception, lambda: test_services.get_closest_drivers_for_address(""))

    def test_empty_address(self):
        test_services = Services(True)
        test_services.drivers.data.append(Driver("Ion", 10, 10))

        self.assertRaises(Exception, lambda: test_services.get_closest_drivers_for_address(""))

    def test_address_not_existent(self):
        test_services = Services(True)
        test_services.drivers.data.append(Driver("Ion", 10, 10))

        self.assertRaises(Exception, lambda: test_services.get_closest_drivers_for_address("test"))

    def test_address_existent(self):
        test_services = Services(True)
        test_services.drivers.data.append(Driver("Ion", 10, 10))
        test_services.addresses.data.append(Address(1, "test", 20, 20))

        test_services.get_closest_drivers_for_address("test")

unittest.main()