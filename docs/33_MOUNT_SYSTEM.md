# PHASE 33 - MOUNT SYSTEM ⭐⭐

## Mục tiêu

Tăng tốc độ di chuyển giữa các khu vực.

Mount cũng là một hệ thống sưu tầm.

---

# Package

com.rpgpack.mount

---

# Files Required

MountManager.java

MountData.java

MountType.java

MountStats.java

MountSkillManager.java

MountScreen.java

MountSpawnManager.java

---

# Mount Types

## Horse

Speed: Base

Stamina: Base

Jump: Base

Unlock: Level 10, Gold × 1000

## Dire Wolf

Speed: +15%

Stamina: +10%

Jump: -1 block

Unlock: Wolf Boss Drop (rare)

## Gryphon

Speed: +50%

Stamina: +20%

Jump: +3 blocks

Passive: Glide (giảm fall damage 90%)

Unlock: Floor 50 Quest

## Dragon

Speed: +100%

Stamina: +50%

Jump: +5 blocks

Passive: Limited Flight

Unlock: Dragon Boss Rare Drop + Dragon Taming Quest

---

# Mount Stats

Speed: Di chuyển nhanh bao nhiêu

Stamina: Bao lâu thì mệt

Jump: Nhảy cao bao nhiêu

Armor: Mount có thể nhận damage không

## Speed Formula

Base Speed × (1 + Mount Level × 0.01)

## Stamina Formula

Base Stamina + (Mount Level × 5)

## Stamina Drain

Sprint: 5 / second

Jump: 10 / lần

Flight: 15 / second

## Stamina Regen

Đứng yên: 10 / second

Đi bộ: 5 / second

---

# Mount Level System

Level: 1 → 50

Tăng EXP khi:

Di chuyển (mỗi 1000 blocks = 1 EXP)

Hoàn thành quest khi đang cưỡi

## Level Bonus

Level 10: +10% Speed

Level 20: Unlock Mount Skill

Level 30: +10% Stamina

Level 40: Armor +20%

Level 50: Evolution Unlock

---

# Mount Skills

## Horse — Charge

Tăng 50% speed trong 5 giây.

Cooldown: 30 giây.

## Dire Wolf — Howl

Giảm 20% damage của kẻ địch gần đó.

Duration: 10 giây.

## Gryphon — Swoop

Bay lên và lao xuống tại điểm chọn.

Không nhận damage khi hạ cánh.

## Dragon — Flame Flight

Bay và phun lửa xuống dưới.

Damage: INT × 2 (AOE).

---

# Mount Equipment

## Saddle

Tăng Mount Armor.

Các loại: Leather, Iron, Gold, Diamond.

## Armor

Tăng Mount HP + Defense.

Các loại: Light, Medium, Heavy.

## Cosmetic

Mount Skin (đổi màu, thêm giáp, thêm hiệu ứng).

---

# Mount Inventory

Một số Mount có inventory:

Horse: 5 slots

Dire Wolf: 5 slots

Gryphon: 10 slots

Dragon: 15 slots

---

# Mount Restrictions

Không dùng Mount trong:

Dungeon

Raid Instance

Boss Arena

PvP Arena (tùy mode)

Town (một số town cấm)

---

# Mount Collection

## Collection Progress

Mỗi Mount unlock → +Collection Score.

Mount Level 50 → Bonus Score.

Đủ bộ → Mount Collector Title, Reward.

## Collection Rewards

5 Mounts: +5% Mount Speed

10 Mounts: Mount Master Title

All Mounts: Legendary Mount Unlock

---

# Rare Mounts

## Unicorn

Speed: 120%

Color: Trắng + hiệu ứng cầu vồng

Unlock: World Event (rare spawn)

## Nightmare

Speed: 130%

Color: Đen + lửa tím

Unlock: Demon Portal Boss (rare)

## Phoenix

Speed: 150%

Có thể bay lướt (không flight hoàn toàn)

Unlock: Fire Raid Boss (Mythic drop)

---

# Mount UI

Mount Selection Screen:

Mount 3D Preview

Stats Comparison

Equipment Slots

Skin Selection

Summon/Dismiss Button (hotkey: Y)

---

# Future Expansion

Mount Breeding

Mount Racing (minigame)

Flying Mount full flight

Water Mount (underwater)

Multi-seat Mount (chở party)
