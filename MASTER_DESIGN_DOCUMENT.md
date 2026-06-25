# RPGPACK - ECHOES OF AINCRAD RPG SYSTEM

## Phiên bản

v1.0

## Minecraft

Minecraft 1.20.1

## Loader

NeoForge / Forge

## Dependencies

* Curios API
* Epic Fight
* GeckoLib (khuyến nghị)
* Jade (optional)
* JEI (optional)

---

# MỤC TIÊU DỰ ÁN

Xây dựng hệ thống RPG hoàn chỉnh lấy cảm hứng từ:

* Sword Art Online: Echoes of Aincrad
* Elden Ring
* Genshin Impact

Hệ thống phải hỗ trợ:

* Character Classes
* RPG Stats
* Leveling
* Skill Tree
* Vision Element System
* Elemental Reactions
* Epic Fight Integration
* Weapon Mastery
* Dungeon/Boss Scaling

---

# PACKAGE STRUCTURE

src/main/java/com/rpgpack

core/
classes/
skills/
vision/
effects/
progression/
network/
gui/
init/

---

# PHASE 1 — CORE RPG SYSTEM

## ModStats.java

Hệ thống sử dụng 8 chỉ số chính.

STR = Strength
VIT = Vitality
END = Endurance
AGI = Agility
DEX = Dexterity
INT = Intelligence
WIS = Wisdom
LUK = Luck

---

## Ý nghĩa chỉ số

### STR

Tăng:

* Physical Damage
* Heavy Weapon Damage
* Block Break

Công thức:

PhysicalDamage += STR × 0.5

---

### VIT

Tăng:

* Max HP
* Physical Defense
* Bleed Resistance

Công thức:

MaxHP = 100 + (VIT × 10)

---

### END

Tăng:

* Max Stamina
* Stamina Regen
* Equip Load

Công thức:

MaxStamina = 100 + (END × 8)

---

### AGI

Tăng:

* Attack Speed
* Movement Speed
* Dodge Distance
* Dodge IFrame

---

### DEX

Tăng:

* Critical Chance
* Accuracy
* Bow Damage
* Backstab Damage

Công thức:

CritChance = DEX × 0.25%

---

### INT

Tăng:

* Magic Damage
* Mana
* Elemental Damage

Công thức:

Mana = 50 + (INT × 5)

---

### WIS

Tăng:

* Mana Regen
* Cooldown Reduction
* Healing Power
* Buff Duration

Công thức:

CooldownReduction = WIS × 0.3%

---

### LUK

Tăng:

* Loot Rate
* Rare Drop Rate
* Critical Damage
* Status Proc Rate

Công thức:

CritDamage = 150% + (LUK × 0.5%)

---

## PlayerData

PlayerData phải lưu:

Level
Experience
ExperienceToNextLevel

StatPoints
SkillPoints

CurrentHP
CurrentMana
CurrentStamina

SelectedClass

STR
VIT
END
AGI
DEX
INT
WIS
LUK

UnlockedSkills

WeaponMasteryLevels

CurrentVisionElement

---

## DerivedStats

Không lưu NBT.

Tính động.

MaxHealth

MaxMana

MaxStamina

PhysicalDamage

MagicDamage

PhysicalDefense

MagicDefense

CritChance

CritDamage

AttackSpeed

MovementSpeed

CooldownReduction

ElementalBonus

ManaRegen

StaminaRegen

---

## StatCalculator

Toàn bộ công thức đặt tại đây.

Không được hardcode ở GUI hoặc Skill.

---

## Network Packets

RequestPlayerDataC2S

SyncPlayerDataS2C

AddStatPointC2S

ChooseClassC2S

UseSkillC2S

UnlockSkillC2S

---

# PHASE 2 — CLASS SYSTEM

## Available Classes

NONE

WARRIOR

BERSERKER

ASSASSIN

MAGE

---

## Warrior

STR 10
VIT 8
END 8
AGI 5
DEX 5
INT 2
WIS 2
LUK 2

Role:

Tank / Bruiser

---

## Berserker

STR 12
VIT 7
END 9
AGI 4
DEX 4
INT 1
WIS 1
LUK 2

Role:

Heavy DPS

---

## Assassin

STR 5
VIT 4
END 5
AGI 10
DEX 12
INT 3
WIS 2
LUK 4

