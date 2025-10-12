from Parser import parse_data
from DataProcessing import sort_sport, apt_athletes, age_distribution


class Main:

    def menu_print(self):
        print("")
        print("1. Sorted List with all sports")
        print("2. Percentage of apt and inapt athletes")
        print("3. Athletes per age rating")
        print("0. Exit")
        user_choice = input("Choose an option: ")
        return user_choice

    def main_loop(self, athletes):
        while True:
            user_choice = self.menu_print()

            if user_choice == '1':
                # Lista ordenada alfabeticamente das modalidades desportivas;
                sports = sort_sport(athletes)
                print()
                print("The sports are: " + ', '.join(sports))
            elif user_choice == '2':
                # Percentagens de atletas aptos e inaptos para a prática desportiva;
                percentage = apt_athletes(athletes)
                print()
                print("The percentage of apt athletes is " + str(percentage) + "%")
                pass
            elif user_choice == '3':
                # Distribuição de atletas por escalão etário (escalão = intervalo de 5 anos): ... [30-34], [35-39], ...
                dist = age_distribution(athletes)
                print()
                print("There are " + str(dist[4]) + " athletes between 20 and 24 years and their percentage is " + str(dist[0]) + "%")
                print("There are " + str(dist[5]) + " athletes between 20 and 24 years and their percentage is " + str(dist[1]) + "%")
                print("There are " + str(dist[6]) + " athletes between 20 and 24 years and their percentage is " + str(dist[2]) + "%")
                print("There are " + str(dist[7]) + " athletes between 20 and 24 years and their percentage is " + str(dist[3]) + "%")
                pass
            elif user_choice == '0':
                break
            else:
                print(" ")
                print("Invalid option:", user_choice)

    def __init__(self):
        athletes = parse_data()
        self.main_loop(athletes)


main_instance = Main()