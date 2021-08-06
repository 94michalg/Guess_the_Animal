# Guess_the_Animal
## Table of contents
* [General info](#general-info)
* [Setup](#setup)
* [How to use](#how-to-use)
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
These are just sample answers, program understands some more complex answers too.

To start playing just choose 1st option. The program will start to ask you some questions and in the end it will guess the animal. If it's right you lost and the tree stays untouched. If you won it will ask you for your answer and try to learn how to distinguish these two animals e.g.
![Distinguish question](/images/distinguish.JPG)

The sentence should satisfy on the following templates: "It can ...", "It has ..." or "It is ..." e.g. "It is a mammal". As you play the knowledge tree grows bigger and the program becomes smarter.

To list all of the animals choose 2nd option:
![List animals](/images/list.JPG)

The 3rd option lets you to list all of the known facts about particular animal:
![Facts about particular animal](/images/facts.JPG)

Last two options lets you to print the tree (for now as a plain text) or print all statistics of the tree:
![Statistics](/images/statistics.JPG)


## To do
