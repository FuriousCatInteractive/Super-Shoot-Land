# Super Shoot Land
## 8INF957  - UQAC
## Automne 2014

### Steven FOUGERON, Yannis M'RAD, Corentin RAOULT

![logo](res/img/logo_readme.png)

## Présentation:

Ce projet est un petit jeu vidéo de type platformer/shooter inspiré de [Super Smash Land](http://www.supersmashland.com/). Ce dernier à été conçut avec GameMaker mais dont nous avons donc repris les assets sonores et graphiques 	.
Chaque joueur contrôle un personnage capable de sauter/bouger/tirer qui a pour but de faire tomber ou tuer l'adversaire.
Le jeu utilise la librairie [JSFML](https://github.com/pdinklag/JSFML/wiki) qui est un binding de la SFML qui est écite en C++.
Cette librairie permet de faciliter la gestion des collisons, le chargement des textures et des sprite sheets ainsi que la gestion des inputs. 

Pour jouer téléchargez la release version 0.1 [ici](https://github.com/FuriousCatInteractive/projet-poo/releases).

## Contôles par défault:
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

## Modules présents:
* jeu de base 1v1 en local
* génération de niveau aléatoire
* bindings des touches à partir d'un fichier XML
* menus, musiques, feedbacks..


## screenshots

![screenshot1](res/img/screenshot (5).png)
![screenshot2](res/img/screenshot (1).png)
![screenshot3](res/img/screenshot (4).png)
![screenshot4](res/img/screenshot (2).png)


# Quelques détails techniques

## Gestion des entrées clavier

### Parsing : 

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

### Utilisation des bindings

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

### Moteur Graphique

En ce qui oncerne l'affichage d'éléments la JSFML est assez performante. En effet nous avons créer un ArrayList<Drawable>
qui contient tous les éléments à afficher à l'écran. Ainsi, à chaque fois qu'on doit afficher les ojbets on à juste à parcourir ce tableau.
En ce qui concerne les sprite, on commence à charger l'image dans la mémoire et on a une fonction qui décide quelle partie de l'image il doit afficher en fonction de l'état du joueur associé.

### Moteur physique

Chaque joueur gère sa propre physique dans un thread, en effet une hitbox lui est associé et en fonction des collisions il met l'état du joueur à jour.
Pour vérifier les collision le moteur physique du joueur parcourt le tableau de tous les objets ayant une hitbox et vérifit si l'intersection avec la hitbox du joueur est différente de `null` ou non. Le résultat de ce test nous donne un rectangle ou les deux hitbox se supperposent et on adapte la postion du joueur en fonction de ce rectangle.


### Gestion des sons

Tous les sons et les musiques sont chargés dans un thread en parrallèle du bootsplash. En effet ces appels systèmes sont assez lourds et prennent du temps car les musiques peuvent faire quelques Mo (même si on utilise le format .ogg qui est plus léger que le .wav) 
Ainsi on attend qque toutes les musiques soient chargées avec la méthode `join()` de la classe Thread avnt de passer au menu prinicpal.
Ensuite il suffit juste d'appeller `music.play()` pour lancer la musique qui est jouée dans un thread à part (gérer par la JSFML).
 
