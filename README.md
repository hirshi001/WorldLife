# NewGame
This is a new game that I am creating. I don't exactly know what it is about myself, I am just currently developing 
some basic logic. Thats why the name is just NewGame.

To play the game, click on the desktop-1.0.jar file and download it.
You can run it either by double clicking it or through the terminal.

Instruction on how to play:
<br>
WASD to move.
<br>
LeftClick to shoot
<br>
Space to enter buildings (currently nothing inside of the building, just some grass)
<br>
R to toggle camera follow.
<br>
LeftShift to move camera to player.
<br>
Right Click and Drag to move camera around the field. (This automatically turns of camera follow)




MODDIFICATIONS:
If you want to mod the game, good luck cause there are no tutorials, however there are a few things to note.
Creating new mobs, structures, projectiles, and items are easy. Just use the code in other Objects as an example. Make sure the new thing you are creating has the correct inheritance structure.

Items don't do anything. I have plans to add functionality to items and hopefully make them compatibale with mobs. If you want to do this yourself, in the BoxGameEntity class (this class is for mobs), there is an onTouchingItem(Item i) method which you can use.

