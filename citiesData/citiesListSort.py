import json
import csv

with open("city.list.json", "r") as f:
    data = json.load(f)

data.sort(key=lambda x:x["name"])

with open("citiesListSorted.csv", "w+") as f:
    w = csv.writer(f)
    for line in data:

        # Prendre valeurs
        id = line["id"]
        name = line["name"]
        country = line["country"]
        lat = line["coord"]["lat"]
        lon = line["coord"]["lon"]

        # Écrire au csv
        w.writerow([id, name, country, lat, lon])
