# PHASE 31 - COSMETIC SYSTEM ⭐⭐

## Mục tiêu

Cosmetic không ảnh hưởng balance.

Mục đích: thể hiện cá nhân, reward cho achievement, monetization (nếu có).

---

# Package

com.rpgpack.cosmetic

---

# Files Required

CosmeticManager.java

CosmeticData.java

SkinManager.java

AuraManager.java

EmoteManager.java

CosmeticScreen.java

CosmeticUnlockManager.java

---

# Cosmetic Types

## Weapon Skins

Thay đổi ngoại hình vũ khí.

Giữ nguyên stats.

Ví dụ: Flame Sword Skin → Iron Sword có lửa bao quanh.

## Armor Skins

Thay đổi ngoại hình giáp.

Giữ nguyên stats.

Ví dụ: Royal Armor Skin → giáp thường thành giáp vàng.

## Vision Skins

Thay đổi hiệu ứng Vision.

Ví dụ: Dark Fire Vision → lửa màu tím.

## Auras

Hiệu ứng bao quanh nhân vật.

Ví dụ: Lightning Aura → tia điện quanh người.

## Titles

Hiển thị danh hiệu (xem doc 28).

## Emotes

Hành động biểu cảm.

Ví dụ: /wave, /bow, /dance, /sit.

---

# Skin Rarity

COMMON — Mặc định, dễ kiếm

RARE — Quest reward, event

EPIC — Achievement, Dungeon drop

LEGENDARY — Raid reward, Season pass

MYTHIC — Cực hiếm, World First, Special Event

---

# Weapon Skins

## Fire Series

Flame Sword

Molten Greatsword

Inferno Dagger

Blazing Bow

Phoenix Staff

## Ice Series

Frost Sword

Glacial Greatsword

Crystal Dagger

Frozen Bow

Blizzard Staff

## Lightning Series

Storm Sword

Thunder Greatsword

Spark Dagger

Bolt Bow

Tempest Staff

## Special Series

Shadow Weapons (đen + tím)

Holy Weapons (vàng + trắng)

Corrupted Weapons (đỏ + đen)

Dragon Weapons (scale texture)

---

# Armor Skins

## Theme Sets

Royal Knight Set

Shadow Assassin Set

Arcane Scholar Set

Dragon Knight Set

SAO Hero Set (Kirito style)

## Full Set Visual

Khi mặc đủ set skin → hiệu ứng đặc biệt.

Ví dụ: Dragon Set → cánh rồng ảo sau lưng.

---

# Vision Skins

## Elemental Variants

Dark Fire (lửa tím)

Holy Fire (lửa trắng)

Frost Fire (lửa xanh) — rare

Blood Lightning (sét đỏ)

## Vision Effect Level

Level 1: Đổi màu hiệu ứng

Level 2: Đổi màu + thêm particle

Level 3: Đổi toàn bộ visual animation

---

# Auras

## Combat Auras

Warrior Aura: Khiên ánh sáng xoay quanh

Berserker Aura: Lửa đỏ bùng cháy

Assassin Aura: Bóng tối mờ ảo

Mage Aura: Rune phép xoay quanh

## Achievement Auras

Ascension 1-10: Hào quang tăng dần

Floor 100 Clear: Aincrad Crown Aura

Raid First Clear: Champion Aura

Mythic Gear Full Set: Mythic Glow

## Event Auras

Season Top 100: Season Aura

World Event MVP: Elemental Crown

---

# Emotes

## Basic Emotes

/wave — Vẫy tay

/bow — Cúi chào

/sit — Ngồi

/laugh — Cười

/cry — Khóc

/point — Chỉ tay

/cheer — Cổ vũ

/clap — Vỗ tay

## Combat Emotes

/taunt — Khiêu khích

/victory — Ăn mừng

/defeat — Thất bại

/ready — Sẵn sàng chiến đấu

## Special Emotes

/dance1-5 — Điệu nhảy

/flare — Bùng cháy năng lượng

/meditate — Ngồi thiền

## Emote Unlock

Basic: Mặc định

Combat: Achievement

Special: Event / Season Pass

---

# Cosmetic Unlock Sources

## Free Sources

Quest Reward

Achievement

Dungeon Drop (rare)

Boss Drop (very rare)

Floor First Clear

## Event Sources

Season Pass (free track)

World Event Participation

Holiday Event

## Premium Sources (Server-dependent)

Donation perk

VIP rank

Cosmetic Shop (in-game gold hoặc premium)

---

# Cosmetic Preview

Trong Cosmetic Screen:

Xoay 3D model

Preview từng skin

Preview full set

Compare với current

---

# Cosmetic Save

Cosmetics lưu vào PlayerData.

Tồn tại qua death, restart.

Không mất khi Prestige (giữ lại cosmetic).

---

# Trade System

Cosmetics COMMON-RARE: Trade được

Cosmetics EPIC+: Soulbound (không trade được)

Event Cosmetics: Tùy event

---

# Future Expansion

Dye System (tự phối màu)

Custom Particle Editor

Player Pose Pack

Weapon Trail Effect

Back Attachment (cánh, túi)
