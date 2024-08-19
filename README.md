# Virtual World Simulator in Java

## Project Overview

The objective of this project was to create a virtual world simulator using a graphical interface implemented with the Swing library in Java. The simulator operates on a grid with a customizable size that the player can choose before starting the game. In this world, simple life forms with different behaviors occupy individual cells on the grid. Each cell can contain at most one organism, and in case of a collision, one of the organisms is either removed or moved.

The simulator is turn-based. During each turn, all organisms in the world perform actions according to their type. Some organisms, like animals, move, while others, like plants, remain stationary. When two organisms end up on the same cell, a collision occurs, and one of them wins by either killing or repelling the other. The order of movement during a turn depends on the organism's initiative, with higher initiative organisms moving first. In cases where organisms have the same initiative, the older one moves first.

Victory in a collision primarily depends on the organism's strength, with some exceptions as specified by the game's rules. When two organisms have equal strength, the one that attacks first wins.

A specific type of animal is the Human, which, unlike other animals, does not move randomly. The direction of movement is determined by the player using the arrow keys on the keyboard. The Human also has a special ability that can be activated using a separate key. Once activated, this ability lasts for five consecutive turns, after which it is deactivated and cannot be used again for the next five turns.

When the program is launched, several instances of each type of animal and plant appear on the grid. The application interface includes various Swing components such as buttons and menu items that allow the player to interact with the game, such as advancing to the next turn, saving the game, or loading a previously saved game.

## Class Implementation

### World Class

The `World` class manages gameplay and organisms. It includes methods such as `executeTurn()` and `drawWorld()`, as well as a 2D array `board` that stores all the organisms present in the world. The `World` class is responsible for managing the state of the game, including the positions of organisms, their actions, and interactions.

### Organism Class

The `Organism` class is an abstract class from which two more abstract classes, `Plant` and `Animal`, are derived. The `Organism` class defines basic fields such as strength, initiative, position `(x, y)`, and a reference to the world in which it is located. It also includes methods such as `action()`, which determines the behavior of the organism during a turn, `collision()`, which defines the organism's behavior during contact with another organism, and `draw()`, which handles the graphical representation of the organism.

### Animal Class

The `Animal` class implements behaviors common to most animals, such as basic movement, which in the `action()` method moves the animal to a randomly selected adjacent cell, and reproduction, which occurs in the `collision()` method when encountering an organism of the same species.

### Human Class

The `Human` class extends the `Animal` class and is controlled by the player. The Human does not reproduce and has a unique ability that the player can activate. The player's input determines the Human's movements and actions within the game world.

## Specific Behaviors of Organisms

Animals and plants have different specific `action()` and `collision()` methods that determine their behavior in the world:

- **Wolf** (strength: 9, initiative: 5) and **Sheep** (strength: 4, initiative: 4) do not have special abilities in either the `action()` or `collision()` methods.
- **Fox** (strength: 3, initiative: 7) has a keen sense of smell and never moves to a cell occupied by an organism stronger than itself.
- **Turtle** (strength: 2, initiative: 1) in 75% of cases does not change its position and repels attacks from animals with strength less than 5.
- **Antelope** (strength: 4, initiative: 4) has a movement range of two cells and a 50% chance to escape before a fight, moving to an unoccupied adjacent cell.

All plants have zero initiative and various effects related to being eaten:

- **Grass** (strength: 0) and **Dandelion** (strength: 0) can spread to adjacent cells, with Dandelion attempting to spread three times in one turn.
- **Guarana** (strength: 0) increases the strength of the animal that eats it by 3.
- **Deadly Nightshade** (strength: 99) kills the animal that eats it.
- **Giant Hogweed** (strength: 10) kills all animals in its vicinity.
## Saving and Loading Game State

The virtual world simulator allows saving and loading the game state to/from a file. The player can save the current game state at any time using the "Save" option in the menu. The saved game state includes the positions of all organisms, their age, strength, and other relevant parameters. The player can also load a previously saved game state using the "Load" option, allowing them to continue the game from the point of the last save.

## Adding Organisms

The game also supports adding new organisms to the world. By clicking on an empty cell in the grid, the player can choose from a menu of available organisms to add to that cell. This feature provides additional control over the game's dynamics and allows for more interactive gameplay.

## Controls

The player controls the Human character and interacts with the game using the following controls:

- **Arrow Keys** - Move the Human in the specified direction (up, down, left, right).
- **Enter** - Proceed to the next turn without moving the Human.
- **P** - Activate the Human's special ability.
- **Save (Menu Option)** - Save the current game state.
- **Load (Menu Option)** - Load the game state from a file.
- **Exit (Menu Option)** - Exit the game.
- **Click on an empty cell** - Add a new organism to the world.

## Summary

The Java project provides a rich simulation of a virtual world where animals and plants interact according to the implemented rules. The game is designed with object-oriented programming principles, focusing on polymorphism and inheritance. Unlike the <a href="https://github.com/pavgos22/virtual-world-cpp">C++ version</a>, this implementation includes a graphical user interface (GUI) developed with the Swing library, allowing for more interactive gameplay. The game features saving and loading capabilities, as well as the ability to add new organisms during gameplay, offering a dynamic and engaging user experience.
