import unittest
from services import Services

class ServicesTest(unittest.TestCase):

    def test_preload(self):
        services = Services()
        repo = services.repository
        services.preload_list()

        self.assertEqual(repo.get_students_count(), 10)
        self.assertEqual(repo.get_disciplines_count(), 10)
        self.assertEqual(repo.get_grades_count(), 10)


    def test_add_student(self):
        services = Services()
        repo = services.repository

        self.assertRaises(Exception, lambda: services.add([]))
        self.assertRaises(Exception, lambda: services.add(["", "", ""]))
        self.assertRaises(Exception, lambda: services.add(["abc", "abc", "abc"]))
        self.assertRaises(Exception, lambda: services.add(["student", "abc", "abc"]))
        self.assertRaises(Exception, lambda: services.add(["student", "0", "abc"]))
        services.add(["student", "0", "test"])

        student1 = repo.get_student(0)
        self.assertEqual(student1.id, 0)
        self.assertEqual(student1.name, "test")
        self.assertEqual(repo.get_students_count(), 1)

        self.assertRaises(Exception, lambda: services.add(["student", "0", "testing"]))
        services.add(["student", "1", "test"])

        student2 = repo.get_student(1)
        self.assertEqual(student2.id, 1)
        self.assertEqual(student2.name, "test")
        self.assertEqual(repo.get_students_count(), 2)

        self.assertNotEqual(student1, student2)


    def test_add_discipline(self):
        services = Services()
        repo = services.repository

        self.assertRaises(Exception, lambda: services.add([]))
        self.assertRaises(Exception, lambda: services.add(["", "", ""]))
        self.assertRaises(Exception, lambda: services.add(["abc", "abc", "abc"]))
        self.assertRaises(Exception, lambda: services.add(["discipline", "abc", "abc"]))
        self.assertRaises(Exception, lambda: services.add(["discipline", "0", "abc"]))
        services.add(["discipline", "0", "test"])

        discipline1 = repo.get_discipline(0)
        self.assertEqual(discipline1.id, 0)
        self.assertEqual(discipline1.name, "test")
        self.assertEqual(repo.get_disciplines_count(), 1)

        self.assertRaises(Exception, lambda: services.add(["discipline", "0", "testing"]))
        services.add(["discipline", "1", "test"])

        discipline2 = repo.get_discipline(1)
        self.assertEqual(discipline2.id, 1)
        self.assertEqual(discipline2.name, "test")
        self.assertEqual(repo.get_disciplines_count(), 2)

        self.assertNotEqual(discipline1, discipline2)


    def test_remove_student(self):
        services = Services()
        repo = services.repository

        services.add(["discipline", "0", "discipline test"])
        services.add(["student", "1", "student 1 test"])
        services.add(["student", "2", "student 2 test"])
        services.grade(["1", "0", "10"])
        services.grade(["1", "0", "6"])
        services.grade(["2", "0", "9"])
        services.grade(["2", "0", "7"])
        services.grade(["2", "0", "8"])

        self.assertEqual(repo.get_students_count(), 2)
        self.assertEqual(repo.get_disciplines_count(), 1)
        self.assertEqual(repo.get_grades_count(), 5)

        self.assertRaises(Exception, lambda: services.remove([]))
        self.assertRaises(Exception, lambda: services.remove(["", ""]))
        self.assertRaises(Exception, lambda: services.remove(["abc", "abc"]))
        self.assertRaises(Exception, lambda: services.remove(["student", "abc"]))
        self.assertRaises(Exception, lambda: services.remove(["student", "0"]))
        services.remove(["student", "2"])

        self.assertEqual(repo.get_students_count(), 1)
        self.assertEqual(repo.get_disciplines_count(), 1)
        self.assertEqual(repo.get_grades_count(), 2)
        self.assertRaises(Exception, lambda: services.remove(["student", "2"]))


    def test_remove_discipline(self):
        services = Services()
        repo = services.repository

        services.add(["student", "0", "student test"])
        services.add(["discipline", "1", "discipline 1 test"])
        services.add(["discipline", "2", "discipline 2 test"])
        services.grade(["0", "1", "10"])
        services.grade(["0", "1", "6"])
        services.grade(["0", "2", "9"])
        services.grade(["0", "2", "7"])
        services.grade(["0", "2", "8"])

        self.assertEqual(repo.get_students_count(), 1)
        self.assertEqual(repo.get_disciplines_count(), 2)
        self.assertEqual(repo.get_grades_count(), 5)

        self.assertRaises(Exception, lambda: services.remove([]))
        self.assertRaises(Exception, lambda: services.remove(["", ""]))
        self.assertRaises(Exception, lambda: services.remove(["abc", "abc"]))
        self.assertRaises(Exception, lambda: services.remove(["discipline", "abc"]))
        self.assertRaises(Exception, lambda: services.remove(["discipline", "0"]))
        services.remove(["discipline", "2"])

        self.assertEqual(repo.get_students_count(), 1)
        self.assertEqual(repo.get_disciplines_count(), 1)
        self.assertEqual(repo.get_grades_count(), 2)
        self.assertRaises(Exception, lambda: services.remove(["discipline", "2"]))


    def test_update_student(self):
        services = Services()
        repo = services.repository

        services.add(["student", "1", "student test"])
        self.assertEqual(repo.get_students_count(), 1)
        student = repo.get_student(1)
        self.assertEqual(student.id, 1)
        self.assertEqual(student.name, "student test")

        self.assertRaises(Exception, lambda: services.update([]))
        self.assertRaises(Exception, lambda: services.update(["", "", ""]))
        self.assertRaises(Exception, lambda: services.update(["abc", "abc", "abc"]))
        self.assertRaises(Exception, lambda: services.update(["student", "abc", "abc"]))
        self.assertRaises(Exception, lambda: services.update(["student", "0", "abc"]))
        self.assertRaises(Exception, lambda: services.update(["student", "1", "abc"]))
        services.update(["student", "1", "test"])

        self.assertEqual(student.id, 1)
        self.assertEqual(student.name, "test")
        self.assertEqual(repo.get_students_count(), 1)


    def test_update_discipline(self):
        services = Services()
        repo = services.repository

        services.add(["discipline", "1", "discipline test"])
        self.assertEqual(repo.get_disciplines_count(), 1)
        discipline = repo.get_discipline(1)
        self.assertEqual(discipline.id, 1)
        self.assertEqual(discipline.name, "discipline test")

        self.assertRaises(Exception, lambda: services.update([]))
        self.assertRaises(Exception, lambda: services.update(["", "", ""]))
        self.assertRaises(Exception, lambda: services.update(["abc", "abc", "abc"]))
        self.assertRaises(Exception, lambda: services.update(["discipline", "abc", "abc"]))
        self.assertRaises(Exception, lambda: services.update(["discipline", "0", "abc"]))
        self.assertRaises(Exception, lambda: services.update(["discipline", "1", "abc"]))
        services.update(["discipline", "1", "test"])

        self.assertEqual(discipline.id, 1)
        self.assertEqual(discipline.name, "test")
        self.assertEqual(repo.get_disciplines_count(), 1)


    def test_list_all(self):
        services = Services()
        repo = services.repository

        self.assertRaises(Exception, lambda: services.list_all())

        services.add(["student", "0", "stud 1"])
        self.assertEqual(repo.get_students_count(), 1)

        self.assertNotIn('|', services.list_all())

        repo.clear()
        self.assertEqual(repo.get_students_count(), 0)
        services.add(["discipline", "1", "discipline 1"])
        self.assertEqual(repo.get_disciplines_count(), 1)

        self.assertNotIn('|', services.list_all())

        services.add(["student", "2", "stud 1"])
        self.assertEqual(repo.get_students_count(), 1)
        services.add(["student", "3", "stud 2"])
        self.assertEqual(repo.get_students_count(), 2)

        self.assertIn('|', services.list_all())


    def test_grade(self):
        services = Services()
        repo = services.repository

        services.add(["student", "1", "stud 1"])
        services.add(["discipline", "1", "discipline 1"])

        self.assertEqual(repo.get_students_count(), 1)
        self.assertEqual(repo.get_disciplines_count(), 1)

        self.assertRaises(Exception, lambda: services.grade([]))
        self.assertRaises(Exception, lambda: services.grade(["", "", ""]))
        self.assertRaises(Exception, lambda: services.grade(["abc", "abc", "abc"]))
        self.assertRaises(Exception, lambda: services.grade(["0", "abc", "abc"]))
        self.assertRaises(Exception, lambda: services.grade(["1", "abc", "abc"]))
        self.assertRaises(Exception, lambda: services.grade(["1", "0", "abc"]))
        self.assertRaises(Exception, lambda: services.grade(["1", "1", "abc"]))
        self.assertRaises(Exception, lambda: services.grade(["1", "1", "0"]))
        self.assertRaises(Exception, lambda: services.grade(["1", "1", "11"]))

        services.grade(["1", "1", "1"])
        services.grade(["1", "1", "10"])
        services.grade(["1", "1", "5"])

        self.assertEqual(repo.get_grades_count(), 3)


    def test_search(self):
        services = Services()
        repo = services.repository

        services.add(["student", "1", "stud 1"])
        services.add(["student", "2", "stud 2"])
        services.add(["discipline", "1", "discipline"])

        self.assertEqual(repo.get_students_count(), 2)
        self.assertEqual(repo.get_disciplines_count(), 1)

        self.assertRaises(Exception, lambda: services.search([]))
        self.assertRaises(Exception, lambda: services.search([""]))
        self.assertRaises(Exception, lambda: services.search(["magic"]))

        self.assertNotIn('|', services.search(["stud"]))
        self.assertIn('|', services.search(["1"]))


    def test_failing_students(self):
        services = Services()
        repo = services.repository

        services.add(["student", "1", "stud 1"])
        services.add(["student", "2", "stud 2"])
        services.add(["student", "3", "stud 3"])
        services.add(["discipline", "1", "discipline 1"])
        services.add(["discipline", "2", "discipline 2"])
        services.grade(["1", "1", "10"])
        services.grade(["2", "1", "10"])
        services.grade(["2", "1", "1"])
        services.grade(["2", "1", "4"])
        services.grade(["2", "2", "5"])

        self.assertEqual(repo.get_students_count(), 3)
        self.assertEqual(repo.get_disciplines_count(), 2)
        self.assertEqual(repo.get_grades_count(), 5)

        self.assertRaises(Exception, lambda: services.see_failing_students())
        
        services.grade(["3", "1", "1"])
        services.grade(["3", "1", "2"])
        services.grade(["3", "1", "3"])
        services.grade(["3", "1", "4"])
        services.grade(["3", "2", "5"])
        self.assertEqual(repo.get_grades_count(), 10)

        self.assertIn("\n", services.see_failing_students())


    def test_best_students(self):
        services = Services()
        repo = services.repository

        services.add(["student", "1", "stud 1"])
        services.add(["student", "2", "stud 2"])
        services.add(["student", "3", "stud 3"])
        services.add(["discipline", "1", "discipline 1"])
        services.add(["discipline", "2", "discipline 2"])

        self.assertEqual(repo.get_students_count(), 3)
        self.assertEqual(repo.get_disciplines_count(), 2)

        self.assertRaises(Exception, lambda: services.see_best_students())
        
        services.grade(["1", "1", "10"])
        services.grade(["2", "1", "10"])
        services.grade(["2", "1", "1"])
        services.grade(["2", "1", "4"])
        services.grade(["2", "2", "5"])
        services.grade(["3", "1", "1"])
        services.grade(["3", "1", "2"])
        services.grade(["3", "1", "3"])
        services.grade(["3", "1", "4"])
        services.grade(["3", "2", "5"])
        self.assertEqual(repo.get_grades_count(), 10)

        self.assertIn("\n", services.see_best_students())


    def test_disciplines_with_grades(self):
        services = Services()
        repo = services.repository

        services = Services()
        repo = services.repository

        services.add(["student", "1", "stud 1"])
        services.add(["student", "2", "stud 2"])
        services.add(["student", "3", "stud 3"])
        services.add(["discipline", "1", "discipline 1"])
        services.add(["discipline", "2", "discipline 2"])

        self.assertEqual(repo.get_students_count(), 3)
        self.assertEqual(repo.get_disciplines_count(), 2)

        self.assertRaises(Exception, lambda: services.see_grades())
        
        services.grade(["1", "1", "10"])
        services.grade(["2", "1", "10"])
        services.grade(["2", "1", "1"])
        services.grade(["2", "1", "4"])
        services.grade(["2", "2", "5"])
        services.grade(["3", "1", "1"])
        services.grade(["3", "1", "2"])
        services.grade(["3", "1", "3"])
        services.grade(["3", "1", "4"])
        services.grade(["3", "2", "5"])
        self.assertEqual(repo.get_grades_count(), 10)

        self.assertIn("\n", services.see_grades())


    def test_undo(self):
        services = Services()
        repo = services.repository

        self.assertRaises(Exception, lambda: services.undo())

        services.add(["student", "1", "stud 1"])
        services.add(["student", "2", "stud 2"])
        services.add(["student", "3", "stud 3"])
        services.add(["discipline", "1", "discipline 1"])
        services.add(["discipline", "2", "discipline 2"])

        self.assertEqual(repo.get_students_count(), 3)
        self.assertEqual(repo.get_disciplines_count(), 2)
        self.assertEqual(repo.get_grades_count(), 0)

        services.undo()
        services.undo()

        self.assertEqual(repo.get_students_count(), 3)
        self.assertEqual(repo.get_disciplines_count(), 0)
        self.assertEqual(repo.get_grades_count(), 0)

        services.redo()

        self.assertEqual(repo.get_students_count(), 3)
        self.assertEqual(repo.get_disciplines_count(), 1)
        self.assertEqual(repo.get_grades_count(), 0)
        
        services.grade(["1", "1", "10"])
        services.grade(["2", "1", "10"])
        services.grade(["2", "1", "1"])
        services.grade(["2", "1", "4"])
        services.grade(["2", "1", "5"])
        services.grade(["3", "1", "1"])
        services.grade(["3", "1", "2"])
        services.grade(["3", "1", "3"])
        services.grade(["3", "1", "4"])
        services.grade(["3", "1", "5"])

        self.assertEqual(repo.get_students_count(), 3)
        self.assertEqual(repo.get_disciplines_count(), 1)
        self.assertEqual(repo.get_grades_count(), 10)

        services.remove(["student", "3"])

        self.assertEqual(repo.get_students_count(), 2)
        self.assertEqual(repo.get_disciplines_count(), 1)
        self.assertEqual(repo.get_grades_count(), 5)

        services.undo()

        self.assertEqual(repo.get_students_count(), 3)
        self.assertEqual(repo.get_disciplines_count(), 1)
        self.assertEqual(repo.get_grades_count(), 10)

        for _ in range(10):
            services.undo()

        self.assertEqual(repo.get_students_count(), 3)
        self.assertEqual(repo.get_disciplines_count(), 1)
        self.assertEqual(repo.get_grades_count(), 0)

        services.remove(["discipline", "1"])
        self.assertEqual(repo.get_disciplines_count(), 0)

        services.undo()
        self.assertEqual(repo.get_disciplines_count(), 1)

        services.update(["discipline", "1", "test"])
        self.assertEqual(repo.get_disciplines_count(), 1)

        services.undo()
        self.assertEqual(repo.get_disciplines_count(), 1)
        discipline = repo.get_discipline(1)
        self.assertEqual(discipline.name, "discipline 1")

        services.undo()
        services.undo()
        services.undo()
        services.undo()

        self.assertTrue(repo.is_empty())

        services.preload_list()
        self.assertFalse(repo.is_empty())

        services.undo()

        self.assertTrue(repo.is_empty())
        self.assertRaises(Exception, lambda: services.undo())

    def test_redo(self):
        services = Services()
        repo = services.repository

        self.assertRaises(Exception, lambda: services.redo())

        services.add(["student", "1", "stud 1"])
        services.add(["student", "2", "stud 2"])
        services.add(["student", "3", "stud 3"])
        services.add(["discipline", "1", "discipline 1"])
        services.add(["discipline", "2", "discipline 2"])

        self.assertEqual(repo.get_students_count(), 3)
        self.assertEqual(repo.get_disciplines_count(), 2)
        self.assertEqual(repo.get_grades_count(), 0)

        self.assertRaises(Exception, lambda: services.redo())

        services.undo()
        services.undo()

        self.assertEqual(repo.get_students_count(), 3)
        self.assertEqual(repo.get_disciplines_count(), 0)
        self.assertEqual(repo.get_grades_count(), 0)

        services.redo()
        services.redo()

        self.assertEqual(repo.get_students_count(), 3)
        self.assertEqual(repo.get_disciplines_count(), 2)
        self.assertEqual(repo.get_grades_count(), 0)
        
        services.grade(["1", "1", "10"])
        services.grade(["2", "1", "10"])
        services.grade(["2", "1", "1"])
        services.grade(["2", "1", "4"])
        services.grade(["2", "2", "5"])
        services.grade(["3", "1", "1"])
        services.grade(["3", "1", "2"])
        services.grade(["3", "1", "3"])
        services.grade(["3", "2", "4"])
        services.grade(["3", "2", "5"])

        self.assertEqual(repo.get_students_count(), 3)
        self.assertEqual(repo.get_disciplines_count(), 2)
        self.assertEqual(repo.get_grades_count(), 10)

        services.remove(["discipline", "2"])

        self.assertEqual(repo.get_students_count(), 3)
        self.assertEqual(repo.get_disciplines_count(), 1)
        self.assertEqual(repo.get_grades_count(), 7)

        services.undo()

        self.assertEqual(repo.get_students_count(), 3)
        self.assertEqual(repo.get_disciplines_count(), 2)
        self.assertEqual(repo.get_grades_count(), 10)

        services.redo()

        self.assertEqual(repo.get_students_count(), 3)
        self.assertEqual(repo.get_disciplines_count(), 1)
        self.assertEqual(repo.get_grades_count(), 7)

        services.remove(["student", "3"])

        self.assertEqual(repo.get_students_count(), 2)
        self.assertEqual(repo.get_disciplines_count(), 1)
        self.assertEqual(repo.get_grades_count(), 4)

        for _ in range(13):
            services.undo()

        self.assertEqual(repo.get_students_count(), 3)
        self.assertEqual(repo.get_disciplines_count(), 1)
        self.assertEqual(repo.get_grades_count(), 0)

        services.redo()
        services.redo()

        self.assertEqual(repo.get_students_count(), 3)
        self.assertEqual(repo.get_disciplines_count(), 2)
        self.assertEqual(repo.get_grades_count(), 1)

        services.update(["discipline", "1", "test"])
        self.assertEqual(repo.get_disciplines_count(), 2)

        services.undo()
        self.assertEqual(repo.get_disciplines_count(), 2)

        discipline = repo.get_discipline(1)
        self.assertEqual(discipline.name, "discipline 1")

        services.redo()
        self.assertEqual(repo.get_disciplines_count(), 2)
        
        discipline = repo.get_discipline(1)
        self.assertEqual(discipline.name, "test")

        for _ in range(7):
            services.undo()

        self.assertTrue(repo.is_empty())

        services.preload_list()
        self.assertFalse(repo.is_empty())

        services.undo()
        self.assertTrue(repo.is_empty())

        services.redo()
        self.assertFalse(repo.is_empty())

        self.assertRaises(Exception, lambda: services.redo())
