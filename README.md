# Hostile Mobs and Girls
More Hostile Mobs and Girls!!!
## Minecraft 1.21.1 enchantments
- `hmag:health_boost`, `hmag:water_aspect`, and `hmag:anti_air` are datapack enchantments in `src/main/resources/data/hmag/enchantment/`. Their IDs and translation keys are unchanged. No enchantment registry bootstrap or datagen run is needed; the shipped JSON is loaded by the dynamic registry.
- The old `enchantment` config section (`healthBoost*`, `waterAspect*`, `antiAir*` max-level/treasure/tradeable/discoverable options) has been removed. Existing values are **not migrated**. Override the enchantment JSON and enchantment tags in a world datapack instead; common-config reloads cannot safely redefine a world's synchronized enchantment registry. `max_level` is now constrained by Minecraft's enchantment codec (1–255).
- Defaults remain level V, with weights 5/2/2 and anvil costs 2/4/4 for Health Boost/Water Aspect/Anti-Air. Minimum costs at level I are 13/5/7, rising by 5 per level; maximum costs are minimum +20. Health Boost supports armor, Water Aspect supports swords (the former `WEAPON` category), and Anti-Air supports crossbows.
- Acquisition is controlled separately by `minecraft` enchantment tags: `treasure`, `non_treasure`, `in_enchanting_table`, `tradeable`, `double_trade_price`, `on_random_loot`, `on_mob_spawn_equipment`, and `on_traded_equipment`. Health Boost is treasure, tradeable (double price), and available in random enchanted loot, but not in enchanting tables or random mob/traded equipment. Water Aspect and Anti-Air are non-treasure and available in all those ordinary acquisition pools. To disable discovery, remove the enchantment from the table/loot/equipment tags; to change treasure status, also update the price and acquisition tags. Tags are additive by default; a higher-priority pack must replace the relevant tag while retaining other desired entries, or use NeoForge tag removals.
- The `hmag:incompatible_with_*` and `hmag:unremovable_curses` tag IDs are unchanged. Exclusivity is checked in both directions by Minecraft; Water Aspect still excludes Fire Aspect and the optional `minecraft:soul_fire_aspect`. Curse removal uses `minecraft:curse` and preserves curses in `hmag:unremovable_curses`.
- Health Boost uses the native `minecraft:attributes` component: +1 health point per level per equipped armor piece, with slot-specific modifier identifiers managed by Minecraft. This restores the intended upstream effect, whose old UUIDs were unused in this port. Water Aspect and Anti-Air deliberately retain their damage event behavior rather than duplicating it in effect components: non-projectile Water Aspect extinguishes the target and adds 2.5 damage/level against water-sensitive mobs; crossbow-arrow Anti-Air multiplies damage by `1 + 0.3 * level` against airborne, non-passenger targets outside water/bubbles/lava, reading the maximum level in the attacker's hands **at impact**, not launch. These two effects remain tied to their IDs in Java.
- Upgrade data stays at `data/<namespace>/hmag/enchantment_upgrade/*.json`, with string enchantment IDs on disk and over the network. IDs are resolved against the current world's registry, not cached as holders. Upgrade limits are independent of the enchantment definition and can intentionally exceed its ordinary maximum. Enchanted books remain excluded from upgrading by the default `hmag:enchantment_not_upgradables` tag; datapacks may remove that exclusion. Crafting uses the appropriate enchantment/stored-enchantment component and preserves unrelated item data.
- Only directly affected item tags and the upgrade/curse-removal recipes have moved from `tags/items` and `recipes` to 1.21.1's singular `tags/item` and `recipe` folders. Other legacy resources/APIs in this ongoing port are outside this migration.

### Validation and remaining gameplay checks
Use Java 21 with `./gradlew compileJava`, `./gradlew build`, and `./gradlew runData`. There is no existing unit-test suite or registered GameTest suite in this repository. Static JSON/tuning checks cover all three definitions, costs at levels I–V, weights, anvil costs, supported equipment, translations, acquisition, Health Boost scaling, and Water Aspect exclusivity. These are not a substitute for Minecraft codec parsing or gameplay tests.

In the migration sandbox, the unchanged baseline `compileJava` stopped before compilation: `neoform-runtime:2.0.24` could not be downloaded because Maven hosts (including `maven.neoforged.net`) failed DNS resolution. Compilation/runtime success is therefore not established. Version-specific APIs and JSON schemas were checked against 1.21.1 sources and vanilla data. Known unrelated legacy code still includes item NBT access and other unported recipes; this migration does not claim to complete the full mod port.

Manual verification once the rest of the port runs:
1. Load a new world and an existing world; check all three IDs and level-V creative books. Override a definition in a datapack, reload/rejoin, and check the displayed maximum and recipe lookups; switch worlds to check that no registry holders persist.
2. Equip, replace, and remove Health Boost I/V in each armor slot and all four together. Confirm +1/+5 health per piece, no main/offhand bonus, and no residual/duplicate modifiers after death, relogging, or repeated swaps.
3. Check Water Aspect on a burning ordinary mob and on a Blaze/Enderman, including a projectile negative case. Check incompatibility with Fire Aspect in both anvil orders and when applying an upgrade.
4. Shoot crossbow arrows at grounded/airborne targets, then water/bubbles/lava/passenger targets; switch hands/weapons between launch and impact to verify hit-time Anti-Air levels and single application.
5. Upgrade an item with multiple enchantments, a name, damage, lore, and custom components; verify only the selected level changes and the prior-work penalty follows the recipe behavior. With a datapack allowing books, repeat on stored enchantments. Remove removable curses while retaining other enchantments and tagged unremovable curses; check named books and JEI examples.
6. Check enchanting, ordinary/treasure loot, librarian prices, equipment enchantments, multiplayer upgrade-data synchronization, and datapack reloads.

## Download
- [GitHub](https://github.com/Mechalopa/Hostile-Mobs-and-Girls/releases) / [CurseForge](https://www.curseforge.com/minecraft/mc-mods/hostile-mobs-and-girls)
## Japanese Forum and Descriptions
- [Minecraft Japan Forum Thread](https://forum.civa.jp/viewtopic.php?f=3&t=750)
## Credits
- `es_es.json` by not-a-java-dev
- `ru_ru.json` by Tefnya, Quarkrus
- `zh_cn.json` by Kzeroko, SodiumZH
- `zh_tw.json` by RaebLin
