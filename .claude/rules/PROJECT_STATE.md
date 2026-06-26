# PROJECT STATE — RPG MODPACK

## CURRENT VERSION

**v0.3.9p2** — v0.3.9p + Performance Optimization Pass #2 (2026-06-26)

Build: `./gradlew build` → `BUILD SUCCESSFUL`
Java: 17 (Eclipse Adoptium)
Gradle: 8.5
Forge: 1.20.1-47.2.0 (chạy được trên 47.4.20)
Mappings: official 1.20.1

---

## COMPLETED SYSTEMS

### 1. BUILD SYSTEM

| File                                         | Status                                                                    |
| -------------------------------------------- | ------------------------------------------------------------------------- |
| `gradle/wrapper/gradle-wrapper.properties` | Gradle 8.5 (fix treo transform)                                           |
| `gradle.properties`                        | `org.gradle.watch-fs=false`, `forge_version=47.2.0`, 4GB heap         |
| `build.gradle`                             | Buildscript approach, ForgeGradle 6.0.24                                  |
| `settings.gradle`                          | pluginManagement → maven.minecraftforge.net                              |
| `mods.toml`                                | Hardcoded (không dùng template expand),`license="MIT"` global section |
| `pack.mcmeta`                              | pack_format 15                                                            |

### 2. CORE SYSTEM (`com.rpgpack.core`)

| Class                | Chức năng                                                                                                                                                                                                                                                                                       |
| -------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `ModStats`         | Enum 8 stats: STR, VIT, END, AGI, DEX, INT, WIS, LUK                                                                                                                                                                                                                                              |
| `PlayerData`       | Level, EXP (formula`100 * 1.5^level`), 8 base stats, statPoints, currentHp/Mana, selectedClass, currentVision, `skillMastery` Map, `skillRank` Map. NBT save/load + migrate từ `skillLevels` cũ. `currentStamina` field retained for NBT compat (unused).                             |
| `DerivedStats`     | 10 derived stats (maxHp, maxMana, physicalDamage, magicDamage, physicalDefense, magicDefense, critChance, critDamage, attackSpeed, moveSpeed, cooldownReduction, elementalBonus, manaRegen). Builder pattern.                                                                                     |
| `StatCalculator`   | Single source of truth. HP=`100+(VIT+bVit)*10+bHp`, Mana=`50+(INT+bInt)*5+bMana`. Mana regen=`3.0+(WIS+bWis)*0.15`. Crit=`(DEX+bDex)*0.25+bCritC`. CDR=`(WIS+bWis)*0.3`. Cooldown reduction=`(WIS+bWis)*0.3`. **Hỗ trợ item bonus** qua overload `calculate(data, player)`. |
| `PlayerCapability` | `ICapabilitySerializable<CompoundTag>`, `CapabilityToken`, `LazyOptional`. Forge capability tự động save/load NBT.                                                                                                                                                                       |

### 3. CLASS SYSTEM (`com.rpgpack.classes`)

| Class               | Base Stats (STR/VIT/END/AGI/DEX/INT/WIS/LUK) | Role               | Playstyle                     |
| ------------------- | -------------------------------------------- | ------------------ | ----------------------------- |
| **NONE**      | 0/0/0/0/0/0/0/0                              | —                 | Default before selection      |
| **WARRIOR**   | 10/8/8/5/5/2/2/2                             | Tank / Frontliner  | Soak damage, protect allies   |
| **BERSERKER** | 12/7/9/4/4/1/1/2                             | Heavy Melee DPS    | Big hits, AOE destruction     |
| **ASSASSIN**  | 5/4/5/10/12/3/2/4                            | Burst DPS / Crit   | Fast strikes, backstab crits  |
| **MAGE**      | 1/3/4/4/3/12/10/3                            | Magic DPS / Caster | Ranged magic, reaction combos |
| **CLERIC**    | 3/5/6/4/3/8/12/5                             | Support / Healer   | Heal allies, buff party       |

| Class            | Chức năng                                                                            |
| ---------------- | -------------------------------------------------------------------------------------- |
| `ClassType`    | Enum 6 values (NONE + 5 class).`getDisplayName()`, `getRole()`, base stat getters. |
| `ClassManager` | `applyClass(data, type)` → set class + base stats + full heal. Debug log.           |

### 4. SKILL SYSTEM (`com.rpgpack.skills`) — 20 SKILLS

