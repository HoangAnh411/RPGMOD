# PHASE 1 - CORE RPG SYSTEM

## Mục tiêu

Xây dựng nền tảng RPG cho toàn bộ mod.

Mọi hệ thống phía sau:

* Class
* Skill
* Vision
* Elemental Reaction
* Weapon Mastery
* Epic Fight

đều phải sử dụng dữ liệu từ Core System.

---

# Package

com.rpgpack.core

---

# Files Required

ModStats.java

DerivedStats.java

StatCalculator.java

PlayerData.java

PlayerCapability.java

NetworkPackets.java

PlayerEvents.java

---

# ModStats

Enum chứa:

STR
VIT
END
AGI
DEX
INT
WIS
LUK

---

# PlayerData

Lưu dữ liệu:

Level

Experience

ExperienceToNextLevel

StatPoints

SkillPoints

CurrentClass

CurrentHP

CurrentMana

CurrentStamina

CurrentVision

STR
VIT
END
AGI
DEX
INT
WIS
LUK

---

# Level System

Level Max:

100

---

# EXP Formula

Level 1 -> 2

100 EXP

Level 2 -> 3

150 EXP

Level 3 -> 4

225 EXP

Công thức:

RequiredExp =
100 × (1.5^(Level-1))

---

# Level Up Rewards

Mỗi level:

+5 Stat Points

+1 Skill Point

Full HP

Full Mana

Full Stamina

---

# Derived Stats

MaxHP

MaxMana

MaxStamina

PhysicalDamage

MagicDamage

PhysicalDefense

MagicDefense

CritChance

CritDamage

AttackSpeed

MoveSpeed

CooldownReduction

ElementalBonus

ManaRegen

StaminaRegen

---

# Stat Formula

MaxHP

100 + (VIT × 10)

---

MaxMana

50 + (INT × 5)

---

MaxStamina

100 + (END × 8)

---

PhysicalDamage

WeaponDamage + (STR × 0.5)

---

MagicDamage

SkillBase + (INT × 0.8)

---

CritChance

DEX × 0.25%

---

CritDamage

150% + (LUK × 0.5%)

---

CooldownReduction

WIS × 0.3%

---

# Network

Client KHÔNG được phép:

* tự cộng stat
* tự tăng exp
* tự mở skill

Mọi thay đổi phải gửi packet về server.
