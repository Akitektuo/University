import random

class Randomiser:

    firstNames = [
        "Liam",
        "Noah",
        "William",
        "James",
        "Logan",
        "Benjamin",
        "Mason",
        "Oliver",
        "Jacob",
        "John",
        "Robert",
        "Michael",
        "David",
        "Richard",
        "Charles",
        "Joseph",
        "Thomas",
        "Emma",
        "Olivia",
        "Ava",
        "Isabella",
        "Sophia",
        "Taylor",
        "Charlotte",
        "Amellia",
        "Evelyn",
        "Mary",
        "Patricia",
        "Linda",
        "Barbara",
        "Elizabeth",
        "Jennifer",
        "Maria",
        "Susan",
        "Margaret"
    ]
    lastNames = [
        "Smith",
        "Johnson",
        "Williams",
        "Jones",
        "Brown",
        "Davis",
        "Miller",
        "Wilson",
        "Moore",
        "Taylor",
        "Anderson",
        "Thomas",
        "Jackson",
        "White",
        "Harris",
        "Martin",
        "Thompson",
        "Garcia",
        "Martinez",
        "Robinson",
        "Clark",
        "Rodriguez",
        "Lewis",
        "Lee",
        "Walker",
        "Hall",
        "Allen",
        "Young",
        "Hernandez",
        "King"
    ]
    disciplines = [
        "Fundamentals of Programming",
        "Mathematical Foundations of Computer Science",
        "Algorithms and Data Structures",
        "Object-Oriented Programming",
        "Advanced Programming Methods",
        "Functional and Logic Programming",
        "Algorithms of Graph Theory",
        "Formal Languages and Compiler Design",
        "Mathematical Analysis",
        "Algebra",
        "Geometry",
        "Probability Theory and Statistics",
        "Numerical Calculus",
        "Dynamical Systems",
        "Software Engineering",
        "Team and Individual Project",
        "Software Verification and Validation",
        "Human-Computer Interaction",
        "Mobile Systems",
        "CASE Tools",
        "Design Patterns",
        "Databases",
        "Transaction Management and Distributed Databases",
        "Spatial Databases",
        "Computer System Architecture",
        "Operating systems",
        "Distributed Operating Systems",
        "Computer Networks",
        "Artificial intelligence",
        "Intelligent Robots",
        "Evolutionary Algorithms",
        "Automated Theorem Proving",
        "Programming Systems and Environments",
        "Web Programming",
        "Multimedia",
        "Image processing",
        "Modeling and simulation"
    ]

    def __init__(self):
        self.usedNames = []

    def __get_random_name(self, name_list):
        randomName = random.choice(name_list)
        while randomName in self.usedNames:
            randomName = random.choice(name_list)
        self.usedNames.append(randomName)
        return randomName

    def get_random_first_name(self):
        return self.__get_random_name(self.firstNames)

    def get_random_last_name(self):
        return self.__get_random_name(self.lastNames)

    def get_random_discipline_name(self):
         return self.__get_random_name(self.disciplines)

    def get_random_grade(self):
        return random.randint(1, 10)

    def get_random(self, listWithElements):
        return random.choice(listWithElements)