# Guess_the_Animal
## Table of contents
* [General info](#general-info)
* [Setup](#setup)
* [Features](#features)
## General info
Project is a simple interactive game based on a binary tree structure. The computer try to guess the animal that the user has in mind with the help of "yes" or "no" questions. During the game, the computer extends its knowledge by learning new facts about animals and use these informations in the next game.  
## Setup
The only setting is the database filetype. To change it simply use `-type` command line argument e.g. `-java Main -type json`. The game supports `.json`, `.xml` and `.yaml` filetypes. The default one is `.json` file format.
## How to use
If the program detects existing database it loads it and greets you with:
![Greetings](/images/greeting.JPG)

If it won't find any existing database it simply asks you for your favourite animal to create new tree.
To answer affirmative you can type: `y`, `yes`, `yeah`, `yep`, `sure`, `right`.
To make a negative reply type: `n`, `no`, `nah`, `nope`, `no way`, `negative`. 


## To do
