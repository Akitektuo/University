# struct Account {
#     char cnp[13], name[101];
#     float balace;
# }

class Account:

    def __init__(self, cnp, name, balance):
        self.cnp = cnp
        self.name = name
        self.balance = balance

    def __str__(self):
        return "Account class with:\nCNP: " + self.cnp + "\nName: " + self.name + "\nBalance: " + str(self.balance) + " RON"

    def transfer_to(self, other, amount):
        self.balance -= amount
        other.balance += amount


account1 = Account("5000210125828", "Alexandru Copindean", 2000)
account2 = Account("5000210125828", "Diana Copindean", 4000)

print("BEFORE TRANSFER")
print(account1)
print()
print(account2)

account2.transfer_to(account1, 2000)

print("\n\nAFTER TRANSFER")
print(account1)
print()
print(account2)