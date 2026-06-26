# PHASE 38 - CONTENT EXPANSION GUIDE ⭐⭐⭐

## Mục tiêu

Dành cho tương lai khi mở rộng game.

Mỗi hệ thống mới phải tuân theo pipeline chuẩn để không bỏ sót.

---

# Khi thêm Class mới

## Pipeline

Class
↓
Base Stats + Role
↓
Skill Tree (4 nhánh)
↓
Weapon Compatibility
↓
Vision Compatibility
↓
Questline (unlock quest)
↓
Trainer NPC
↓
Starting Gear
↓
Balance Check
↓
Release

## Checklist

[ ] Role rõ ràng (không trùng 100% class cũ)

[ ] Base stats hợp lý

[ ] 4-6 skill khởi đầu

[ ] Skill Tree hoàn chỉnh

[ ] Weapon nào dùng được

[ ] Vision recommendation

[ ] Quest unlock class

[ ] NPC trainer (dialogue, vị trí)

[ ] Starting gear

[ ] Balance vs 4 class gốc

[ ] Party role

[ ] PvP balance check

## Future Class Candidates

Paladin — Tank/Healer hybrid, STR + WIS

Ranger — Ranged DPS, DEX + AGI

Necromancer — Minion DPS, INT + WIS

Spellblade — Melee/Magic hybrid, STR + INT

Dragoon — Jump/Dive DPS, STR + AGI

Monk — Fist DPS, STR + AGI

Bard — Support, DEX + WIS

Summoner — Pet DPS, INT + END

---

# Khi thêm Vision mới

## Pipeline

Element
↓
Status Effect
↓
Reaction với 4 element cũ
↓
Element Boss
↓
Element Dungeon
↓
Element Gear Set
↓
Vision Ascension
↓
Balance Check
↓
Release

## Checklist

[ ] Element identity rõ ràng

[ ] Status effect không trùng element cũ

[ ] Phản ứng với Fire/Water/Ice/Lightning

[ ] Boss element (design + mechanic)

[ ] Dungeon element (theme + mobs)

[ ] Gear set element

[ ] Vision passive pool (10+ passives)

[ ] Vision signature effect

[ ] Ascension tree

[ ] Balance vs 4 Vision gốc

## Future Element Candidates

EARTH — Defense + Shield

WIND — Mobility + Dodge

LIGHT — Healing + Buff

DARK — Debuff + Life Steal

---

# Khi thêm Weapon mới

## Pipeline

Weapon Type
↓
Base Stats + Scaling
↓
Mastery Tree (4 mốc: 25/50/75/100)
↓
Skill Compatibility
↓
Class Compatibility
↓
Epic Fight Integration
↓
Weapon Art (SAO style)
↓
Crafting Recipe
↓
Balance Check
↓
Release

## Checklist

[ ] Weapon identity (khác weapon hiện tại)

[ ] Primary + Secondary scaling

[ ] Class nào dùng được

[ ] Skill tương thích

[ ] Mastery bonus hợp lý

[ ] Weapon Art

[ ] Epic Fight animation

[ ] Crafting recipe

[ ] Legendary + Mythic version

[ ] Balance vs weapon cùng loại

---

# Khi thêm Dungeon mới

## Pipeline

Dungeon Theme
↓
Floor Requirement
↓
Tier + Level Range
↓
Mobs + Elite
↓
Boss Design
↓
Boss Mechanics
↓
Loot Table
↓
Achievements
↓
Testing
↓
Release

## Checklist

[ ] Theme phù hợp Floor

[ ] Difficulty hợp lý vs tier

[ ] Mob variety (3-5 loại)

[ ] Elite mobs (1-2 loại)

[ ] Boss design (3 phases)

[ ] Boss mechanics (ít nhất 3 mechanic)

[ ] Loot table (tất cả rarity)

[ ] Dungeon-specific drop

[ ] Achievement

