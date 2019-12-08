import unittest
from controller import Controller

class ControllerTest(unittest.TestCase):

    def test_preload(self):
        controller = Controller()
        services = controller.services
        controller.preload_list()

        self.assertEqual(services.get_students_count(), 10)
        self.assertEqual(services.get_disciplines_count(), 10)
        self.assertEqual(services.get_grades_count(), 10)


    def test_add_student(self):
        controller = Controller()
        services = controller.services

        self.assertRaises(Exception, lambda: controller.add([]))
        self.assertRaises(Exception, lambda: controller.add(["", "", ""]))
        self.assertRaises(Exception, lambda: controller.add(["abc", "abc", "abc"]))
        self.assertRaises(Exception, lambda: controller.add(["student", "abc", "abc"]))
        self.assertRaises(Exception, lambda: controller.add(["student", "0", "abc"]))
        controller.add(["student", "0", "test"])

        student1 = services.get_student(0)
        self.assertEqual(student1.id, 0)
        self.assertEqual(student1.name, "test")
        self.assertEqual(services.get_students_count(), 1)

        self.assertRaises(Exception, lambda: controller.add(["student", "0", "testing"]))
        controller.add(["student", "1", "test"])

        student2 = services.get_student(1)
        self.assertEqual(student2.id, 1)
        self.assertEqual(student2.name, "test")
        self.assertEqual(services.get_students_count(), 2)

        self.assertNotEqual(student1, student2)


    def test_add_discipline(self):
        controller = Controller()
        services = controller.services

        self.assertRaises(Exception, lambda: controller.add([]))
        self.assertRaises(Exception, lambda: controller.add(["", "", ""]))
        self.assertRaises(Exception, lambda: controller.add(["abc", "abc", "abc"]))
        self.assertRaises(Exception, lambda: controller.add(["discipline", "abc", "abc"]))
        self.assertRaises(Exception, lambda: controller.add(["discipline", "0", "abc"]))
        controller.add(["discipline", "0", "test"])

        discipline1 = services.get_discipline(0)
        self.assertEqual(discipline1.id, 0)
        self.assertEqual(discipline1.name, "test")
        self.assertEqual(services.get_disciplines_count(), 1)

        self.assertRaises(Exception, lambda: controller.add(["discipline", "0", "testing"]))
        controller.add(["discipline", "1", "test"])

        discipline2 = services.get_discipline(1)
        self.assertEqual(discipline2.id, 1)
        self.assertEqual(discipline2.name, "test")
        self.assertEqual(services.get_disciplines_count(), 2)

        self.assertNotEqual(discipline1, discipline2)


    def test_remove_student(self):
        controller = Controller()
        services = controller.services

        controller.add(["discipline", "0", "discipline test"])
        controller.add(["student", "1", "student 1 test"])
        controller.add(["student", "2", "student 2 test"])
        controller.grade(["1", "0", "10"])
        controller.grade(["1", "0", "6"])
        controller.grade(["2", "0", "9"])
        controller.grade(["2", "0", "7"])
        controller.grade(["2", "0", "8"])

        self.assertEqual(services.get_students_count(), 2)
        self.assertEqual(services.get_disciplines_count(), 1)
        self.assertEqual(services.get_grades_count(), 5)

        self.assertRaises(Exception, lambda: controller.remove([]))
        self.assertRaises(Exception, lambda: controller.remove(["", ""]))
        self.assertRaises(Exception, lambda: controller.remove(["abc", "abc"]))
        self.assertRaises(Exception, lambda: controller.remove(["student", "abc"]))
        self.assertRaises(Exception, lambda: controller.remove(["student", "0"]))
        controller.remove(["student", "2"])

        self.assertEqual(services.get_students_count(), 1)
        self.assertEqual(services.get_disciplines_count(), 1)
        self.assertEqual(services.get_grades_count(), 2)
        self.assertRaises(Exception, lambda: controller.remove(["student", "2"]))


    def test_remove_discipline(self):
        controller = Controller()
        services = controller.services

        controller.add(["student", "0", "student test"])
        controller.add(["discipline", "1", "discipline 1 test"])
        controller.add(["discipline", "2", "discipline 2 test"])
        controller.grade(["0", "1", "10"])
        controller.grade(["0", "1", "6"])
        controller.grade(["0", "2", "9"])
        controller.grade(["0", "2", "7"])
        controller.grade(["0", "2", "8"])

        self.assertEqual(services.get_students_count(), 1)
        self.assertEqual(services.get_disciplines_count(), 2)
        self.assertEqual(services.get_grades_count(), 5)

        self.assertRaises(Exception, lambda: controller.remove([]))
        self.assertRaises(Exception, lambda: controller.remove(["", ""]))
        self.assertRaises(Exception, lambda: controller.remove(["abc", "abc"]))
        self.assertRaises(Exception, lambda: controller.remove(["discipline", "abc"]))
        self.assertRaises(Exception, lambda: controller.remove(["discipline", "0"]))
        controller.remove(["discipline", "2"])

        self.assertEqual(services.get_students_count(), 1)
        self.assertEqual(services.get_disciplines_count(), 1)
        self.assertEqual(services.get_grades_count(), 2)
        self.assertRaises(Exception, lambda: controller.remove(["discipline", "2"]))


    def test_update_student(self):
        controller = Controller()
        services = controller.services

        controller.add(["student", "1", "student test"])
        self.assertEqual(services.get_students_count(), 1)
        student = services.get_student(1)
        self.assertEqual(student.id, 1)
        self.assertEqual(student.name, "student test")

        self.assertRaises(Exception, lambda: controller.update([]))
        self.assertRaises(Exception, lambda: controller.update(["", "", ""]))
        self.assertRaises(Exception, lambda: controller.update(["abc", "abc", "abc"]))
        self.assertRaises(Exception, lambda: controller.update(["student", "abc", "abc"]))
        self.assertRaises(Exception, lambda: controller.update(["student", "0", "abc"]))
        self.assertRaises(Exception, lambda: controller.update(["student", "1", "abc"]))
        controller.update(["student", "1", "test"])

        self.assertEqual(student.id, 1)
        self.assertEqual(student.name, "test")
        self.assertEqual(services.get_students_count(), 1)


    def test_update_discipline(self):
        controller = Controller()
        services = controller.services

        controller.add(["discipline", "1", "discipline test"])
        self.assertEqual(services.get_disciplines_count(), 1)
        discipline = services.get_discipline(1)
        self.assertEqual(discipline.id, 1)
        self.assertEqual(discipline.name, "discipline test")

        self.assertRaises(Exception, lambda: controller.update([]))
        self.assertRaises(Exception, lambda: controller.update(["", "", ""]))
        self.assertRaises(Exception, lambda: controller.update(["abc", "abc", "abc"]))
        self.assertRaises(Exception, lambda: controller.update(["discipline", "abc", "abc"]))
        self.assertRaises(Exception, lambda: controller.update(["discipline", "0", "abc"]))
        self.assertRaises(Exception, lambda: controller.update(["discipline", "1", "abc"]))
        controller.update(["discipline", "1", "test"])

        self.assertEqual(discipline.id, 1)
        self.assertEqual(discipline.name, "test")
        self.assertEqual(services.get_disciplines_count(), 1)


    def test_list_all(self):
        controller = Controller()
        services = controller.services

        self.assertRaises(Exception, lambda: controller.list_all())

        controller.add(["student", "0", "stud 1"])
        self.assertEqual(services.get_students_count(), 1)

        self.assertNotIn('|', controller.list_all())

        services.clear()
        self.assertEqual(services.get_students_count(), 0)
        controller.add(["discipline", "1", "discipline 1"])
        self.assertEqual(services.get_disciplines_count(), 1)

        self.assertNotIn('|', controller.list_all())

        controller.add(["student", "2", "stud 1"])
        self.assertEqual(services.get_students_count(), 1)
        controller.add(["student", "3", "stud 2"])
        self.assertEqual(services.get_students_count(), 2)

        self.assertIn('|', controller.list_all())


    def test_grade(self):
        controller = Controller()
        services = controller.services

        controller.add(["student", "1", "stud 1"])
        controller.add(["discipline", "1", "discipline 1"])

        self.assertEqual(services.get_students_count(), 1)
        self.assertEqual(services.get_disciplines_count(), 1)

        self.assertRaises(Exception, lambda: controller.grade([]))
        self.assertRaises(Exception, lambda: controller.grade(["", "", ""]))
        self.assertRaises(Exception, lambda: controller.grade(["abc", "abc", "abc"]))
        self.assertRaises(Exception, lambda: controller.grade(["0", "abc", "abc"]))
        self.assertRaises(Exception, lambda: controller.grade(["1", "abc", "abc"]))
        self.assertRaises(Exception, lambda: controller.grade(["1", "0", "abc"]))
        self.assertRaises(Exception, lambda: controller.grade(["1", "1", "abc"]))
        self.assertRaises(Exception, lambda: controller.grade(["1", "1", "0"]))
        self.assertRaises(Exception, lambda: controller.grade(["1", "1", "11"]))

        controller.grade(["1", "1", "1"])
        controller.grade(["1", "1", "10"])
        controller.grade(["1", "1", "5"])

        self.assertEqual(services.get_grades_count(), 3)


    def test_search(self):
        controller = Controller()
        services = controller.services

        controller.add(["student", "1", "stud 1"])
        controller.add(["student", "2", "stud 2"])
        controller.add(["discipline", "1", "discipline"])

        self.assertEqual(services.get_students_count(), 2)
        self.assertEqual(services.get_disciplines_count(), 1)

        self.assertRaises(Exception, lambda: controller.search([]))
        self.assertRaises(Exception, lambda: controller.search([""]))
        self.assertRaises(Exception, lambda: controller.search(["magic"]))

        self.assertNotIn('|', controller.search(["stud"]))
        self.assertIn('|', controller.search(["1"]))


    def test_failing_students(self):
        controller = Controller()
        services = controller.services

        controller.add(["student", "1", "stud 1"])
        controller.add(["student", "2", "stud 2"])
        controller.add(["student", "3", "stud 3"])
        controller.add(["discipline", "1", "discipline 1"])
        controller.add(["discipline", "2", "discipline 2"])
        controller.grade(["1", "1", "10"])
        controller.grade(["2", "1", "10"])
        controller.grade(["2", "1", "1"])
        controller.grade(["2", "1", "4"])
        controller.grade(["2", "2", "5"])

        self.assertEqual(services.get_students_count(), 3)
        self.assertEqual(services.get_disciplines_count(), 2)
        self.assertEqual(services.get_grades_count(), 5)

        self.assertRaises(Exception, lambda: controller.see_failing_students())
        
        controller.grade(["3", "1", "1"])
        controller.grade(["3", "1", "2"])
        controller.grade(["3", "1", "3"])
        controller.grade(["3", "1", "4"])
        controller.grade(["3", "2", "5"])
        self.assertEqual(services.get_grades_count(), 10)

        self.assertIn("\n", controller.see_failing_students())


    def test_best_students(self):
        controller = Controller()
        services = controller.services

        controller.add(["student", "1", "stud 1"])
        controller.add(["student", "2", "stud 2"])
        controller.add(["student", "3", "stud 3"])
        controller.add(["discipline", "1", "discipline 1"])
        controller.add(["discipline", "2", "discipline 2"])

        self.assertEqual(services.get_students_count(), 3)
        self.assertEqual(services.get_disciplines_count(), 2)

        self.assertRaises(Exception, lambda: controller.see_best_students())
        
        controller.grade(["1", "1", "10"])
        controller.grade(["2", "1", "10"])
        controller.grade(["2", "1", "1"])
        controller.grade(["2", "1", "4"])
        controller.grade(["2", "2", "5"])
        controller.grade(["3", "1", "1"])
        controller.grade(["3", "1", "2"])
        controller.grade(["3", "1", "3"])
        controller.grade(["3", "1", "4"])
        controller.grade(["3", "2", "5"])
        self.assertEqual(services.get_grades_count(), 10)

        self.assertIn("\n", controller.see_best_students())


    def test_disciplines_with_grades(self):
        controller = Controller()
        services = controller.services

        controller = Controller()
        services = controller.services

        controller.add(["student", "1", "stud 1"])
        controller.add(["student", "2", "stud 2"])
        controller.add(["student", "3", "stud 3"])
        controller.add(["discipline", "1", "discipline 1"])
        controller.add(["discipline", "2", "discipline 2"])

        self.assertEqual(services.get_students_count(), 3)
        self.assertEqual(services.get_disciplines_count(), 2)

        self.assertRaises(Exception, lambda: controller.see_grades())
        
        controller.grade(["1", "1", "10"])
        controller.grade(["2", "1", "10"])
        controller.grade(["2", "1", "1"])
        controller.grade(["2", "1", "4"])
        controller.grade(["2", "2", "5"])
        controller.grade(["3", "1", "1"])
        controller.grade(["3", "1", "2"])
        controller.grade(["3", "1", "3"])
        controller.grade(["3", "1", "4"])
        controller.grade(["3", "2", "5"])
        self.assertEqual(services.get_grades_count(), 10)

        self.assertIn("\n", controller.see_grades())


    def test_undo(self):
        controller = Controller()
        services = controller.services

        self.assertRaises(Exception, lambda: controller.undo())

        controller.add(["student", "1", "stud 1"])
        controller.add(["student", "2", "stud 2"])
        controller.add(["student", "3", "stud 3"])
        controller.add(["discipline", "1", "discipline 1"])
        controller.add(["discipline", "2", "discipline 2"])

        self.assertEqual(services.get_students_count(), 3)
        self.assertEqual(services.get_disciplines_count(), 2)
        self.assertEqual(services.get_grades_count(), 0)

        controller.undo()
        controller.undo()

        self.assertEqual(services.get_students_count(), 3)
        self.assertEqual(services.get_disciplines_count(), 0)
        self.assertEqual(services.get_grades_count(), 0)

        controller.redo()

        self.assertEqual(services.get_students_count(), 3)
        self.assertEqual(services.get_disciplines_count(), 1)
        self.assertEqual(services.get_grades_count(), 0)
        
        controller.grade(["1", "1", "10"])
        controller.grade(["2", "1", "10"])
        controller.grade(["2", "1", "1"])
        controller.grade(["2", "1", "4"])
        controller.grade(["2", "1", "5"])
        controller.grade(["3", "1", "1"])
        controller.grade(["3", "1", "2"])
        controller.grade(["3", "1", "3"])
        controller.grade(["3", "1", "4"])
        controller.grade(["3", "1", "5"])

        self.assertEqual(services.get_students_count(), 3)
        self.assertEqual(services.get_disciplines_count(), 1)
        self.assertEqual(services.get_grades_count(), 10)

        controller.remove(["student", "3"])

        self.assertEqual(services.get_students_count(), 2)
        self.assertEqual(services.get_disciplines_count(), 1)
        self.assertEqual(services.get_grades_count(), 5)

        controller.undo()

        self.assertEqual(services.get_students_count(), 3)
        self.assertEqual(services.get_disciplines_count(), 1)
        self.assertEqual(services.get_grades_count(), 10)

        for _ in range(10):
            controller.undo()

        self.assertEqual(services.get_students_count(), 3)
        self.assertEqual(services.get_disciplines_count(), 1)
        self.assertEqual(services.get_grades_count(), 0)

        controller.remove(["discipline", "1"])
        self.assertEqual(services.get_disciplines_count(), 0)

        controller.undo()
        self.assertEqual(services.get_disciplines_count(), 1)

        controller.update(["discipline", "1", "test"])
        self.assertEqual(services.get_disciplines_count(), 1)

        controller.undo()
        self.assertEqual(services.get_disciplines_count(), 1)
        discipline = services.get_discipline(1)
        self.assertEqual(discipline.name, "discipline 1")

        controller.undo()
        controller.undo()
        controller.undo()
        controller.undo()

        self.assertTrue(services.is_empty())

        controller.preload_list()
        self.assertFalse(services.is_empty())

        controller.undo()

        self.assertTrue(services.is_empty())
        self.assertRaises(Exception, lambda: controller.undo())

    def test_redo(self):
        controller = Controller()
        services = controller.services

        self.assertRaises(Exception, lambda: controller.redo())

        controller.add(["student", "1", "stud 1"])
        controller.add(["student", "2", "stud 2"])
        controller.add(["student", "3", "stud 3"])
        controller.add(["discipline", "1", "discipline 1"])
        controller.add(["discipline", "2", "discipline 2"])

        self.assertEqual(services.get_students_count(), 3)
        self.assertEqual(services.get_disciplines_count(), 2)
        self.assertEqual(services.get_grades_count(), 0)

        self.assertRaises(Exception, lambda: controller.redo())

        controller.undo()
        controller.undo()

        self.assertEqual(services.get_students_count(), 3)
        self.assertEqual(services.get_disciplines_count(), 0)
        self.assertEqual(services.get_grades_count(), 0)

        controller.redo()
        controller.redo()

        self.assertEqual(services.get_students_count(), 3)
        self.assertEqual(services.get_disciplines_count(), 2)
        self.assertEqual(services.get_grades_count(), 0)
        
        controller.grade(["1", "1", "10"])
        controller.grade(["2", "1", "10"])
        controller.grade(["2", "1", "1"])
        controller.grade(["2", "1", "4"])
        controller.grade(["2", "2", "5"])
        controller.grade(["3", "1", "1"])
        controller.grade(["3", "1", "2"])
        controller.grade(["3", "1", "3"])
        controller.grade(["3", "2", "4"])
        controller.grade(["3", "2", "5"])

        self.assertEqual(services.get_students_count(), 3)
        self.assertEqual(services.get_disciplines_count(), 2)
        self.assertEqual(services.get_grades_count(), 10)

        controller.remove(["discipline", "2"])

        self.assertEqual(services.get_students_count(), 3)
        self.assertEqual(services.get_disciplines_count(), 1)
        self.assertEqual(services.get_grades_count(), 7)

        controller.undo()

        self.assertEqual(services.get_students_count(), 3)
        self.assertEqual(services.get_disciplines_count(), 2)
        self.assertEqual(services.get_grades_count(), 10)

        controller.redo()

        self.assertEqual(services.get_students_count(), 3)
        self.assertEqual(services.get_disciplines_count(), 1)
        self.assertEqual(services.get_grades_count(), 7)

        controller.remove(["student", "3"])

        self.assertEqual(services.get_students_count(), 2)
        self.assertEqual(services.get_disciplines_count(), 1)
        self.assertEqual(services.get_grades_count(), 4)

        for _ in range(13):
            controller.undo()

        self.assertEqual(services.get_students_count(), 3)
        self.assertEqual(services.get_disciplines_count(), 1)
        self.assertEqual(services.get_grades_count(), 0)

        controller.redo()
        controller.redo()

        self.assertEqual(services.get_students_count(), 3)
        self.assertEqual(services.get_disciplines_count(), 2)
        self.assertEqual(services.get_grades_count(), 1)

        controller.update(["discipline", "1", "test"])
        self.assertEqual(services.get_disciplines_count(), 2)

        controller.undo()
        self.assertEqual(services.get_disciplines_count(), 2)

        discipline = services.get_discipline(1)
        self.assertEqual(discipline.name, "discipline 1")

        controller.redo()
        self.assertEqual(services.get_disciplines_count(), 2)
        
        discipline = services.get_discipline(1)
        self.assertEqual(discipline.name, "test")

        for _ in range(7):
            controller.undo()

        self.assertTrue(services.is_empty())

        controller.preload_list()
        self.assertFalse(services.is_empty())

        controller.undo()
        self.assertTrue(services.is_empty())

        controller.redo()
        self.assertFalse(services.is_empty())

        self.assertRaises(Exception, lambda: controller.redo())
