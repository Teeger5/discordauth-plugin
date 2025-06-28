# Discord hitelesítő Spigot szerverhez
## Működése
1. Csatlakozik a játékos a szerverhez
2. A játékost megállítja a plugin; nem tud cselekedni
3. A plugin a hozzá tartozó Discord botnak küldi a játékos UUID-ját 
Socket kapcsolaton keresztül
4. Ha a bot talál az adatbázisban Discord felhasználó ID-t a játékos UUID-hez társítva,
akkor DM üzenetet küld neki
   - Az üzeneten van egy Hitelesítés gomb, amire a felhasználónak kattintania kell
   - A gomb ID értékkében benne van az UUID
   - Ha nem található az UUID-hez tartozó felhasználó, 
   akkor a bot jelzi azt a plugin-nek, ami erről értesíti a játékost
5. A Hitelesítés gomba kattintva a bot értesíti a plugin a hitelesítés megtörténtéről
   - Visszaküldi a gomb ID értékében lévő UUID-t
6. A plugin továbbengedi a felhasználót
   - Hozzáadja az UUID-t a hitelesített felhasználókhoz,
   ezután már nem lesz korlátozva
7. A plugin értesíti a botot a sikeres belépésről
8. A bot erről üzenetet küld a játékosnak

### SocketCommand
Ebben az enumban a plugin és a bot között küldhető parancsok vannak

Bot küldi a pluginnak:
- `SUCCESS` - sikeres hitelesítéskor, azaz Hitelesítés gombra kattintáskor
- `USER_FOUND` - A játékos UUID-hez tartozik Discord ID
- `USER_NOT_FOUND` - A játékos UUID-hez nem tartozik Discord ID
- `INITIATED` - Hitelesítő gombos üzenet elküldve a felhasználónak

Plugin küldi a botnak:
- AUTH_REQUEST - Hitelesítési kérés a játékos csatlakozásakor
- SUCCESS_CONFIRMED - A játékos megkezdhette a játékot