| Class         | Key | Skill           | CD  | Mana | Stam | Type  | Effect                                                                |
| ------------- | --- | --------------- | --- | ---- | ---- | ----- | --------------------------------------------------------------------- |
| **WAR** | R   | Shield Bash     | 6s  | 15   | 20   | PHYS  | 150% STR + knockback. Nearest entity if no target.                    |
|               | G   | Battle Cry      | 12s | 30   | 0    | BUFF  | +30% DMG +20% DEF (5s)                                                |
|               | C   | Shield Wall     | 20s | 40   | 0    | BUFF  | -50% damage (5s)                                                      |
|               | V   | Earth Shatter   | 40s | 100  | 50   | PHYS  | 400% STR AOE 6 blocks                                                 |
| **BER** | R   | Ground Smash    | 7s  | 20   | 25   | PHYS  | 200% STR AOE 4 blocks. EXPLOSION particles.                           |
|               | G   | Blood Rage      | 14s | 35   | 0    | BUFF  | +30% DMG, -15% DEF (8s). FLAME particles.                             |
|               | C   | Whirlwind       | 18s | 30   | 35   | PHYS  | 120% STR × 3 spins AOE 3 blocks. SWEEP particles.                    |
|               | V   | Berserk Mode    | 40s | 80   | 40   | BUFF  | +50% DMG, +30% SPD, +20% Move (10s). FLAME column.                    |
| **SIN** | R   | Shadow Strike   | 5s  | 20   | 0    | PHYS  | 200% DEX. Nearest if no target. SMOKE+PHANTOM.                        |
|               | G   | Poison Blade    | 12s | 25   | 0    | PHYS  | 120% DEX + Poison 4s. Nearest if no target.                           |
|               | C   | Smoke Bomb      | 20s | 35   | 0    | UTIL  | Blind+Slow AOE 5 blocks. LARGE_SMOKE.                                 |
|               | V   | Execution       | 40s | 90   | 0    | PHYS  | 350% DEX. ×2 if target <30% HP. CRIT sound.                          |
| **MAG** | R   | Mana Burst      | 5s  | 15   | 0    | MAGIC | 300% INT AOE 2 blocks. ENCHANT particles.                             |
|               | G   | Arcane Missile  | 8s  | 25   | 0    | MAGIC | 200% INT raycast tracking. SOUL_FIRE particles.                       |
|               | C   | Blink           | 12s | 30   | 0    | UTIL  | Teleport 8 blocks forward. PORTAL particles.                          |
|               | V   | Meteor          | 45s | 150  | 0    | MAGIC | 500% INT AOE 4 blocks. FLAME+LAVA+EXPLOSION+LIGHTNING.                |
| **CLE** | R   | Heal            | 8s  | 20   | 0    | HEAL  | Heal 15 + WIS×2. HEART particles.                                    |
|               | G   | Holy Bolt       | 10s | 25   | 0    | MAGIC | 150% WIS raycast. Does NOT damage players.                            |
|               | C   | Purify          | 20s | 30   | 0    | HEAL  | Clear Poison/Wither/Weak/Slow/Blind + Regen 4s.                       |
|               | V   | Divine Blessing | 40s | 80   | 0    | BUFF  | 10s: Regen II + Resist II + Absorb II + Fire Res. LEVELUP+BELL sound. |

**Tất cả skill đều có:**

- Particle effects (16 loại particle khác nhau)
- Sound effects (20+ sound events)
- `!e.is(player)` AOE filter — không bao giờ damage caster
- Nearest-entity fallback khi `getLastHurtMob()` = null
- Class binding (`getClassType()`)

**Core skill classes:**

| Class                    | Chức năng                                                                                                                                                                                                                                                  |
| ------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| `BaseSkill`            | Abstract: id, name, manaCost, cooldownTicks, damageType, unlockLevel, classType.`staminaCost` field kept (unused). Rank scaling:`getDamageMultiplier(rank)` (E=100%→SS=350%), `getScaledCooldown(rank)` (E=100%→SS=60%), `getMasteryGain(target)`. |
| `SkillRegistry`        | `LinkedHashMap` lưu tất cả skill. `CLASS_SKILLS` (`EnumMap<ClassType, List<String>>`) cho class-specific lookup. `getSkillsForClass(type)`.                                                                                                       |
| `SkillCooldownManager` | Server-side:`Map<UUID, Map<String, Integer>>` (ConcurrentHashMap). Tick-based decrement. Iterator-based (không `removeIf`+`setValue` — tránh crash Microsoft JDK).                                                                                  |

### 5. COMBAT SYSTEM (`com.rpgpack.combat`)

