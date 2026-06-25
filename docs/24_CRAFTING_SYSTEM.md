# PHASE 24 - CRAFTING SYSTEM ⭐⭐⭐

## Mục tiêu

Minecraft không có crafting hệ thống riêng sẽ rất phí.

Tạo hệ thống crafting RPG chuyên sâu bổ sung cho vanilla crafting.

---

# Package

com.rpgpack.crafting

---

# Files Required

CraftingManager.java

BlacksmithManager.java

AlchemistManager.java

VisionForgeManager.java

BossForgeManager.java

CraftingRecipe.java

CraftingScreen.java

CraftingStationBlock.java

---

# Crafting Stations

## Blacksmith

Chế tạo:

Vũ khí

Giáp

Dụng cụ

---

## Alchemist Table

Chế tạo:

Potion

Buff Food

Elixir

---

## Vision Forge

Chế tạo:

Vision Upgrade

Vision Evolution

Vision Material

---

## Boss Forge

Chế tạo:

Legendary Weapon

Mythic Weapon

Boss Gear

---

# Blacksmith System

## Weapon Craft

### Recipe Format

Base Weapon (Iron Sword/Greatsword/etc.)

+ Material (tùy rarity)

+ Gold Cost

= Crafted Weapon

### Materials by Rarity

UNCOMMON: Iron Ingot × 10 + Gold × 100

RARE: Diamond × 5 + Gold × 500

EPIC: Enchanted Metal × 3 + Gold × 2000

LEGENDARY: Boss Soul × 1 + Gold × 10000

MYTHIC: Mythic Essence × 1 + Gold × 50000

## Armor Craft

Tương tự Weapon nhưng dùng Armor Material.

---

# Alchemist System

## Potion Types

Healing Potion

Hồi HP tức thì.

HP Restore: 20% Max HP

Cooldown: 60 giây

---

Mana Potion

Hồi Mana tức thì.

Mana Restore: 30% Max Mana

Cooldown: 60 giây

---

Stamina Potion

Hồi Stamina tức thì.

Stamina Restore: 50% Max Stamina

Cooldown: 90 giây

---

Buff Potion

Strength Potion: +20% Physical Damage (5 phút)

Defense Potion: +20% Defense (5 phút)

Speed Potion: +20% Move Speed (5 phút)

Crit Potion: +10% Crit Chance (5 phút)

## Potion Recipe

Herb × 3 + Water Bottle + Gold × 50

## Buff Food

Roasted Meat: +10 STR (30 phút)

Herb Salad: +10 INT (30 phút)

Energy Drink: +10 END (30 phút)

Sweet Cake: +10 LUK (30 phút)

## Food Recipe

Ingredient × 3 + Spice × 1 + Gold × 30

---

# Material Gathering

## Herbs

Fire Herb → Fire-related craft

Ice Herb → Ice-related craft

Thunder Herb → Lightning-related craft

Water Herb → Water-related craft

## Ores

Magisteel → Magic equipment

Titansteel → Heavy equipment

Shadowsteel → Assassin equipment

## Boss Materials

Boss Soul → Legendary craft

Boss Core → Vision upgrade

Boss Scale → Armor craft

---

# Vision Forge

## Vision Upgrade

Vision + Vision Shard × 5 + Gold × 1000

→ Vision Level +1

## Vision Evolution

Vision Level 10 + Evolution Stone × 1 + Boss Soul × 3 + Gold × 50000

→ Vision Rank Up (new passive, new effect)

## Evolution Stones

Fire Stone → Fire Vision

Ice Stone → Ice Vision

Thunder Stone → Lightning Vision

Ocean Stone → Water Vision

---

# Boss Forge

## Legendary Weapon

Base Weapon + Boss Soul × 5 + Legendary Essence × 1 + Gold × 100000

## Mythic Weapon

Legendary Weapon + Mythic Essence × 1 + Boss Core × 3 + Gold × 500000

## Boss Armor

Base Armor + Boss Scale × 10 + Gold × 50000

---

# Crafting Level System

## Blacksmith Level

1 → 100

Mỗi lần craft tăng EXP.

Level cao:

Tăng tỉ lệ thành công

Giảm material cost

Mở recipe mới

## Alchemist Level

1 → 100

Level cao:

Tăng potion duration

Tăng potion strength

Mở recipe mới

---

# Crafting UI

## Blacksmith Screen

Recipe List (trái)

Material Preview (phải)

Success Chance

Gold Cost

Craft Button

## Alchemist Screen

Recipe Grid

Ingredient Slots

Result Preview

Brew Button

---

# Future Expansion

Enchanting (khác vanilla enchant)

Rune System

Soul Binding

Artifact Crafting

Dye System
