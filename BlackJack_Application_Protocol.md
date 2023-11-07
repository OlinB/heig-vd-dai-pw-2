
# Application Protocol : BlackJack

#### Auteurs: Kevin Farine, Olin Bourquin
## Overview
Le protocol BlackJack est un protocol client-serveur permettant de simuler une partie de BlackJack en tant que joueur (client) contre un croupier (serveur).
Le client se connectera au serveur et pourra démarrer une partie standard de BlackJack contre le serveur.
## Features
à définir
## Transport protocol
Port utilisé : à définir
Protocol utilisé : TCP
## Messages
### Client
- Hit
- Stand
### Serveur
- Ok
- Player-Hand (visible 2+)
	- De quel joueur, si multijoueur
- Dealer-Hand (1 visible, 1 cachée, avant de révéler)

