# ROADMAP — RPG MODPACK

## Mục tiêu

Xây dựng MMORPG system cho Minecraft theo phong cách:
- Genshin Impact (Vision + Element + Artifact)
- Path of Exile / Diablo (Loot + Affix + Random Stats)
- Elden Ring (Combat feel)

---

# VERSION 0.1 — CORE PLAYABLE ✅ DONE

## Status: COMPLETE

### Core System
- [x] PlayerData (Level, EXP, 8 Stats, NBT)
- [x] Stat System (STR, VIT, END, AGI, DEX, INT, WIS, LUK)
- [x] Level Up System (+5 stat points/level)
- [x] StatCalculator (15 derived stats)
- [x] Mana regen (3.0 + WIS×0.15/s)
- [x] Stamina regen (1.0 + END×0.1/s)
- [x] HP regen (0.5/s out of combat)

### Class System
- [x] 5 classes: Warrior, Berserker, Assassin, Mage, Cleric
- [x] Class Selection Screen (responsive cards)
- [x] /class command

### Combat System
- [x] STR/INT damage bonus
- [x] Defense reduction formula
- [x] Crit system (chance + multiplier)
- [x] Stamina consumption (sprint, jump)

### Skill System
- [x] 20 skills (4 per class)
- [x] Skill HUD (R/G/C/V keys)
- [x] Cooldown system
- [x] Rank system (E→D→C→B→A→S→SS)
- [x] Mastery gain on skill use
- [x] Rank-based scaling (damage + cooldown)
- [x] Particle + sound effects
- [x] Unlock level check

### Network
- [x] 8 packets (SyncPlayerData, ChooseClass, AddStat, OpenClassSelection, UseSkill, CooldownSync, RankUp, FloatingDamage)

### GUI
- [x] Character Screen (P) with Character + Skills tabs
- [x] XP bar
- [x] RPG Status Bars (HP/Mana/Stamina)
- [x] Floating Damage Numbers
- [x] Mob Health Bars

### Persistence
- [x] NBT save/load (full PlayerData + skillMastery + skillRank)
- [x] Death persistence
- [x] Reconnect persistence

---

# VERSION 0.2 — COMBAT & PROGRESSION 🔧 IN PROGRESS

## Mục tiêu: Combat thực sự, không còn gần vanilla

### Damage System
- [x] Floating damage text (CRIT vàng, thường trắng)
- [x] Mob HP bar + level
- [x] Skill multiplier trong damage formula
- [x] Crit multiplier
- [ ] **Final Damage formula đầy đủ**:
  ```
  Final Damage = Base Damage × Skill Multiplier × Crit × Element Bonus × Defense Reduction × Class Modifier
  ```
- [ ] Defense Reduction áp dụng cho mob (không chỉ player)
- [ ] Class Modifier (Warrior +phys%, Mage +mag%, Assassin +crit%)

### Enemy Scaling
- [ ] **MobData system**:
  - Level scaling (mob level = f(distance from spawn, world difficulty))
  - HP scaling: base HP × (1 + level × 0.2)
  - Damage scaling: base DMG × (1 + level × 0.15)
  - Defense scaling: base DEF × (1 + level × 0.1)
  - EXP reward scaling: base EXP × (1 + level × 0.3)
- [ ] Elite modifier: ×3 HP, ×1.5 DMG, purple name
- [ ] Boss modifier: ×10 HP, ×2 DMG, orange name, phases

### Progression
- [x] EXP từ mob kill
- [x] Level up → stat points
- [x] Skill mastery → rank up
- [ ] **Quest EXP** (future)
- [ ] **Dungeon clear reward** (future)

### Expected Result
```
Warrior STR 20:
  Basic Attack = 10 × 1.2 (class) = 12
  Shield Bash  = 12 × 1.5 (skill) = 18
  Crit         = 18 × 1.5 = 27

Floating numbers: 12, 18, CRIT! 27
```

---

# VERSION 0.3 — LOOT SYSTEM

## Mục tiêu: Mod bắt đầu giống RPG

### Rarity System
- [ ] Common (White)
- [ ] Uncommon (Green)
- [ ] Rare (Blue)
- [ ] Epic (Purple)
- [ ] Legendary (Orange)
- [ ] Mythic (Red)

### Random Stats
- [ ] Item roll random stats khi loot
- [ ] Physical Damage bonus
- [ ] Magic Damage bonus
- [ ] STR/VIT/END/AGI/DEX/INT/WIS/LUK bonus
- [ ] Crit Chance % bonus
- [ ] Crit Damage % bonus
- [ ] Attack Speed % bonus
- [ ] HP/Mana/Stamina bonus

### Affix System
- [ ] of Fury (+Phys DMG)
- [ ] of Destruction (+Crit DMG)
- [ ] of Blood (+Life Steal)
- [ ] of Shadow (+Dodge)
- [ ] of Flame (+Fire DMG)
- [ ] of Frost (+Ice DMG)
- [ ] of Thunder (+Lightning DMG)
- [ ] of the Wise (+Mana Regen)

