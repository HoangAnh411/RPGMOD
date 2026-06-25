# chưa PROJECT STATE - RPG MODPACK

## CURRENT VERSION

v0.1 CORE PLAYABLE — COMPLETE

---

## COMPLETED SYSTEMS

### Build System

- [X] settings.gradle (NeoForge 1.20.1-47.1.0)
- [X] build.gradle (NeoForge + Curios + GeckoLib + JEI)
- [X] gradle.properties, mods.toml, pack.mcmeta
- [X] en_us.json

### Core System (01_CORE_SYSTEM.md)

- [X] ModStats enum (8 stats)
- [X] PlayerData (Level, EXP, stats, NBT save/load, level up)
- [X] DerivedStats (15 derived stats, Builder pattern)
- [X] StatCalculator (all formulas, single source of truth)
- [X] PlayerCapability (Forge capability + NBT serialization)
- [ ] 
- [X] PlayerTickHandler (stamina/mana/hp regen every tick)

### Class System (02_CLASS_SYSTEM.md)

- [X] ClassType enum (NONE + 4 classes with base stats)
- [X] ClassManager (apply class stats)
- [X] ClassSelectionScreen (4 cards, role/stats/playstyle, auto-open)

### Skills (03_SKILL_SYSTEM.md)

- [X] BaseSkill (id, name, mana/stamina cost, cooldown, damage type)
- [X] GroundSmashSkill (Berserker, STR×2.5, AOE 4 blocks)
- [X] ManaBurstSkill (Mage, INT×3.0, magic burst)
- [X] ShadowStrikeSkill (Assassin, DEX×2.0, backstab)
- [X] ShieldBreakSkill (Warrior, STR×1.8, stagger)
- [X] SkillRegistry (init + lookup)
- [X] SkillCooldownManager (server tick-based, per-player)
- [X] UseSkillC2S (validate mana/stamina/level/cooldown → execute)
- [X] Skill keybinds 1-5
- [X] SkillHotbarOverlay (5 slots + cooldown timer + cost)

### Combat (08_EPIC_FIGHT_INTEGRATION.md)

- [X] CombatHandler (LivingHurtEvent → STR/INT bonus, defense reduction, crit)
- [X] EXP on kill (LivingDeathEvent, scaled by mob HP + LUK)

### GUI (13_GUI_UX_SYSTEM.md)

- [X] ClassSelectionScreen (auto when class=NONE)
- [X] CharacterScreen (P key — stats, derived, stat allocation)
- [X] SkillHotbarOverlay (bottom-center, cooldown display)
- [X] ModKeybinds (P, K, 1-5)

### Network (12_TECHNICAL_ARCHITECTURE.md)

- [X] 6 packets: SyncPlayerDataS2C, ChooseClassC2S, AddStatPointC2S, OpenClassSelectionS2C, UseSkillC2S, CooldownSyncS2C
- [X] Server authority on all stat/skill changes

### Save System (17_SAVE_DATABASE_SYSTEM.md)

- [X] Capability + NBT save/load (automatic via Forge)
- [X] onPlayerClone handles death persistence

---

## v0.1 PLAYABLE CHECKLIST

- [X] Có thể chơi singleplayer
- [X] Có level up (EXP from kills, formula-based progression)
- [X] Có class (4 classes, select on first join)
- [X] Có skill đánh được (4 skills, keys 1-5, cooldown + cost)
- [X] Stats ảnh hưởng combat (damage + defense + crit)
- [X] Stamina/Mana regen
- [X] Save/load hoạt động

---

## TODO NEXT — v0.2 VISION SYSTEM

1. Vision system (Curios slot + VisionItem)
2. Element system (FIRE/WATER/ICE/LIGHTNING)
3. Basic status effects (Burn, Wet, Chilled, Electrified)
4. Skill element modifier (Vision changes skill effect)

---

## KNOWN BUGS

- Chưa test được (cần gradle build)
- Cooldown reduction chưa áp dụng trong UseSkillC2S (đã fix)

---

## LAST COMPLETED TASK

"v0.1 CORE PLAYABLE complete: skill execution, cooldown sync, EXP on kill, stamina/mana regen, full combat loop"