Role:

Burst DPS

---

## Mage

STR 1
VIT 3
END 4
AGI 4
DEX 3
INT 12
WIS 10
LUK 3

Role:

Caster

---

## Class Selection

Người chơi lần đầu đăng nhập:

* Tự động mở GUI chọn Class

Sau khi chọn:

* Gán chỉ số khởi đầu
* Đồng bộ dữ liệu
* Khóa GUI

---

# PHASE 3 — SKILL SYSTEM

## BaseSkill

Thông tin bắt buộc:

skillId

skillName

manaCost

staminaCost

cooldownTicks

damageType

scalingStats

---

## Damage Types

PHYSICAL

MAGIC

FIRE

WATER

ICE

LIGHTNING

---

## Berserker Skill

Ground Smash

Scale:

Damage =
BaseDamage +
(STR × 2.5)

Có thể Crit.

---

## Mage Skill

Mana Burst

Scale:

Damage =
BaseDamage +
(INT × 3.0)

Cooldown giảm bởi WIS.

---

## Assassin Skill

Shadow Strike

Scale:

Damage =
BaseDamage +
(DEX × 2.0)

Backstab Bonus.

---

## Warrior Skill

Shield Break

Scale:

Damage =
BaseDamage +
(STR × 1.8)

Tăng Stagger.

---

# PHASE 4 — VISION SYSTEM

## Elements

NONE

FIRE

WATER

ICE

LIGHTNING

---

## Vision Item

Trang bị thông qua Curios.

Mỗi người chơi:

Chỉ được đeo 1 Vision.

---

## FIRE

Damage Bonus:

+15%

Scaling:

INT 70%
WIS 30%

---

## WATER

Mana Regen Bonus

Scaling:

INT 50%
WIS 50%

---

## ICE

Freeze Duration Bonus

Scaling:

INT 60%
WIS 40%

---

## LIGHTNING

Attack Speed Bonus

Scaling:

AGI 60%
INT 40%

---

# PHASE 5 — SKILL EVALUATOR

Công thức:

# Final Skill

Base Skill
+
Vision Modifier
+
Player Stats

---

Ví dụ

Ground Smash

Fire Vision

=

Flame Ground Smash

---

Ground Smash

Lightning Vision

=

Thunder Ground Smash

---

# PHASE 6 — ELEMENTAL REACTIONS

## Status Effects

Wet

Chilled

Burned

Electrified

Frozen

---

## Reaction Matrix

Wet + Ice

=

Frozen

---

Wet + Lightning

=

Electro Charged

---

Wet + Fire

=

Vaporize

---

Burned + Lightning

=

Overload

---

# Scaling

Frozen Duration

3s + (WIS / 20)

---

Electro Charged Damage

5 + (INT × 0.5)

---

Vaporize Multiplier

1.5 + (INT / 100)

---

# PHASE 7 — WEAPON MASTERY

Weapon Types

Sword

GreatSword

Dagger

Bow

Staff

---

Mastery Level

1 → 100

---

Bonus

Damage

Attack Speed

Skill Unlocks

Special Moves

---

# PHASE 8 — SKILL TREE

Mỗi Class có cây kỹ năng riêng.

## Berserker

Ground Smash

├─ Shockwave

├─ Flame Smash

└─ Titan Crush

---

## Mage

Mana Burst

├─ Fire Burst

├─ Ice Burst

└─ Arc Lightning

---

## Assassin

Shadow Strike

├─ Poison Edge

├─ Phantom Dash

└─ Death Mark

---

## Warrior

Shield Break

├─ Fortress Stance

├─ Shield Charge

└─ Guardian Roar

---

# PHASE 9 — EPIC FIGHT INTEGRATION

Stat ảnh hưởng trực tiếp combat.

AGI

→ Attack Speed

→ Dodge Distance

---

END

→ Stamina

---

STR

→ Heavy Weapon Damage

---

DEX

→ Critical Rate

---

# PHASE 10 — ENDGAME

Dungeon Scaling

Boss Scaling

Elite Mobs

Raid Bosses

World Events

Legendary Vision

Unique Weapons

Mythic Equipment

Level Cap 100

Mastery Cap 100

Skill Tree Completion

Achievement System