| Class                 | Chức năng                                                                                                                                                                                                                        |
| --------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `CombatHandler`     | `LivingHurtEvent`: STR bonus cho physical, INT bonus cho magic (check `getMsgId()`). Defense reduction: `def/(def+100+level*10)`, cap 80%. Crit check: `random < critChance%`. Crit multiplier. MISS detection tick-based. |
| `PlayerTickHandler` | Mana regen (always), HP regen 0.5/s (out of combat only). XP on kill:`maxHealth*2 * (1+LUK*1%)`. XP orb: `PickupXp` event. Dirty sync mana/HP/exp mỗi 0.5s (only changed values). Stats cache via `getCachedStats()`.       |

### 6. NETWORK (`com.rpgpack.network`) — 7 PACKETS

| Packet                    | Direction      | Chức năng                                                                                                              |
| ------------------------- | -------------- | ------------------------------------------------------------------------------------------------------------------------ |
| `SyncPlayerDataS2C`     | Server→Client | Sync toàn bộ PlayerData (16 field + skillLevels). Handler:`copyFrom()` vào client capability.                       |
| `ChooseClassC2S`        | Client→Server | Chọn class. Server:`ClassManager.applyClass()` + sync vanilla max health + sync data. Reject nếu đã chọn.         |
| `AddStatPointC2S`       | Client→Server | Tăng stat. Server: switch 8 stat,`statPoints--`, update vanilla max health, sync immediate.                           |
| `OpenClassSelectionS2C` | Server→Client | Mở ClassSelectionScreen.`@OnlyIn(Dist.CLIENT)` guard.                                                                 |
| `UseSkillC2S`           | Client→Server | Cast skill. Server double-validate: cooldown, mana, level. Apply skill-level CDR + damage scaling. Sync data + cooldown. |
| `CooldownSyncS2C`       | Server→Client | Sync cooldown từng skill. Client update`SkillHotbarOverlay.clientCooldowns`.                                          |
| `RankUpC2S`             | Client→Server | Rank up skill (E→SS). Server validate level + mastery threshold, increment rank, sync.                                  |

**Packet registration:** `ModMessages.CHANNEL` (SimpleChannel, protocol "1"). 7 packets, sequential IDs (0→6).

**Spam protection:**

- Client: check `currentMana < manaCost` → `§cNot enough Mana!` action bar, không gửi packet
- Server: validate lại cooldown/mana/alive/class trước khi cast

### 7. GUI (`com.rpgpack.gui`)

| Class                    | Key         | Chức năng                                                                                                                                                                                                    |
| ------------------------ | ----------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `ClassSelectionScreen` | Auto        | 5 cards responsive (max 160px, scale theo width). Hover highlight, gold border khi selected. CONFIRM chỉ enable sau khi chọn. "✓ SELECTED" indicator. Switch case đầy đủ 5 class.`[CLASS]` debug log. |
| `CharacterScreen`      | **P** | Tab Character (8 base stats + nút [+] + 15 derived stats) + Tab Skills (4 skill cards, level badge, upgrade button). XP bar (LV X — XP / Required). Scroll đồng bộ.                                       |
| `SkillHotbarOverlay`   | HUD         | Bottom-right vertical layout, 28×28 slots.`[R] [G] [C] [V]` labels. Cooldown dark overlay + countdown timer. Resource cost text. `IGuiOverlay` registered above `HOTBAR`.                               |
| `RpgStatusOverlay`     | HUD         | Top-left: HP/Mana RPG bars. Ẩn vanilla HP, Armor, Food, XP bar.                                                                                                                                               |
| `GuiEventHandler`      | Client tick | Key handling: P→CharacterScreen, R/G/C/V→class-specific skills (index 0-3 từ`getSkillsForClass()`). Unlock level check + mana validation. ClientTick decrement cooldown.                                  |

### 8. KEYBINDS (`ModKeybinds`)

| Phím       | Chức năng                         |
| ----------- | ----------------------------------- |
| **P** | Character Screen (stats + allocate) |
| **R** | Skill 1                             |
| **G** | Skill 2                             |
| **C** | Skill 3                             |
| **V** | Skill 4                             |

Tất cả dùng `InputConstants.Type.KEYSYM` + `RegisterKeyMappingsEvent`. Category: `key.categories.rpgpack`.

### 9. COMMAND SYSTEM

| Command                                             | Chức năng                                                                                               |
| --------------------------------------------------- | --------------------------------------------------------------------------------------------------------- |
| `/class <warrior\|berserker\|assassin\|mage\|cleric>` | Đổi class ngay lập tức. Tab-completion. Reset stats về base, full heal, sync client.`[CLASS]` log. |
| `/rpg debug`                                      | Hiển thị toàn bộ stats, skill, cooldowns.                                                             |
| `/rpg reload`                                     | Full heal HP/Mana, reset stats cache, sync client.                                                        |
| `/rpg salvage`                                    | Phá hủy item RPG đang cầm → nhận iron/gold/diamond theo rarity.                                     |

