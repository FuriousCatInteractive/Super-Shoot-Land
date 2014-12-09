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



###Gestion des entrées clavier###

#Parsing : 

Le fichier bindings.xml contient la liste des touches associées à des actions pour un joueur donné (bindings).
Une action est représentée par une balise <bind> possédant un nom (ACTION_MOVE, ACTION_ATTACK...) et le joueur pouvant
executer cette action.
Dans chaque <bind> nous aurons des <key> indiquant quelles touches du clavier effectuent l'action.
Chaque key possède un attribut name indiquant le code de la touche qui sera récupéré par JSFML.

La classe XmlKeyBindingsParser réalise le parsing de ce fichier.
Nous disposons d'un arraylist de KeyBindings dans lequels sera stocké la liste des actions et leurs touches.
Un objet KeyBinding contient un attribut actioName (nom de l'action dans <bind>), le numéro du joueur associé et
un arraylist d'objets Keyboard.Key de JSFML qui sont les touches du clavier.

Le parsing est basé sur DOM, il stocke le fichier XML sous forme d'un arbre.
Le fichier étant relativement petit, cette méthode est donc plutôt adéquate.
Nous récupérons tous les noeuds <bind> dans un NodeList, avec pour chacun le type d'action
et le joueur associé.
Nous créons un objet KeyBinding qui va contenir ces informations, puis nous itérons sur
chaque noeud <key> enfant de <bind> afin de récupérer le nom de la touche donnée.
Une vérification est faite en comparant le nom de la touche avec les codes de touches 
proposés par JSFML pour vérifier que la touche existe bien.
La key est alors ajoutée à l'arraylist de Keyboard.Key du KeyBinding, et au final
nous aurons notre liste d'objets KeyBindings prêts à l'usage.

C'est la classe GameConfig qui appelle XmlKeyBindingsParser lors de l'initialisation du jeu.

#Utilisation des bindings

La classe InputManager sert à récupérer les évènements d'appui sur une touche en cours de partie.
Nous vérifions avec JSFML si un évènement a été produit, si oui alors pour chaque joueur nous
vérifions quelle action a été entreprise.
Pour cela nous utilisons une classe KeyboardActions qui contient une série de méthodes
statiques permettant de vérifier si une action donnée a été effectuée.
Par exemple, pour vérifier si le joueur 1 a fait un saut on va regarder si la méthode
isJumping(GameLoop.p1) retourne vrai.

Cette méthode va parcourir l'ensemble des KeyBindings stocké dans GameConfig,
et si nous trouvons une action ayant pour joueur associé le joueur 1 et
qui a pour nom ACTION_JUMP alors nous allons parcourir toutes les
Keyboard.Key de ce KeyBinding et demander à JSFML de vérifier si cette touche
est actuellement pressée (Keyboard.isKeyPressed(la touche)).
Si c'est le cas nous renvoyons vrai, et nous faisons sauter le joueur 1.

Toutes les actions des joueurs sont vérifiées de la sorte, en donnant en paramètre aux méthodes les
joueurs dont il faut récupérer l'action.
Une méthode movementKeyReleased prenant un évènement clavier et un joueur en paramètre permet
de vérifier si une touche donnée a été relachée, utilisée avant tout pour arrêter de déplacer les
personnages lorsqu'on relâche les touches de mouvement.



