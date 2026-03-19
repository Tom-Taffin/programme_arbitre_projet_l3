# Programme Arbitre

Le programme arbitre permet d'assurer que les parties se déroulent correctement.
C'est ce programme qui va notamment s'occuper de la validation des coups, de la gestion des meeples, du comptage de points et de la distribution des blâmes.

## Installation

```bash
Mettre commandes ici
```

## Fonctionnement général

L'arbitre s'articule autour de deux fichiers principaux: **Game.java** pour la logique du jeu, et **RefereeView.java** pour communiquer avec le réflecteur.

On peut voir **RefereeView.java** comme une machine à état, où les états représentent les différentes parties d'un tour de jeu.

![State Machine](./images/state_machine_referee_view.jpg)

Si un état a besoin de vérifier, ou de mettre à jour un élément de jeu, il délègue la tâche à **Game.java**.

**Game.java** garde en mémoire tout ce qui se rapporte à la partie en cours: les joueurs, le plateau, les meeples... Et dispose de toutes les méthodes nécessaires pour interagir avec.

Pour le plateau, les joueurs et le score, la logique devient compliqué à gérer au sein d'une seule classe, donc on a préféré faire des classes *Manager* pour ces éléments auxquels **Game.java** va faire appel pour déléguer les tâches.

Voyons un exemple de déroulement d'un état:

Dans l'état *OfferTile*, on est au début du tour. On doit piocher une tuile et l'offrir au joueur courant. **RefereeView.java** commence par demander à **Game.java** de piocher une tuile.

Ensuite, **RefereeView.java** va envoyer cette tuile au joueur via le réflecteur, puis se mettre en attente d'une commande *PLACES*.

Selon la commande *PLACES* reçu, la machine à états va aller dans l'état correspondant et continuer le tour de jeu.

Si aucune commande *PLACES* n'est reçu au bout d'un certain temps, le programme lui distribue un blâme.

Si, durant l'état *OfferTile*, la pioche est vide ou il n'y a plus qu'un joueur en jeu, la machine à état bascule dans *endsGame* et le jeu se termine.

## Dépendances

- Game Elements
- Carcassonne Connection Library