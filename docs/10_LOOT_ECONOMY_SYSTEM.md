# PHASE 10 - LOOT & ECONOMY SYSTEM

## Mục tiêu

Xây dựng hệ thống kinh tế MMORPG dài hạn.

Người chơi phải luôn có mục tiêu:

Farm
→ Nâng cấp
→ Dungeon
→ Boss
→ Endgame

---

# Package

com.rpgpack.economy

com.rpgpack.loot

---

# Files Required

CurrencyManager.java

LootTableManager.java

DropCalculator.java

GearGenerator.java

EnhancementManager.java

ReforgeManager.java

ShopManager.java

AuctionManager.java (Future)

---

# Currency Types

## Gold

Tiền tệ cơ bản.

Nguồn:

* Quái
* Quest
* Dungeon
* Boss

Dùng cho:

* Repair
* Shop
* Enhancement

---

## Dungeon Token

Nhận từ:

Dungeon Clear

Dùng để:

Đổi trang bị Tier cao.

---

## Boss Soul

Nhận từ:

Boss

Dùng để:

Craft Legendary Gear

Craft Vision Upgrade

---

## Vision Shard

Nhận từ:

Element Boss

Dùng để:

Nâng cấp Vision

---

# Item Rarity

COMMON

UNCOMMON

RARE

EPIC

LEGENDARY

MYTHIC

---

# Drop Rates

COMMON

60%

---

UNCOMMON

25%

---

RARE

10%

---

EPIC

4%

---

LEGENDARY

0.9%

---

MYTHIC

0.1%

---

# LUK Influence

Luck tăng:

Rare Drop

Legendary Drop

Boss Drop

Material Drop

---

Formula

FinalDropRate

=

BaseRate × (1 + LUK × 0.01)

---

# Equipment Slots

Weapon

Helmet

Chestplate

Leggings

Boots

Ring

Necklace

Vision

Artifact

---

# Gear Stats

Primary Stats

STR
VIT
END
AGI
DEX
INT
WIS
LUK

---

# Enhancement System

+1 → +15

---

Success Rates

+1 → +5

100%

---

+6 → +10

80%

---

+11 → +15

50%

---

# Enhancement Bonus

+1

+2% Item Stats

---

+15

+30% Item Stats

---

# Reforge System

Random lại dòng chỉ số.

Ví dụ:

STR +15

→

DEX +15

---

# Gear Score

Formula

GearScore

=

BaseStats

Enhancement

RarityBonus

---

# Market System

Future Update

Player Market

Auction House

Trade Window

Guild Storage

---

# Anti Inflation

Gold Sink

Bao gồm:

* Enhancement
* Repair
* Fast Travel
* Reforge
* Vision Upgrade

---

# Endgame Economy

Gold

↓

Dungeon Token

↓

Boss Soul

↓

Legendary Craft

↓

Mythic Craft
