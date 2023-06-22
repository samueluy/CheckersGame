# Checkers Mini-Max Alpha Beta Pruning

This documentation is for the Checkers Mini-Max Alpha Beta Pruning program that I developed as part of the CSINTSY course at De La Salle University – Manila.

[Documentation](https://docs.google.com/document/d/1qL85K-IjkLkhqLiNrbg-mT6uK05iV6sKE8jl3-QuCMs/edit?usp=sharing)

## State Representation

The Checkers program consists of six classes. The `Board` class represents the game board, including the cells, counters for pieces, and the current player. The `Cell` class represents each square on the board. The `Piece` class represents each piece in the game. The `Move` class contains methods related to moving pieces and implements the Mini-Max algorithm with alpha-beta pruning. The `Node` class represents nodes in the game tree, and the `Game` class is the driver class.

## Utility Function

To calculate the heuristics for the game, the program assigns numerical values to different aspects of the game board. Each regular piece is given a value of 3, and a king piece is given a value of 5. The ability of pieces to control the center of the board is also considered. The heuristic value of a node is calculated by subtracting the points of the black side from the points of the white side. A positive value favors the white side, and a negative value favors the black side.

## Experiment Results

To test the effectiveness of move ordering, 10 simulations were run with and without move ordering. The results showed that move ordering improved the performance of the algorithm, with an average improvement of 1,399,216.3 ns. Although the best time achieved was without move ordering, move ordering consistently provided better performance over the course of a game.

## Game Complexity

The average branching factor of a game of checkers is approximately 6.4, and the average number of plies to finish a game is 60. Based on these approximations, the game-tree complexity of Classical Checkers is around 6.460. The implementation of move ordering in this program improved the effective branching factor, reducing it to 22 from 36 without move ordering.

## Skills and Challenges

During this project, I applied my existing skills in board representation and navigation. However, implementing AI moves and the MiniMax alpha-beta pruning algorithm required me to acquire new skills. I faced challenges in duplicating the board, understanding and implementing the algorithm, and translating checkers strategies into calculatable heuristics. Additionally, limited internet access and workspace during the break presented additional challenges, but they allowed me to dedicate time to plan the project.

For more details, please refer to the references mentioned in the documentation.

Submitted to: Ms. Merlin Teodosia Suarez