`RegisterCommandsEvent` → `ClassCommand.register(dispatcher)`.

### 10. EVENTS (`ModEvents`)

| Event                     | Chức năng                                                                                                                                |
| ------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------ |
| `EntityJoinLevelEvent`  | **First join** (class=NONE): full heal + open class selection. **Reconnect**: chỉ sync data + cooldown, KHÔNG reset mana/HP. |
| `PlayerEvent.Clone`     | Death:`reviveCaps()` → copy toàn bộ data → `invalidateCaps()`. `isWasDeath()` guard.                                             |
| `RegisterCommandsEvent` | Register`/class` command.                                                                                                                |

### 11. CAPABILITY & PERSISTENCE

| Cơ chế                      | Status                                                                                                                                    |
| ----------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------- |
| NBT save (`serializeNBT`)   | Level, exp, statPoints, 8 stats, currentHp/Mana, selectedClass, currentVision, skillMastery, skillRank                                    |
| NBT load (`deserializeNBT`) | Safety:`tag.contains()` check cho string field. skillMastery/skillRank từ compound. Auto-migrate `skillLevels` cũ → `skillRank`. |
| Death (`PlayerEvent.Clone`) | Copy toàn bộ data từ old → new entity                                                                                                 |
| Reconnect                     | Giữ nguyên mana/HP từ NBT. Safety clamp ≤1 → 20% max.                                                                                |
| First join                    | Full heal + class selection                                                                                                               |

### 12. LOOT SYSTEM (`com.rpgpack.loot`)

| Class                | Chức năng                                                                                                                                                                                     |
| -------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `ItemRarity`       | 6 tier: Common (60% trắng) → Uncommon (25% xanh) → Rare (10% blue) → Epic (4% tím) → Legendary (1% cam) → Mythic (0% đỏ).`roll(luckBonus)` với luck từ LUK.                        |
| `LootGenerator`    | Roll 15 stat ngẫu nhiên. Số stat = rarity tier+1. Value scale theo rarity + mob level. Affix suffix 3 tier.`addLore()` cho tooltip.                                                        |
| `LootHandler`      | `LivingDropsEvent`: apply rarity+stats vào drop. Boss ≥200HP → Rare+ auto + 2% Mythic. Elite ≥80HP → Uncommon+. `ItemTooltipEvent`: hiển thị stat màu. Item name colored by rarity. |
| `ChestLootHandler` | **v0.3.8** — `PlayerInteractEvent.RightClickBlock` on chests: apply RPG rarity+stats to weapons/armor/tools on first open. Tracks "already rolled" via chest NBT.                      |
| `CraftLootHandler` | **v0.3.9** — `ItemCraftedEvent` + periodic inventory scanner (ServerTickEvent 1s): apply RPG stats to crafted items. Safety net catch shift-click vanilla items.                     |
| `ItemStatApplier`  | Tick mỗi 2s: đọc item đang mặc → bonus vào persistent data.`StatCalculator.calculate(data, player)` áp dụng bonus.                                                                   |

---

## STABILITY FIXES HISTORY

