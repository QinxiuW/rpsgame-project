# rpsgame-project

* Developer: Qinxiu Wang
* Email: Wqinxiu@gmail.com

## PROBLEM DESCRIPTION
Write a program that plays 10 iterations of Rock, Paper, Scissors.
There should be three modes of play, one fair, where both players should play always randomly,another unfair, where one player should always play randomly, the other should always choose rock. And the last one when one of the players is remote and has to be reached through an HTTP call. In this mode both play randomly. The mode should be asked at the beginning of the iterations through the command line. It should show each player's play and the outcome for every game (iteration), and at
the end of the iterations, how many games each player has won and how many were a draw. This output should go to the console or a file. This should be asked through the command line as well.

## TECHNOLOGIES
| TECHNOLOGY        | DESCRIPTION                                        |
| ----------------- | -------------------------------------------------- |
| Intellij IDEA     | Integrated Development Environment (IDE)           |
| Spring boot       | Main java framework v.2.5.3                        |
| CheckStyle-Idea   | Intellij IDEA plugin for check code specifications |
| google code style | Coding standard                                    |
| PostMan           | For send REST API requests                         |
| Processon         | For editing charts                                 |

## SERVER DETAILS

### DEFAULT PORT
    * 8080

### HTTP CALLS FOR REMOTE MODE
    * Player connection: http://localhost:8080/v1/playerConnect?player=pep
    * Player choice: http://localhost:8080/v1/choiceConnect?choice=Rock

### COSTUM RESPONSE CODE
| CODE        | DESCRIPTION    |
| ----------- | -------------- |
| 200000      | OK             |
| 400001      | INVALID PARAMS |
| 400002      | INVALID CHOICE |
| 400003      | INVALID PLAYER |

## WORK FLOW CHART
![image](https://github.com/QinxiuW/rpsgame-project/blob/master/work-flow-chart.png)
