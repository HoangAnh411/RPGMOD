# PHASE 23 - EQUIPMENT SYSTEM ⭐⭐⭐

## Mục tiêu

Hiện tại đã có Loot System (doc 10) nhưng chưa có thiết kế Equipment hoàn chỉnh.

File này định nghĩa toàn bộ hệ thống trang bị.

---

# Package

com.rpgpack.equipment

---

# Files Required

EquipmentData.java

EquipmentManager.java

EquipmentGenerator.java

AffixManager.java

AffixPool.java

SetBonusManager.java

EquipmentScreen.java

---

# Equipment Slots

WEAPON

HELMET

CHESTPLATE

LEGGINGS

BOOTS

RING_1

RING_2

NECKLACE

ARTIFACT

---

# Equipment Rarity

COMMON — Trắng

UNCOMMON — Xanh lá

RARE — Xanh dương

EPIC — Tím

LEGENDARY — Cam

MYTHIC — Đỏ

---

# Weapon Types

SWORD

One-hand

Balanced

Scaling: STR 60% / DEX 40%

---

GREATSWORD

Two-hand

Heavy

Scaling: STR 100%

---

KATANA

One-hand

Speed

Scaling: DEX 70% / AGI 30%

---

DAGGER

One-hand

Crit

Scaling: DEX 80% / AGI 20%

---

SPEAR

Two-hand

Range

Scaling: STR 40% / DEX 60%

---

BOW

Two-hand

Ranged

Scaling: DEX 100%

---

STAFF

Two-hand

Magic

Scaling: INT 100%

---

# Armor Types

## Light Armor

Bonus: Move Speed, Dodge iFrame

Penalty: Low Defense

Class: Assassin, Mage

## Medium Armor

Bonus: Balanced

Penalty: None

Class: Flexible

## Heavy Armor

Bonus: High Defense, Poise

Penalty: -10% Move Speed, -5% Dodge Distance

Class: Warrior, Berserker

---

# Base Equipment Stats

Mỗi món trang bị có:

Primary Stat (1 dòng chính)

Secondary Stats (1-3 dòng phụ tùy rarity)

## Primary Stats by Slot

Weapon: Damage

Helmet: VIT

Chestplate: VIT + END

Leggings: END

Boots: AGI

Ring: Random Stat

Necklace: Random Stat

Artifact: Special Effect

---

# Affix System

Mỗi trang bị RARE trở lên có 1-3 Affix.

## Affix Pool

### Offensive

Flaming: +10% Fire Damage

Freezing: +10% Ice Damage

Thundering: +10% Lightning Damage

Tidal: +10% Water Damage

Sharp: +5% Physical Damage

Arcane: +15 INT

Brutal: +15 STR

Swift: +5% Attack Speed

Deadly: +5% Crit Chance

Lucky: +10 LUK

### Defensive

Sturdy: +15 VIT

Enduring: +15 END

Agile: +10 AGI

Wise: +15 WIS

Dextrous: +15 DEX

Guardian: +5% Defense

Vital: +5% Max HP

### Utility

ManaFlux: +5 Mana Regen

StaminaFlux: +5 Stamina Regen

Haste: +3% Cooldown Reduction

Traveler: +5% Move Speed

Greedy: +10% Loot Rate

---

# Set Bonus System

## Cơ chế

Trang bị cùng Set khi mặc đủ số món sẽ kích hoạt bonus.

## Example Sets

### Dragon Set (Fire)

2 món: +10% Fire Damage

4 món: +20% Burn Damage

6 món: Inferno Burst (Skill)

### Frost Set (Ice)

2 món: +10% Ice Damage

4 món: +1s Freeze Duration

6 món: Frost Nova (Skill)

### Storm Set (Lightning)

2 món: +5% Attack Speed

4 món: +10% Move Speed

6 món: Chain Lightning (Skill)

### Ocean Set (Water)

2 món: +10 Mana Regen

4 món: +20% Healing

6 món: Healing Rain (Skill)

### Shadow Set

2 món: +10% Crit Damage

4 món: +5% Crit Chance

6 món: Shadow Step (Skill)

### Titan Set

2 món: +10% Defense

4 món: +20% Max HP

6 món: Unbreakable (Passive)

---

# Equipment Generation

## Drop Generation

Mob Drop → Roll Rarity → Roll Affix → Roll Stats

## Stat Roll Range

COMMON: 50% - 70%

UNCOMMON: 60% - 80%

RARE: 70% - 90%

EPIC: 80% - 100%

LEGENDARY: 90% - 110%

MYTHIC: 100% - 130%

## LUK Bonus

LUK tăng cơ hội roll stat cao.

---

# Equipment Level Requirement

Level yêu cầu tăng theo rarity:

COMMON: Any Level

UNCOMMON: Level 10+

RARE: Level 25+

EPIC: Level 50+

LEGENDARY: Level 75+

MYTHIC: Level 100

---

# Equipment Screen

Hiển thị:

Player Model + Equipment

Current Stats

Set Bonus Active

Gear Score

Compare Mode (hover item)

---

# Gear Score Formula

GearScore =

BaseStats × RarityMultiplier × EnhancementLevel

RarityMultiplier:

COMMON: 1.0

UNCOMMON: 1.2

RARE: 1.5

EPIC: 2.0

LEGENDARY: 3.0

MYTHIC: 5.0

---

# Future Expansion

Mythic Affix (unique effect)

Awakening System

Equipment Socket/Gem

Transmogrification

Equipment Link (shared account)