| Version    | Fix                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
| ---------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| v0.3.9p2   | **Performance Optimization Pass #2**: (1) TargetHUD BossTier array-index thay for-loop + cache entity name/tier (tránh NBT read + String allocation mỗi 60fps frame). (2) SkillHotbarOverlay `String.format` → manual int+frac (zero allocation cooldown text). (3) CraftLootHandler dirty-player tracking — scanner chỉ chạy cho player vừa craft (0 scan khi idle). (4) PlayerTickHandler `getOrDefault` → `get`+null check (tránh Float/Integer boxing). (5) RpgStatusOverlay + SkillHotbarOverlay cache invalidation khi player change. BUILD SUCCESSFUL.                                                                                                                                                                                                                                                                                                                     |
| v0.3.9p    | **Performance Optimization Pass**: (1) CraftLootHandler scanner dirty-player tracking — 0 scans when idle. (2) SkillCooldownManager tick-counter — O(1) per tick instead of O(N×M). (3) PlayerData.getCachedStats() remove redundant applyItemBonuses. (4) BossTier.VALUES cached static array — no array alloc per call. (5) SyncVitalsS2C lightweight packet (12 bytes vs ~1KB NBT). (6) CooldownSyncS2C batch mode — 1 packet join sync instead of 20. (7) CombatHandler MISS detection stores dimension key with UUID. (8) FloatingDamageRenderer O(1) swap-with-last removal. (9) LootGenerator bitmask instead of HashSet. BUILD SUCCESSFUL.                                                                                                                                                                                                                                                     |
| v0.3.7     | **Stamina Removal**: Removed entire stamina system. Deleted `stamina/` package (3 files) + `StaminaSyncS2C.java`. Stripped stamina from DerivedStats, StatCalculator, PlayerTickHandler, ModEvents, ClassManager, UseSkillC2S, GuiEventHandler, DebugCommand, RpgStatusOverlay, CharacterScreen, SkillHotbarOverlay, ModMessages. `PlayerData.currentStamina` + `BaseSkill.staminaCost` retained for NBT compat. BUILD SUCCESSFUL.                                                                                                                                                                                                                                                                                                                                                                                                        |
| v0.3.8     | **v0.3 Complete**: Chest loot via `ChestLootHandler`. Item name color. `/rpg salvage` command. `copyFrom()` HashMap→clear+putAll fix. FloatingDamage `depthMask` fix. BUILD SUCCESSFUL.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| v0.3.9     | **Crafting & Item Stat Fix**: `CraftLootHandler` thêm periodic inventory scanner (ServerTickEvent 1s) — safety net catch shift-click crafted items chưa có RPG tag. `StatCalculator.applyItemBonuses` thêm off-hand item (`getOffhandItem()`) — shield/totem stats giờ được tính. BUILD SUCCESSFUL.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
| v0.3.6     | **Stamina Resource Manager Refactor**: StaminaHandler is now a pure resource manager with unified API: `canConsume()`, `consumeStamina()`, `recoverStamina()`, `updateLastAction()`, `isExhausted()`, `getCurrentStamina()`, `setCurrentStamina()`. Costs moved to DerivedStats (`sprintStaminaCostPerSec`, `jumpStaminaCost`, `attackStaminaCost`) — no hardcoded values in logic. UseSkillC2S + GuiEventHandler use `canConsume()`/`consumeStamina()` instead of raw PlayerData access. Added `onLivingHurt` → `updateLastAction()` (damage resets regen delay). Removed redundant stamina clamp from PlayerTickHandler.equipmentChange. BUILD SUCCESSFUL.                                                                                                                                                        |
| v0.3.4     | **Performance Audit Fixes**: `DEBUG_STAMINA=false` in PlayerTickHandler + SyncPlayerDataS2C (removes 10+ LOGGER calls from hot path). CharacterScreen uses `getCachedStats()` instead of bypassing cache with `StatCalculator.calculate()` every frame. SkillCooldownManager.onServerTick uses iterator removal (zero allocations) instead of `new ArrayList` per tick. CombatHandler.onServerTick uses iterator removal + direct dimension lookup instead of `getAllLevels()` iteration. HealSkill uses `getCachedStats()`. ItemStatApplier defers `float[15]` allocation until after null check. RpgStatusOverlay + SkillHotbarOverlay cache capability lookups across frames. PlayerTickHandler `PlayerLoggedOutEvent` handler cleans up 8 static maps (memory leak fix). SkillHotbarOverlay.clientTick uses iterator removal. |
| v0.3.3     | **Performance Optimization**: Stat caching (DerivedStats cached in PlayerData, invalidated on equipment/class/stat change). PlayerTickHandler uses `getCachedStats()` instead of `StatCalculator.calculate()` every tick — eliminates ~400 DerivedStats allocations/sec with 20 players. `LivingEquipmentChangeEvent` triggers cache invalidation. CombatHandler MISS detection uses server tick counter instead of wall-clock `System.currentTimeMillis()`. ItemStatApplier: fixed double-counting main hand stats, added `markStatsDirty()` call. All stat mutation points (AddStatPoint, ClassManager, ChooseClass, ClassCommand) call `markStatsDirty()`. All callers use `getCachedStats()` API consistently.                                                                                                                 |
| v0.1→v0.2 | Gradle 8.9→8.5,`org.gradle.watch-fs=false`, PoseStack→GuiGraphics, `isMagic()`→`getMsgId()`, `mods.toml` hardcode + license. Class selection: responsive cards, death persistence.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| v0.2       | `ConcurrentHashMap.removeIf()+setValue()` → iterator (Microsoft JDK crash). 20 skills + class binding. Keybinds R/G/C/V. Genshin-style HUD. XP orb hook. Cleric class. `/class` command. ShieldBash self-damage fix. `!e.is(player)` all skills. Mana regen 1.0→2.0. Combat debug log.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
| v0.2.1     | Skill C key + HP bar fix: GuiEventHandler uses`KeyMapping.getKey().getValue()`. Vanilla `MAX_HEALTH` updated after class change in `ChooseClassC2S` + `ClassCommand`. HP sync on damage. CharacterScreen compact scrollable layout. Client-side unlock level check.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
| v0.2.2     | **XP & Skill Progression Overhaul**: 20 skill cooldowns rebalanced (Ult 60-120s→40-45s). Vanilla XP bar hidden. Skill Upgrade Lv1→5 (XP cost 100→1000, +25% DMG/level, -5% CD/level). CharacterScreen tabs [Character][Skills] + XP bar. Mana regen 2→3/s. `skillLevels` NBT. `SkillUpgradeC2S` packet.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| v0.2.3     | **Echoes of Aincrad refactor**: Xóa toàn bộ `skillPoints`, `SkillUpgradeC2S`. Thay bằng Rank system (E→D→C→B→A→S→SS) + Mastery (tăng khi dùng skill trúng mục tiêu). Rank up yêu cầu Level + Mastery. `PlayerData.skillMastery`/`skillRank` NBT. `RankUpC2S` packet. BaseSkill rank-based scaling.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
| v0.2.4     | **Floating Damage + Mob Health UI**: `FloatingDamageS2C` packet broadcast damage numbers từ server. `FloatingDamageRenderer` render số damage bay lên (trắng=thường, vàng=CRIT!, xanh=+heal, xanh dương=DODGE, xám=MISS). `MobHealthOverlay` HP bar + level trên đầu mob (tím=elite, cam=boss). `CombatHandler` gửi floating damage mỗi `LivingHurtEvent`.                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
| v0.2.5     | **HUD Polish**: Floating damage font 0.025→0.04 + `disableDepthTest` fix xuyên block. Mob HP bar hiển thị tất cả Enemy/Mob, không chỉ Elite/Boss. Skill HUD thêm rank badge, resource cost (MP/SP), countdown timer, lock Lv indicator. Performance: cap entries + snapshot pattern.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| v0.2.6     | **Mob Scaling + Final Damage**: `MobScaler` system: level = f(distance), HP scale `base×(1+lv×0.25)`, DMG bonus `lv×0.5`, DEF reduction, EXP scale. Elite ≥80HP (purple), Boss ≥200HP (orange). `CombatHandler` final formula: `(Raw+Stat)×ClassMult×Crit×Element×DefenseMult`. Class modifier: Warrior/Berserker +20% phys, Mage +25% mag, Assassin +15% all, Cleric +10% mag. MobHealthOverlay show Lv+HP cho mọi Enemy.                                                                                                                                                                                                                                                                                                                                                                                                    |
| v0.2.8     | **Regen fix + HEAL/DODGE**: Mana regen luôn active. Sync mỗi 0.5s. `inCombat` fix. HealSkill broadcast TYPE_HEAL. CombatHandler defense >95% → DODGE.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| v0.3.0     | **Loot System**: `ItemRarity` 6 tier, `LootGenerator` 15 stats + affix, `LootHandler` drops + tooltip, `ItemStatApplier` gear→stats, `StatCalculator` item bonus overload. Boss Rare+ & 2% Mythic. Glow outline (boss ≥200HP). MISS floating text. Stamina revert simple.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
| v0.3.1     | Bug fixes: Item bonus client-side, Stamina food-level block, Mana always regen, Death clamp.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| v0.3.2     | **Stamina Refactor**: Removed all food/hunger manipulation. True RPG stamina: sprint cost 0.5/tick, jump cost 5.0 (ground→air detection), regen 2.0+END×0.15/sec after 40-tick delay. Force sprint off at 0 stamina. Vanilla food bar restored. Debug logging.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |

