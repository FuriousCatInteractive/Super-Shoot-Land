#Projet
##8INF957  - UQAC
##Automne 2014

###Steven FOUGERON, Yannis M'RAD, Corentin RAOULT 

##Présentation:

Ce projet est un petit jeu vidéo de type platformer/shooter inspiré de Super Smsh Land (http://www.supersmashland.com/). Ce dernier à été conçut avec GameMaker mais dont nous avons donc repris les assets sonores et graphiques 	.
Chaque joueur contrôle un personnage capable de sauter/bouger/tirer qui a pour but de faire tomber ou tuer l'adversaire.
Le jeu utilise la librairie JSFML (https://github.com/pdinklag/JSFML/wiki) qui est un binding de la SFML qui est écite en C++.
Cette librairie permet de faciliter la gestion des collisons, le chargement des textures et des sprite sheets ainsi que la gestion des inputs.

##Contôles par défault:
* joueur 1:
	* flèches directionnelles pour bouger
	* NUMPAD1 pour tirer/selectionner
	* NUMPAD2 pour sauter
	* NUMPAD3 pour quitter
* joueur 2:
	* W, A, S, D pour bouger
	* R pour tirer/selectionner
	* T pour sauter
	* Y pour quitter

##Modules présents:
* jeu de base 1v1 en local
* génération de niveau aléatoire
* bindings des touches à partir d'un fichier XML
* menus, musiques, feedbacks..


##Nombre d'heures investies

modélisation: Temps prévu : 2h, Temps effectif : ~1h30 mins
développement:  Temps prévu : 80h, Temps effectif : ~100h
débogage: Temps prévu : 10h, Temps effectif : ~13h mins