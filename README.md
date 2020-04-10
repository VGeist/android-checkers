# android-checkers
Checkers game for android

ADDED vector images for checker pieces
ADDED game board graphics
Created RecyclerView to display log information
GameActivity only tracks buttons for tiles that are used in checkers, this is to make the file easier to read
ADDED AddGameBoardButtons() to GameActivity - used to keep onCreate() easy to read
ADDED UpdateGameTileButton to GameActivity - if tile has a checker, displays appropriate icon and enables button 
ADDED abstract Piece class
ADDED Man class
ADDED King class

To finish for part 3 - 
allow pieces to move
	- block movement with pieces in the way
	? add jumping and piece capture
add stand-in movement logging
? implement becomeKing