---

## CURRENT PLAYABLE CHECKLIST

- [X] Build thành công (`BUILD SUCCESSFUL`)
- [X] 5 class: Warrior, Berserker, Assassin, Mage, Cleric
- [X] 20 skills (4 mỗi class), full particle + sound
- [X] Class selection screen responsive, 5 cards
- [X] Character screen (P): 8 stat buttons + 15 derived stats
- [X] Skill HUD bottom-right: [R][G][C][V] + cooldown timers
- [X] RPG Status bars top-left: HP/Mana, vanilla HP/Armor/XP hidden, vanilla FOOD visible
- [X] Combat: STR/INT damage bonus, defense reduction, crit
- [X] Regen: Mana 3+WIS×0.15/s (always), HP 0.5/s out of combat
- [X] XP: mob kill (maxHP×2), XP orb (PickupXp event), LUK bonus
- [X] Level up: statPoints+5 mỗi level. KHÔNG còn skillPoints.
- [X] Save/load: toàn bộ data + skillMastery/skillRank NBT, death persistence, reconnect persistence
- [X] `/class` command với tab-completion
- [X] Server authority: double-validate mana/cooldown/level trước skill
- [X] Client spam protection: check mana < cost + unlock level → action bar warning
- [X] No self-damage: `!e.is(player)` filter + nearest-entity fallback
- [X] Dedicated server safe: `Minecraft.getInstance()` only in gui/proxy
- [X] 8 network packets, đúng direction, `enqueueWork()` safety
- [X] Skill Rank system (E→D→C→B→A→S→SS), mastery gain on hit, rank up via `RankUpC2S`
- [X] CharacterScreen dual tab [Character][Skills] + XP progress bar + mastery/rank display
- [X] Skill scaling: +DMG/rank (100%→350%), -CD/rank (100%→60%)
- [X] Floating damage numbers (5 type: normal/CRIT/HEAL/DODGE/MISS) + `disableDepthTest`
- [X] Target HUD: raycast 30 block, anti-flicker, Level+Name+HP bar
- [X] MobScaler: level scaling theo distance, HP/DMG/DEF/EXP, Elite/Boss modifier
- [X] Final damage formula: `(Raw+Stat)×ClassMult×Crit×Element×DefenseMult`
- [X] ItemStatApplier: gear stats → thực tế ảnh hưởng PlayerData
- [X] Loot system: 6 rarity tier, 15 random stats, affix, boss Rare+, 2% Mythic, glow outline
- [X] Mana regen always, sync every 0.5s
- [X] 💀 Stamina: **REMOVED in v0.3.7**. HP + Mana are the only resources.
- [X] Chest loot: RPG stats trong chest tự nhiên (v0.3.8)
- [X] Item name màu theo rarity (v0.3.8)
- [X] `/rpg salvage` command (v0.3.8)
- [X] Performance: `copyFrom()` zero-allocation, `depthMask` fix (v0.3.8)

