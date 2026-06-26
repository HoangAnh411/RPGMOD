# PHASE 28 - TITLE & ACHIEVEMENT SYSTEM ⭐⭐

## Mục tiêu

MMORPG rất cần hệ thống danh hiệu và thành tựu để tạo động lực dài hạn cho người chơi.

Title hiển thị trước tên, Achievement là cột mốc vĩnh viễn.

---

# Package

com.rpgpack.achievement

---

# Files Required

AchievementData.java

AchievementManager.java

TitleManager.java

AchievementRegistry.java

AchievementScreen.java

TitleScreen.java

AchievementNotification.java

---

# Achievement Categories

## Combat

Monster Slayer I → X

Boss Hunter I → X

Raid Conqueror I → X

Dungeon Clearer I → X

## Exploration

Floor Explorer I → X

Map Completion I → X

Hidden Area Discoverer

World Traveler

## Collection

Weapon Collector I → V

Armor Collector I → V

Pet Collector I → V

Title Collector

## Crafting

Master Blacksmith I → V

Master Alchemist I → V

Master Chef I → V

## Social

Guild Member I → V

Party Player I → V

Trade Master I → V

## Special

First Death

Floor 100 Clear

Legendary Drop

Mythic Drop

Perfect Dodge 1000 times

---

# Achievement Tiers

Mỗi achievement có tiers:

I → 10 lần

II → 50 lần

III → 100 lần

IV → 500 lần

V → 1000 lần

X → 10000 lần (Legendary)

---

# Achievement Rewards

EXP

Gold

Title Unlock

Stat Points (rare)

Skill Points (rare)

Cosmetic (very rare)

---

# Title System

## Title Display

[Title] PlayerName

Hiển thị trong:

Chat

Nameplate

Character Screen

Party UI

## Title Categories

### Combat Titles (có stat bonus)

Goblin Slayer

Kill 100 Goblins → +1 STR

Dragon Hunter

Kill 100 Dragons → +5% Boss Damage

Undead Bane

Kill 1000 Undead → +3% Damage to Undead

Boss Executioner

Kill 50 Bosses → +3% Execution Damage

Raid Champion

Clear 10 Raids → +2% All Stats

### Progression Titles (cosmetic)

Floor Conqueror

Clear Floor → [Floor X Conqueror]

Aincrad Veteran

Reach Floor 50

Aincrad Hero

Reach Floor 100

### Mastery Titles

Sword Saint

Sword Mastery 100

Bow Master

Bow Mastery 100

Archmage

Staff Mastery 100

Shadow Lord

Dagger Mastery 100

### Special Titles (rare)

The Unkillable

Complete dungeon without taking damage

Speed Demon

Kill boss within 30 seconds

Lone Wolf

Solo a Raid Boss

Deathless

Reach Level 100 without dying

Mythic Forger

Craft a Mythic item

Vision God

Ascend Vision to Divine

### Event Titles (limited time)

Season X Champion

Event participation reward

World Savior

World Event completion

---

# Title Stat Bonus Rules

Chỉ 1 title active tại 1 thời điểm (hiển thị).

Stat bonus từ title đã unlock luôn active (không cần đeo).

Stat bonus từ title stack với nhau.

Max stat bonus từ title: +10 All Stats, +10% Boss Damage.

---

# Achievement Points

Mỗi achievement cho điểm:

Tier I: 10 points

Tier V: 50 points

Tier X: 100 points

Hidden Achievement: 30 points

## Achievement Rank

Dựa trên tổng điểm:

Beginner: 0

Adventurer: 100

Veteran: 500

Elite: 1000

Master: 2500

Grandmaster: 5000

Legend: 10000

---

# Achievement Rewards by Rank

Adventurer: +1 Stat Point

Veteran: +1 Skill Point

Elite: Cosmetics - Adventurer Cape

Master: +2 Stat Points

Grandmaster: Title - Grandmaster

Legend: Mythic Pet Egg

---

# Achievement UI

Achievement Screen:

Category Tabs

Progress Bar mỗi achievement

Completed glow effect

Claim Reward button

Title Selection Screen:

Owned Titles list

Preview on character

Active title highlight

---

# Achievement Notifications

Toast notification khi hoàn thành:

Icon + "Achievement Unlocked: [Name]"

Sound effect

Chat message (cấu hình được)

---

# Server-wide Broadcast

Legendary Achievement (Tier X):

Broadcast toàn server

Special visual effect

Hall of Fame entry

---

# Future Expansion

Daily Achievement

Weekly Achievement

Season Achievement

Guild Achievement

Hidden Achievement Hunt
