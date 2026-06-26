# PHASE 21 - WEAPON ART SYSTEM

## Mục tiêu

Tái hiện hệ thống Sword Skills / Weapon Arts của SAO.

Weapon Arts là hệ thống riêng biệt với:

- Class
- Vision
- Skill Tree

Người chơi có thể sử dụng Weapon Arts dựa trên loại vũ khí đang trang bị.

---

# Design Philosophy

Class

↓

Role

(Tank / DPS / Mage)

---

Weapon Art

↓

Combat Style

---

Vision

↓

Element Modifier

---

Ví dụ:

Berserker
+
Katana
+
Lightning Vision

hoặc

Warrior
+
Spear
+
Fire Vision

hoặc

Mage
+
Staff
+
Ice Vision

---

# Package

com.rpgpack.weaponart

---

# Files Required

WeaponArt.java

WeaponArtRegistry.java

WeaponArtManager.java

WeaponArtCooldownManager.java

WeaponArtPacket.java

WeaponArtScreen.java

WeaponArtTree.java

WeaponArtMastery.java

---

# Weapon Categories

ONE_HANDED_SWORD

DUAL_BLADES

GREATSWORD

KATANA

SPEAR

DAGGER

BOW

STAFF

---

# Weapon Art Structure

artId

artName

weaponType

manaCost

staminaCost

cooldown

unlockLevel

masteryRequirement

damageScaling

animationId

---

# Damage Formula

Final Damage

=

Weapon Damage

Class Scaling

Weapon Art Scaling

Vision Modifier

Reaction Modifier

---

# Weapon Art Slots

Slot 1

Slot 2

Slot 3

Ultimate Slot

---

# Unlock Requirements

Weapon Mastery

Quest

Boss Drop

Skill Book

Trainer NPC

---

# One-Handed Sword

Role:

Balanced

---

## Sonic Leap

Dash tới mục tiêu.

Damage:

250%

Weapon Damage

Cooldown:

8s

---

## Horizontal Square

4-hit combo.

Damage:

4 x 90%

Cooldown:

12s

---

## Vorpal Strike

Single Target Burst.

Damage:

450%

Cooldown:

20s

---

# Dual Blades

Role:

Fast DPS

---

## Double Circular

AOE Spin Attack

Damage:

300%

Cooldown:

10s

---

## Starburst Stream

16-hit Combo

Damage:

16 x 70%

Cooldown:

60s

Ultimate

---

## Eclipse

27-hit Combo

Damage:

27 x 80%

Cooldown:

120s

Legendary Unlock

---

# Greatsword

Role:

Heavy Damage

---

## Earth Shatter

AOE Slam

Damage:

350%

Cooldown:

15s

---

## Titan Breaker

Boss Killer

Damage:

600%

Cooldown:

25s

---

# Katana

Role:

Precision

---

## Tsujikaze

Dash Slash

Damage:

280%

Cooldown:

8s

---

## Hiougi

Critical Strike

Damage:

500%

Crit Bonus:

+50%

Cooldown:

20s

---

## Sakura Storm

AOE Combo

Damage:

8 x 100%

Cooldown:

40s

---

# Spear

Role:

Reach

---

## Spiral Spear

Pierce Attack

Damage:

300%

Cooldown:

10s

---

## Dragon Fang

Line Attack

Damage:

450%

Cooldown:

18s

---

# Dagger

Role:

Assassination

---

## Shadow Fang

Backstab

Damage:

400%

Cooldown:

12s

---

## Phantom Assault

5-hit Combo

Damage:

5 x 90%

Cooldown:

20s

---

# Bow

Role:

Ranged DPS

---

## Multi Shot

5 Arrows

Cooldown:

10s

---

## Rain of Arrows

AOE

Cooldown:

20s

---

## Piercing Arrow

Ignore Armor

Cooldown:

25s

---

# Staff

Role:

Magic Focus

---

## Arcane Missile

Magic Projectile

Cooldown:

5s

---

## Elemental Burst

AOE Magic

Cooldown:

20s

---

## Mana Nova

Ultimate

Cooldown:

60s

---

# Weapon Art Mastery

Mỗi Weapon Type có cây Weapon Art riêng.

---

Mastery 10

Unlock Tier 1

---

Mastery 25

Unlock Tier 2

---

Mastery 50

Unlock Tier 3

---

Mastery 75

Unlock Tier 4

---

Mastery 100

Unlock Ultimate

---

# Vision Integration

Fire Vision

→ Burn

---

Water Vision

→ Wet

---

Ice Vision

→ Chilled

---

Lightning Vision

→ Electrified

---

Ví dụ

Vorpal Strike

Fire Vision

↓

Flame Vorpal Strike

---

Starburst Stream

Lightning Vision

↓

Thunderburst Stream

---

# Epic Fight Integration

Weapon Arts phải:

- sử dụng animation Epic Fight
- hỗ trợ combo chaining
- hỗ trợ hitbox riêng
- hỗ trợ cancel window

---

# Combo System

Light Attack

↓

Light Attack

↓

Weapon Art

↓

Dodge Cancel

↓

Weapon Art

---

# Ultimate Gauge

Tích từ:

- gây damage
- nhận damage
- perfect dodge
- boss fight

---

100%

↓

Ultimate Skill Available

---

# Future Expansion

Weapon Evolution

Legendary Weapon Arts

Boss Weapon Arts

Unique Weapon Arts

Hidden Weapon Arts
