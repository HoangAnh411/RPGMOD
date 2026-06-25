# PHASE 39 - VERSION SCOPE & ROADMAP

## Mục tiêu

Khóa phạm vi phát triển để tránh:

- Feature creep (thêm quá nhiều thứ)
- Không build được bản playable
- Code rối không test được

---

# VERSION 0.1 - CORE PLAYABLE (BẮT BUỘC)

## Mục tiêu

Chạy được game RPG cơ bản trong Minecraft.

---

## Features

### Core System

- PlayerData (Level, EXP, Stats)
- Stat System (STR, VIT, AGI, DEX, INT, END, WIS, LUK)
- Level Up System

---

### Class System

- Choose Class GUI
- Warrior
- Berserker
- Mage
- Assassin

---

### Basic Combat

- Damage scaling theo STR / INT
- Stamina system cơ bản
- Simple cooldown skill

---

### Skill System (BASIC)

- Ground Smash
- Mana Burst

---

### Save System

- Capability + NBT save/load

---

## OUTPUT

✔ Có thể chơi singleplayer
✔ Có level up
✔ Có class
✔ Có skill đánh được

---

# VERSION 0.2 - VISION SYSTEM

## Features

- Vision Item (Curios)
- Element system
- FIRE / WATER / ICE / LIGHTNING

---

## Basic Effects

- Burn
- Wet
- Chilled
- Electrified

---

## Skill Integration

- Skill đổi hiệu ứng theo Vision

---

## OUTPUT

✔ Skill có element
✔ Có trạng thái cơ bản

---

# VERSION 0.3 - ELEMENTAL REACTION

## Features

- Reaction System
- Freeze
- Vaporize
- Overload
- Electro Charge

---

## OUTPUT

✔ Combat có combo element
✔ Có reaction damage

---

# VERSION 0.4 - WEAPON MASTER + WEAPON ART

## Features

- Weapon Mastery system
- Weapon Art system (SAO style)
- Combo system

---

## OUTPUT

✔ Combat có style
✔ Không chỉ spam skill

---

# VERSION 0.5 - DUNGEON SYSTEM

## Features

- Dungeon Tier 1–3
- Basic Boss
- Loot table

---

## OUTPUT

✔ Có PvE loop

---

# VERSION 0.6 - ECONOMY + GEAR

## Features

- Gold
- Loot rarity
- Gear enhancement
- Reforge

---

## OUTPUT

✔ Có progression loop

---

# VERSION 0.7 - SKILL TREE

## Features

- Skill tree per class
- Unlock system
- Branching builds

---

## OUTPUT

✔ Build diversity

---

# VERSION 0.8 - EPIC FIGHT INTEGRATION

## Features

- Stamina rework
- Dodge / parry
- Poise system
- Hyper armor

---

## OUTPUT

✔ Combat feel như Elden Ring

---

# VERSION 0.9 - MULTIPLAYER BASE

## Features

- Packet sync hoàn chỉnh
- Anti cheat basic
- Server validation

---

## OUTPUT

✔ Multiplayer playable

---

# VERSION 1.0 - OFFICIAL RELEASE

## Features

- Full dungeon system
- Boss system
- Vision full
- Reaction full
- Weapon art full
- Skill tree full

---

## OUTPUT

✔ MMORPG Minecraft playable server

---

# DEVELOPMENT RULES

## RULE 1

Không thêm feature ngoài version hiện tại.

---

## RULE 2

Không code system chưa nằm trong roadmap.

---

## RULE 3

Luôn build playable trước, rồi mới mở rộng.

---

## RULE 4

Nếu hệ thống chưa test được → KHÔNG MOVE ON VERSION.

---

# TECH DEBT CONTROL

Mỗi version phải có:

- Playable build
- Test scenario
- Bug list
- Performance check

---

# SUCCESS METRICS

Version 0.1:

- 60 FPS stable
- No crash
- Save/load OK

Version 0.3:

- Element reaction working

Version 0.5:

- Dungeon playable

Version 1.0:

- Multiplayer stable 10–50 players
