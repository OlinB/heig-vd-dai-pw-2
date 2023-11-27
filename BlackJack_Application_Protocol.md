
# Application Protocol : BlackJack

#### Auteurs: Kevin Farine, Olin Bourquin
## Section 1 - Overview
Le BJ (BlackJack) protocol est un protocol client-serveur permettant de simuler une partie de BlackJack en tant que joueur (client) contre un croupier (serveur).
Le client se connectera au serveur et pourra démarrer une partie standard de BlackJack contre le serveur.
## Section 2 - Transport protocol
Le BJ protocol est un protocol de jeu.
Il doit utiliser le protocol TCP (Transmission COntrol Protocol) pour s'assurer de la
transmission des données et doit utiliser le port ****

La connexion initiale est initiée par le client. 
Le client peut refuser la connexion si le nombre de connexion maximum est atteint.
Le nombre de connexion maximum est de 7.
Au-delà de ce nombre, le serveur refusera toute nouvelle connexion.

Une fois la connexion établi, le client peut lancer une nouvelle partie ou bien quitter via une commande.

Une fois la nouvelle parie commencée, la main du croupier et du joueur sont affichées.
À ce moment-ci, le joueur pourra effectuer différentes actions, comme tirer une nouvelle carte ou se contenter de sa main actuelle.

Le serveur devra s'assurer que les actions effectuées sont possibles dépendant de l'instant dans la partie.
Si l'action est possible, le serveur affichera le résultat du tirage ou de la partie. 
Si l'action n'est pas autorisé, un message d'erreur sera renvoyé.

## Section 3 - Messages
### Client
- CONNECT
- HIT
- STAND
### Serveur
- OK
- Player-Hand (visible 2+)
	- De quel joueur, si multijoueur
- Dealer-Hand (1 visible, 1 cachée, avant de révéler)

## Section 4 - Exemples


