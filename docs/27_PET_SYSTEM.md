# PHASE 27 - PET SYSTEM ⭐⭐

## Mục tiêu

Tạo hệ thống Pet độc lập với Companion.

Pet không có cốt truyện, tập trung vào gameplay utility.

---

# Package

com.rpgpack.pet

---

# Files Required

PetData.java

PetManager.java

PetType.java

PetAI.java

PetEvolutionManager.java

PetScreen.java

---

# Pet Types

## Combat Pet

Đánh quái.

Có skill.

Scale theo level người chơi.

## Utility Pet

Nhặt đồ tự động.

Mở rộng inventory.

Auto-loot filter.

## Mount Pet

Cưỡi.

Tăng tốc độ di chuyển.

Có stamina riêng.

---

# Combat Pets

## Wolf

Vai trò: Melee DPS

Skill: Howl (+10% Damage to party, 10 giây)

Passive: Bleed on attack

## Baby Dragon

Vai trò: Ranged DPS

Skill: Fire Breath (AOE cone)

Passive: Burn on attack

## Spirit Fox

Vai trò: Magic Support

Skill: Mana Gift (hồi 20% Mana cho chủ)

Passive: +5 Mana Regen for owner

## Iron Golem

Vai trò: Mini Tank

Skill: Taunt (aggro nearby enemies)

Passive: +5% Defense for owner

---

# Utility Pets

## Loot Rabbit

Auto-pickup item trong 8 blocks.

+10 inventory slots.

Filter: chỉ nhặt RARE trở lên (cấu hình được).

## Treasure Sprite

Phát hiện chest ẩn trong 16 blocks.

+5% Rare Drop Rate.

## Ore Sniffer

Đánh dấu quặng giá trị trong 12 blocks.

Auto-mine basic ore.

---

# Mount Pets

## Horse

Speed: 150%

Stamina: 200

Jump: 3 blocks

## Dire Wolf

Speed: 170%

Stamina: 250

Jump: 2 blocks

Passive: Intimidate (mobs chạy khi đến gần)

## Gryphon

Speed: 200%

Stamina: 300

Jump: 5 blocks

Passive: Glide (giảm fall damage 80%)

## Dragon

Speed: 250%

Stamina: 500

Jump: 8 blocks

Passive: Flight (limited)

Cần điều kiện đặc biệt để unlock.

---

# Pet Level System

Pet Level: 1 → 100

Tăng EXP khi:

Combat Pet: tham gia combat

Utility Pet: sử dụng ability

Mount Pet: di chuyển

## Level Bonus

Mỗi Level:

+1% Pet Stats

Level 25: Unlock Skill 1

Level 50: Unlock Passive

Level 75: Unlock Skill 2

Level 100: Evolution Unlock

---

# Pet Evolution

## Evolution Tiers

Common → Uncommon → Rare → Epic → Legendary

## Evolution Requirements

Level đạt mốc + Evolution Stone + Gold

Ví dụ:

Wolf (Common → Uncommon)

Wolf Level 25 + Beast Stone × 5 + Gold × 5000

## Evolution Bonus

Tăng stats

Mở skill mới

Thay đổi visual

Đổi tên (ví dụ: Wolf → Dire Wolf → Fenrir)

---

# Pet Food System

## Food Types

Meat: +HP Regen

Fish: +Stamina Regen

Berry: +EXP Gain

Special Food: Temporary Buff

## Hunger System

Pet có Hunger bar.

Giảm theo thời gian (chậm).

Feed để duy trì.

Nếu đói: Pet giảm 50% stats, không dùng skill.

---

# Pet Recall

Có thể recall pet bất kỳ lúc nào.

Pet hồi phục HP khi recalled.

Recall không mất buff.

---

# Pet Inventory

Pet có inventory riêng (5-15 slots).

Utility Pet: +10 slots.

Dùng để chở đồ.

---

# Pet UI

Pet Status (HP, Hunger, EXP)

Pet Skill Bar (2 skills)

Pet Mode (Follow/Stay/Aggressive/Passive)

Pet Inventory

Evolution Button

---

# Rare Pets

## Drop Sources

Boss Drop (rare)

World Event Reward

Achievement Reward

Season Pass

## Example Rare Pets

Phoenix Chick → Phoenix (Fire Combat Pet)

Baby Kraken → Kraken (Water Combat Pet)

Frost Cub → Frost Tiger (Ice Mount)

---

# Future Expansion

Pet Breeding

Pet Trading

Pet Arena (PvP)

Legendary Pet Quest
