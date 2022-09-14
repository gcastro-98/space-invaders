# space-invaders
## Small &amp; simple project to emulate the classical 'Space Invaders' (1978) game in Java.

This project aims to reproduce the typical flow in a videogame, naturally implemented in frameworks such as Unity (C#). Essentially, an infite loop is performed each time and each iteration constitutes a frame. Within each frame every objects' state, position & physics are updated (similarly to Update(), LateUpdate() & FixedUpdate() functions in Unity). Specifically, after initializing every object at the beginning, for each frame the following will be carried out:
- All the objects will update its position.
- Then, if need be, the enemies or the player will shoot.
- After that, there will be collision checking (to ensure whether any "element" - any object that can be hit - has been shot).
- Finally, the screen will be printed.

In my case, I will be inspired by one of the most famous well-known videogames in history: 'Space Invaders'. Thence, my idea for the game is to contain a player spaceship trying to avoid bullets from 3 different kind of enemies (and the player may be protected below under some walls). All these structures (except from bullets) can be damaged more than once. The game ends once every enemy has been killed or the player dies.

### Requisites
You will need a Java compiler: in my case, I used the compiler of the following version of the _open_ Java Development Kit (JDK) for Ubuntu 22.04:
```console
openjdk 11.0.16 2022-07-19
OpenJDK Runtime Environment (build 11.0.16+8-post-Ubuntu-0ubuntu122.04)
OpenJDK 64-Bit Server VM (build 11.0.16+8-post-Ubuntu-0ubuntu122.04, mixed mode, sharing)
```

### Documentation
I will document the project using Javadoc (Ubuntu 22.04: ``libloader-java-doc``). In case you wanted to fork the project, modify it and improve it and, then, update your documentation by running javadoc in the ``src`` folder and set ``doc`` as the output folder. In Linux, it would be:
```console
user@device:~/space-invaders/src$ javadoc *.java -d ../doc
```
