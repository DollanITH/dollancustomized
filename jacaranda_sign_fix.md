# Jacaranda Sign GUI Fix

## Issue Summary
The Jacaranda sign was not opening its GUI properly. The debug logs showed:
- Client receiving packet successfully
- Tile entity being null at the specified position
- Client not recognizing it as TileEntityJacarandaSign

## Root Causes Found

### 1. TileEntityJacarandaSign not extending TileEntitySign ✅
- **Problem**: TileEntityJacarandaSign was extending TileEntity directly
- **Fix**: Changed it to extend TileEntitySign so it's compatible with the vanilla GUI system

### 2. Missing Item Registration for Standing Sign ✅  
- **Problem**: JacarandaStandingSign block was registered but no item for it
- **Fix**: Added item registration in the Registry inner class

## Files Modified:

### 1. TileEntityJacarandaSign.java
- Changed: `extends TileEntity` → `extends TileEntitySign`
- This allows proper integration with vanilla sign GUI system

### 2. JacarandaStandingSign.java
- Added item registration in the Registry class
- Added model registration for the item

## Current Implementation
The code now uses vanilla's `player.openEditSign()` method which:
1. Works with TileEntitySign (which TileEntityJacarandaSign now extends)
2. Automatically handles the GUI opening on client side
3. Synchronizes data properly between server and client

## Expected Behavior
After these fixes:
1. Place jacaranda sign (wall or standing)
2. GUI should open automatically
3. Text should be editable
4. Sign should persist data correctly

## Verification Steps
1. Build the project: `gradlew clean build`
2. Start the game
3. Place a jacaranda sign
4. Verify GUI opens
5. Verify text can be entered and saved