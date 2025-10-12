def sort_sport(athletes):
    sports = []
    for athlete in athletes:
        if athlete["modalidade"] in sports:
            pass
        else:
            sports.append(athlete["modalidade"])

    sports.sort()
    return sports


def apt_athletes(athletes):
    apt = []
    total = []
    for athlete in athletes:
        if athlete["resultado"] == "true":
            apt.append(athlete)
            total.append(athlete)
        else:
            total.append(athlete)
    percentage = (len(apt) / len(total)) * 100
    return percentage


def age_distribution(athletes):
    total = []
    range1 = []
    range2 = []
    range3 = []
    range4 = []
    dist_list = []

    for athlete in athletes:
        total.append(athlete)
        idade = athlete["idade"]
        idade = int(idade)
        if 20 <= idade <= 24:
            range1.append(athlete)
        elif 25 <= idade <= 29:
            range2.append(athlete)
        elif 30 <= idade <= 34:
            range3.append(athlete)
        elif 35 <= idade <= 39:
            range4.append(athlete)

    dist1 = len(range1) / len(total) * 100
    dist_list.append(round(dist1, 2))
    dist2 = len(range2) / len(total) * 100
    dist_list.append(round(dist2, 2))
    dist3 = len(range3) / len(total) * 100
    dist_list.append(round(dist3, 2))
    dist4 = len(range4) / len(total) * 100
    dist_list.append(round(dist4, 2))
    dist_list.append(len(range1))
    dist_list.append(len(range2))
    dist_list.append(len(range3))
    dist_list.append(len(range4))
    return dist_list