[ ] Speed run possible

---

# Khi thêm Boss mới

## Pipeline

Boss Concept
↓
Element + Theme
↓
3 Phases
↓
Skills (5-8 skills)
↓
Mechanics (4-6 mechanics)
↓
Arena Design
↓
Loot Table
↓
Music/Sound
↓
Balance
↓
Release

## Checklist

[ ] Visual design concept

[ ] Element identity

[ ] Phase transitions rõ ràng

[ ] Mechanics diverse (stack, spread, dodge, DPS check, etc.)

[ ] Arena hazards

[ ] Enrage timer

[ ] Loot table (guaranteed + chance)

[ ] Boss Soul drop

[ ] Achievement

[ ] Tested solo + party

---

# Khi thêm Skill mới

## Pipeline

Skill Concept
↓
Class Requirement
↓
Damage/Cost/Cooldown
↓
Animation
↓
Particle Effect
↓
Sound Effect
↓
Scaling Formula
↓
Skill Tree Position
↓
Balance Check
↓
Release

## Checklist

[ ] Class-bound hay universal

[ ] Vision interaction

[ ] Cost (Mana/Stamina/HP) hợp lý

[ ] Cooldown hợp lý (không spam được)

[ ] Animation sync với Epic Fight

[ ] Hitbox chính xác

[ ] Damage formula rõ ràng

[ ] Skill Tree position hợp lý

[ ] Upgrade path

[ ] Balance vs skill cùng tier

---

# Khi thêm Quest mới

## Pipeline

Quest Story
↓
Type (Main/Side/Daily/etc.)
↓
Objectives
↓
NPC Dialogue
↓
Rewards
↓
Quest Chain
↓
Testing
↓
Release

## Checklist

[ ] Story phù hợp lore

[ ] Objective rõ ràng

[ ] NPC dialogue đầy đủ

[ ] Rewards hợp lý vs difficulty

[ ] Quest marker hoạt động

[ ] Quest item không bug

[ ] Chain logic đúng (prerequisite)

[ ] Faction reputation thay đổi

[ ] Tested multiple paths

---

# Khi thêm Event mới

## Pipeline

Event Concept
↓
Type (World/Guild/Seasonal)
↓
Mechanics
↓
Schedule
↓
Rewards
↓
Announcement
↓
Testing
↓
Release

## Checklist

[ ] Event không conflict event khác

[ ] Schedule hợp lý (không spam)

[ ] Rewards xứng đáng

[ ] Participation reward (ai cũng có)

[ ] MVP reward (top performance)

[ ] Announcement system hoạt động

[ ] Event không gây lag server

[ ] Tested với nhiều player

---

# Version Release Checklist

## Code

[ ] Không crash

[ ] Không memory leak

[ ] Không infinite loop

[ ] TPS ổn định (≥ 18)

## Balance

[ ] Balance review (doc 37)

[ ] Không có build bắt buộc mới

[ ] Không class vô dụng

## Content

[ ] Quest hoàn thành được

[ ] Boss kill được

[ ] Dungeon clear được

[ ] Drop rate đúng config

[ ] Achievement unlock được

## Multiplayer

[ ] Packet sync đúng

[ ] Không dupe bug

[ ] Không infinite gold/exp

[ ] Server validation hoạt động

[ ] Dedicated server test

## Polish

[ ] Text không lỗi

[ ] Icon không thiếu

[ ] Sound không thiếu

[ ] Animation mượt

---

# Documentation Update

Mỗi lần thêm content mới:

[ ] Update doc tương ứng

[ ] Update MASTER_DESIGN_DOCUMENT.md

[ ] Update PROJECT_STATE.md

[ ] Update version roadmap nếu cần

---

# Future Expansion Areas

Aincrad Floor 101+

Cross-server content

Mobile companion app

Web API

Content Creator tools

Modding API (cho modder khác mở rộng RPGMOD)