Ví dụ: `Iron Sword of Fury` → +12 Phys DMG, +5 STR, +3% Crit

### Loot Source
- [ ] Mob drop
- [ ] Boss drop (guaranteed Rare+)
- [ ] Chest loot
- [ ] Dungeon reward

---

# VERSION 0.4 — VISION SYSTEM

## Mục tiêu: Linh hồn của project

### 7 Elements
- [ ] Pyro (Fire)
- [ ] Hydro (Water)
- [ ] Electro (Lightning)
- [ ] Cryo (Ice)
- [ ] Anemo (Wind)
- [ ] Geo (Earth)
- [ ] Dendro (Nature)

### Vision Unlock
- [ ] Vision Shard item
- [ ] Curios slot integration
- [ ] Unlock cinematic/effect

### Elemental Skills (35 skills total)
| Class | Pyro | Hydro | Electro | Cryo | Anemo | Geo | Dendro |
|-------|------|-------|---------|------|-------|-----|--------|
| Warrior | Flame Slash | Aqua Guard | Thunder Cleave | Frost Strike | Gale Blade | Stone Crash | Vine Whip |
| Berserker | Burning Charge | Raging Wave | Storm Fury | Ice Rage | Cyclone | Quake | Wild Thorns |
| Assassin | Blaze Dash | Mist Step | Lightning Dash | Frost Shadow | Wind Step | Sand Strike | Poison Edge |
| Mage | Fireball | Water Orb | Chain Lightning | Ice Lance | Tornado | Rock Spike | Leech Seed |
| Cleric | Sacred Flame | Healing Rain | Shocking Prayer | Cold Blessing | Wind Grace | Earth Shield | Nature Mend |

### Elemental Effects
- [ ] Pyro → Burn (DoT)
- [ ] Hydro → Wet (tăng Electro dmg)
- [ ] Electro → Electrified (stun ngắn)
- [ ] Cryo → Freeze (đóng băng)
- [ ] Anemo → Swirl (spread element)
- [ ] Geo → Crystallize (shield)
- [ ] Dendro → Root (snare)

---

# VERSION 0.5 — ARTIFACT SYSTEM

## Mục tiêu: Endgame farm

### 5 Artifact Slots
- [ ] Flower (HP main stat)
- [ ] Feather (ATK main stat)
- [ ] Sands (ATK% / DEF% / Energy Recharge%)
- [ ] Goblet (Element% / Phys%)
- [ ] Circlet (Crit Rate% / Crit DMG% / Healing%)

### Stat Rolls
- [ ] Main stat (fixed per slot)
- [ ] Sub stats (4 random, roll mỗi 4 level)
- [ ] Rarity: 3★ → 4★ → 5★ (tăng sub stat count)

### Set Bonus
- [ ] 2-piece bonus
- [ ] 4-piece bonus
- [ ] Ví dụ: Crimson Witch 4p = +40% Pyro DMG

---

# VERSION 0.6 — DUNGEON

## Mục tiêu: PvE loop

### Dungeon Key
- [ ] Mob rơi Ancient Key
- [ ] Mở Abyss Dungeon

### Boss Design
- [ ] Pyro Regisvine
- [ ] Electro Hypostasis
- [ ] Geo Dragon
- [ ] Mỗi boss có: Phase 1 → Phase 2 → Enrage

### Reward
- [ ] Guaranteed Rare+ loot
- [ ] Vision Shard (rare drop)
- [ ] Artifact pieces
- [ ] Boss material (dùng craft/upgrade)

---

# VERSION 0.7 — MULTIPLAYER

## Mục tiêu: Chơi được với bạn bè

### Party System
- [ ] /party invite <player>
- [ ] /party kick <player>
- [ ] /party leave
- [ ] Party list UI

### Shared Systems
- [ ] Shared EXP trong 50m radius
- [ ] Party member HP bars
- [ ] Dungeon instance (per party)

### Raid
- [ ] 4-player raid
- [ ] 8-player boss raid
- [ ] Raid loot distribution

---

# VERSION 1.0 — OFFICIAL RELEASE

## Final Checklist
- [x] 5 class
- [x] 20+ skills (base, sẽ mở rộng theo Vision)
- [ ] Vision system (7 elements × 5 class = 35 skills)
- [ ] Loot rarity + affix
- [ ] Artifacts
- [ ] Bosses
- [ ] Dungeons
- [ ] Multiplayer party
- [ ] Endgame progression loop

## Output
✔ MMORPG Minecraft playable server
✔ 60 FPS stable, no crash
✔ Save/load OK, multiplayer sync OK

---

# DEVELOPMENT RULES

## RULE 1
Không thêm feature ngoài version hiện tại.

## RULE 2
Không code system chưa nằm trong roadmap.

## RULE 3
Luôn build playable trước, rồi mới mở rộng.

## RULE 4
Nếu hệ thống chưa test được → KHÔNG MOVE ON VERSION.

## TECH DEBT CONTROL
Mỗi version phải có:
- Playable build
- Test scenario
- Bug list
- Performance check
