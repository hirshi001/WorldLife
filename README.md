# NewGameMaker
This is a new project I am making for PlatHacks. This is a working base project that is very easy for other people to modify and make their very own unique games! This project uses Libgdx so if you know how to use Libgdx, you can do much more things that maybe I haven't even intended to be possible. If you don't know how to use Libgdx, thats fine as well. Very little use of the Libgdx library is needed to make modifications.

Link to youtube video: https://youtu.be/Q8lvFCoBBXE

Link to a small game I made with this mentioned in youtube video: https://drive.google.com/file/d/1w08dXWVDM5nCaaDCePyHeYk6UA-vuxd3/view?usp=sharing

To play the game, click on the desktop-1.0.jar file and download it.
You can run it either by double clicking it or through the terminal.

Instruction on how to play:
<br>
WASD to move.
<br>
Left Click to shoot
<br>
Press Space when in front of a door to enter buildings (currently nothing inside of the building, just some grass)
<br>
R to toggle camera follow.
<br>
LeftShift to move camera to player.
<br>
Right Click and Drag to move camera around the field. (This automatically turns of camera follow)

<br>
<br>
<br>

MODDIFICATIONS:
NOTHING IN THE PROJECT IS GAURENTEED TO BE THREAD SAFE. ASSUME NOTHING IS THREAD SAFE.
If you want to mod the game, good luck cause there are no tutorials because of time restraints, however once you understand the code, creating mods for the game is very, very simple. I coded the project the way I did just mainly for creating mods. 
While modding the game, there are a few things to note.

Creation of the starting field, structures, and mobs should happen in the startup method in GameApplication.

Creating new mobs, structures, projectiles, and items are easy. Just use the code in other Objects as an example. Make sure the new thing you are creating has the correct inheritance structure.

Items don't do anything. I have plans to add functionality to items and hopefully make them compatibale with mobs. If you want to do this yourself, in the GameMob class (this class is for mobs), there is an onTouchingItem(Item i) method which you can use.

Structures will not be placed if there is another structure or a collidable tile in the way.

Make sure to use the Registry class properly if you want to add different kinds of tiles.

Also, there is nearly no documentation because I'm grinding right now and don't have time to. 

<br>
<br>
<br>

New Features are always coming out! So modders, check this page often to see the new changes.