---

## TODO NEXT — v0.4 Vision System (theo roadmap)

1. Vision Item (Curios slot) — 7 elements: Pyro, Hydro, Electro, Cryo, Anemo, Geo, Dendro
2. Element system: FIRE > ICE > LIGHTNING > WATER > FIRE
3. Status effects: Burn, Freeze, Electrified, Soaked
4. Elemental Skills: 35 skills (7 elements × 5 classes)
5. Skill đổi hiệu ứng theo Vision
6. Vision Shard drop từ boss (rare)
7. Curios integration (trang bị Vision vào slot Curios)

### v0.3 Remaining (minor QoL, defer)

- [X] Chest loot integration
- [X] Item name màu trong hotbar
- [X] Salvage/Disenchant system
- [X] Sync optimization (copyFrom HashMap allocations)
- [X] FloatingDamage depth test fix

---

## SOURCE AUDIT (2026-06-26)

| Package         | Files        | Status                                                                                                                                                                                                                       |
| --------------- | ------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `core`        | 6            | PlayerData (skillMastery/skillRank NBT), StatCalculator (item bonus overload), DerivedStats, ModStats, PlayerCapability, ModCapabilities                                                                                     |
| `classes`     | 2            | ClassType (5 class + NONE), ClassManager                                                                                                                                                                                     |
| `combat`      | 3            | CombatHandler (formula+MISS), PlayerTickHandler (mana/HP regen+XP+sync), MobScaler (tier+distance scaling)                                                                                                                   |
| `skills`      | 23           | BaseSkill (rank E→SS scaling), SkillRegistry (20 skills + CLASS_SKILLS), SkillCooldownManager + 20 skill implementations                                                                                                    |
| `gui`         | 7            | CharacterScreen (tabs + XP bar), ClassSelectionScreen, FloatingDamageRenderer (5 types), GuiEventHandler (keybinds), RpgStatusOverlay (HP/Mana), SkillHotbarOverlay (rank+cost+cd), TargetHUD (raycast + LOS + anti-flicker) |
| `network`     | 7            | SyncPlayerData, ChooseClass, AddStat, OpenClassSelection, UseSkill (mastery+scaling), CooldownSync, RankUp, FloatingDamage (5 types)                                                                                         |
| `loot`        | 6            | ItemRarity (6 tier), LootGenerator (15 stats + affix), LootHandler (mob drops + tooltip), ChestLootHandler (chest loot), CraftLootHandler (crafting + scanner), ItemStatApplier (gear→stats)                              |
| `init`        | 3            | ModEvents (join/clone/mob spawn), ModKeybinds (P/R/G/C/V), ModMessages (7 packets)                                                                                                                                           |
| `command`     | 2            | ClassCommand (/class), DebugCommand                                                                                                                                                                                          |
| `proxy`       | 1            | ClientProxy                                                                                                                                                                                                                  |
| **TOTAL** | **58** | BUILD SUCCESSFUL, 0 errors                                                                                                                                                                                                   |

