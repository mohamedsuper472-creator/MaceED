# MaceED (Fabric 1.21.11)

Makes the player's hitbox bigger (or smaller). Width and height are set separately.

## Commands
- `/hitbox` - show the current values
- `/hitbox set <width> <height>` - set both (multipliers, 1.0 = normal)
- `/hitbox width <value>` / `/hitbox height <value>`
- `/hitbox reset`

Vanilla player = 0.6 wide, 1.8 tall. Example: `/hitbox set 3 2` = 1.8 wide, 3.6 tall.
Values are limited to 0.1 - 20. They are saved in `config/maceed.json`.

## Build
1. Install JDK 21 and Gradle (or open the folder in IntelliJ IDEA and let it import).
2. In this folder run: `gradle build`
   (if you have a wrapper from the Fabric example mod, `./gradlew build` works too)
3. Take `build/libs/maceed-1.0.0.jar` and put it in your `mods` folder with Fabric API.

## Notes
- The eye height scales with the height value, so the camera moves up/down with the hitbox.
- On a multiplayer server, install the mod on the server AND the clients, with the same values.
- Any player can use `/hitbox` (no permission check) - fine for singleplayer/friends.
