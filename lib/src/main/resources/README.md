# LTC

### Liferay Temp Cleaner

- [Introduction](#introduction)

- [Structure](#structure)

- [Getting started](#getting-started)

- [Commands](#commands)

- [Configs](#configs)

- [Ending](#ending)

#### Introduction

Hallo, wenn du nur ein kleinwenig so bist wie ich, dann bist du eine faule Sau, der/die</br>
so wenig Aufwand wie möglich am Tag aufbringen möchte.</br>
Dir muss dann liferay ein Dorn im Auge sein.</br>
Speziell eines der am häufig auftauchenden nerfigen Aufgabe ist das Löschen der temporären Datein.

- work
- osgi/state
- tomcat/temp
- tomcat/work

Man muss sich durch diese Ordner durch navigieren und dann alle Datein darin löschen.</br>
Das sieht meist (ungefähr) so aus:

`win + E` -> `DML Click{workspace}` -> `DBL Click bundles` -> `DBL Click liferay` -></br>
`DBL Click work` -> `ctlr + A` -> `del` -> `LMB back` -> `DBL Click osgi` -></br>
`DBL Click state` -> `ctlr + A` -> `del` -> `LMB back` -> `DBL Click tomcat` -></br>
`DBL click temp` -> `ctlr + A` -> `del` -> `LMB back` -></br>
`DBL Click work` -> `ctlr + A` -> `del`

Man weis was geschehen muss, man ist mit den Gedanken schon weiter, aber der
physikalische Körper ist noch hinterher und genau da möchte ich abhilfe schaffen.

#### Structure

Nach dem Unzippen wurde folgende Ordnerstruktur aufgebaut:
- ltc-{version}
    - bin
        - ltc.bat

    - config
        - conf.json
        - sample-conf.json

    - libs
        - ltc-{version}.jar

    - README.md

In `bin` ist lediglich eine simple .bat Datei, die das Programm ausführt.</br>
In `config` sind Konfigurationsdateien vorhanden, dort vorzufinden ist conf.json und sample-conf.json.</br>
In conf.json sind Konfigurationen gespeichert über deine workspaces, da diese leer</br>
ist findest du eine sample-conf.json, die dient dazu ein Gefühl zu bekommen, wie</br>
die conf.json aufgebaut sein muss.

Im laufe kann es passieren, dass ein `logs` erstellt wird, inder zum beispiel</br>
falsch gesetzte Attribute geloggt werden.

#### Getting Started

1. Diese Dokumentation lesen!

2. In den Umgebungsvariablen unter `Path` folgenden Eintrag hinzufügen:</br>
`{your\path}\ltc\ltc-{version}\bin`

3. Im folge darauf mit `ltc -v` oder `ltc --version` die Funktionalität bestätigen.

4. Konfigurationen anpassen.

5. Mit dem Löschen beginnen :)

#### Commands

|        Name        |        Type       | Required | Default-Value |                                                                                                                 Description                                                                                                                |
|:------------------:|:-----------------:|:--------:|:-------------:|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
| -l</br> --location | String <location> |   true   |      none     | Der Pfad zum Workspace.</br> Ein '.' kann gesetzt werden</br> um den geradigen Pfad zu wählen                                                                                                                                              |
|  -c</br> --no-conf |      boolean      |   false  |     false     | Hiermit wird das Programm</br> ohne Berücksichtigung auf die</br> conf.json ausgeführt                                                                                                                                                     |
|    -a</br> --all   |      boolean      |   false  |     false     | Hiermit werden alle Temporären</br> Verzeichnisse von Liferay für die</br> Säuberung ausgewählt.</br> Das hat den gleichen Effekt wie:</br> `--c-work`</br> `--c-osgi-state`</br> `--c-tomcat-temp`</br> `--c-tomcat-work`</br> zu setzen. |
|    --no-includes   |      boolean      |   false  |     false     | Hiermit werden wird das Programm</br> ohne Berücksichtigung auf die</br> 'inc-args' von der conf.json</br> ausgeführt.                                                                                                                     |
|      --c-work      |      boolean      |   false  |     false     | Hiermit wird das '/work'</br> Verzeichnis gesäubert.                                                                                                                                                                                       |
|   --c-osgi-state   |      boolean      |   false  |     false     | Hiermit wird das '/osgi/state'</br> Verzeichnis gesäubert.                                                                                                                                                                                 |
|   --c-tomcat-temp  |      boolean      |   false  |     false     | Hiermit wird das '/tomcat/temp'</br> Verzeichnis gesäubert.                                                                                                                                                                                |
|   --c-tomcat-work  |      boolean      |   false  |     false     | Hiermit wird das '/tomcat/work'</br> Verzeichnis gesäubert.                                                                                                                                                                                |

#### Configs

Es gibt 3 Attribute, die man setzen kann pro workspace:

- "liferay-home-name"
- "tomcat-home-name"
- "inc-args"

Da wir für jedes Projekt eine andere Version von Liferay benutzen, muss man</br>
in der conf.json mit dem Attribut `liferay-home-name` den Namen von seinem</br>
Liferay angeben.</br>
Wird kein Attribut gesetzt wird `liferay` als Defaultwert genommen.</br>
Dafür kann man dann auch ein 'symbolic link' erstellen.</br>
Bsp: `mklink /D C:\Workspaces\ws_obis\bundles\liferay C:\Workspaces\ws_obis\bundles\liferay-7.2.1-ga2`

Das gleiche gilt auch für `tomcat-home-name`, da liferay sein Tomcatverzeichniss</br>
mit Versionnummer anlegt.</br>
Wird hier kein Attribut gesetzt wird `tomcat` als Defaultwert genommen.</br>
Auch hier nach belieben ein 'symbolic link' erstellen.</br>
Bsp: Bsp: `mklink /D C:\Workspaces\ws_obis\bundles\liferay\tomcat C:\Workspaces\ws_obis\bundles\liferay-7.2.1-ga2\tomcat-9.0.17`</br>
_PS: Neuere Liferayversionen legen das Tomcatverzeichniss ohne Versionierung an._

Im letzten Attribut `inc-args` kann man spezifizieren, welche Verzeichnisse geleert werden sollen.</br>
Da es 4 temporäre Verzeichnisse gibt, gibt es auch nur 4 Werte, die hier gesetz werden können.</br>
Diese sind:

- --c-work
- --c-osgi-state
- --c-tomcat-temp
- --c-tomcat-work

Diese sind die gleichen wie auch bei [Commands](#commands)

#### Ending

Repository: https://github.com/ThePcda/ltc

Bei Anregungen oder Verbesserungsvorschläge kann man natürlich auf mich zu kommen.

Mit dieser App könnt machen was ihr wollt, außer es zu Kapital machen.