---

## ROADMAP MỚI (đã cập nhật `docs/39_VERSION_SCOPE_AND_ROADMAP.md`)

| Version        | Theme                                               | Status  |
| -------------- | --------------------------------------------------- | ------- |
| **v0.1** | Core Playable                                       | ✅ DONE |
| **v0.2** | Combat & Progression                                | ✅ DONE |
| **v0.3** | Loot System (Rarity + Affix + Random Stats)         | ✅ DONE |
| v0.4           | Vision System (7 elements × 5 classes = 35 skills) | 📋      |
| v0.5           | Artifact System (Genshin-style 5 slots)             | 📋      |
| v0.6           | Dungeon (Keys + Bosses with phases)                 | 📋      |
| v0.7           | Multiplayer (Party + Shared XP + Raid)              | 📋      |
| v1.0           | Official Release                                    | 📋      |

---

## FLOATING DAMAGE — COMPLETE

- [X] Font size 0.04 + `disableDepthTest()`
- [X] HEAL floating text: HealSkill gửi `FloatingDamageS2C` TYPE_HEAL
- [X] DODGE: defense >95% → "DODGE" xanh dương
- [X] MISS: `AttackEntityEvent` + `ServerTickEvent` timer → "MISS" xám
- [X] Glow outline: Boss (≥200HP) `setGlowingTag` → đã bỏ (wall-hack)

---

## KNOWN BUGS (audit 2026-06-26)

### REMAINING

*(none)*

### FIXED in v0.3.9

| # | Bug | Fix |
| - | --- | --- |
| 10 | ~~Craft (shift-click) tạo đồ vanilla~~ | `CraftLootHandler` thêm periodic inventory scanner (ServerTickEvent mỗi 1s) — safety net catch mọi RPG-type item chưa có `rpg_rarity` tag. |
| 11 | ~~Off-hand item bonus không được tính~~ | `StatCalculator.applyItemBonuses` thêm `sumItem(totals, player.getOffhandItem())` — shield/totem giờ được tính. |

### FIXED in v0.3.3

| # | Bug                                  | Fix                                                                                                                        |
| - | ------------------------------------ | -------------------------------------------------------------------------------------------------------------------------- |
| 9 | ~~MISS detection dùng wall-clock~~ | Replaced`System.currentTimeMillis()` with `serverTickCounter` in CombatHandler. Tick-based, immune to TPS fluctuation. |

### FIXED in v0.3.1

| # | Bug                                            | Fix                                                                                                             |
| - | ---------------------------------------------- | --------------------------------------------------------------------------------------------------------------- |
| 1 | ~~Item bonus không hiển thị trên client~~ | `StatCalculator.calculate(data)` giờ tự lấy `Minecraft.getInstance().player` để đọc item client-side |
| 4 | ~~Mana regen dừng trong combat~~             | Bỏ`!inCombat` check → mana luôn regen                                                                      |
| 7 | ~~CharScreen stat thiếu item bonus~~         | Đã fix cùng với#1                                                                                           |
| 8 | ~~Sau khi chết stamina/mana=0~~              | `onPlayerClone` clamp 30% max                                                                                 |

### FIXED in v0.3.2

| #   | Bug                                | Fix                                                                                                                                       |
| --- | ---------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------- |
| 2-3 | ~~Stamina dùng food level hack~~ | Xóa toàn bộ`setFoodLevel(3/20)`. True RPG stamina: sprint cost 0.5/tick, jump detect ground→air, force sprint off, regen delay 40t. |

---

## LAST COMPLETED TASK

"Performance Optimization Pass #2 (v0.3.9p2): 6 optimizations across 5 files. (1) TargetHUD BossTier array-index lookup instead of for-loop + entity name/tier caching (avoids NBT read + String allocation every 60fps frame). (2) SkillHotbarOverlay String.format → manual int+frac (zero allocation cooldown text). (3) TargetHUD entity name caching (getName().getString() only on target change). (4) CraftLootHandler dirty-player tracking (0 scans when idle — scanner only runs for players who crafted). (5) PlayerTickHandler getOrDefault → get+null check (avoids Float/Integer boxing). (6) RpgStatusOverlay + SkillHotbarOverlay cache invalidation on player change (prevents stale capability reference). BUILD SUCCESSFUL."
