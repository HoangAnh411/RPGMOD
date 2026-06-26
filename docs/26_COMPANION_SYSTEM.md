# PHASE 26 - COMPANION SYSTEM ⭐⭐

## Mục tiêu

Rất hợp với chủ đề Sword Art Online.

Người chơi có thể có bạn đồng hành AI hỗ trợ trong combat và exploration.

---

# Package

com.rpgpack.companion

---

# Files Required

CompanionData.java

CompanionManager.java

CompanionAI.java

CompanionType.java

CompanionEquipment.java

CompanionLevelManager.java

CompanionScreen.java

---

# Companion Types

## NPC Companion

Nhân vật cốt truyện.

Có backstory.

Có questline riêng.

Có skill riêng.

Unlock qua Main Quest.

---

## Summon Companion

Triệu hồi tạm thời.

Từ Skill hoặc Item.

Thời gian giới hạn.

Mạnh nhưng có cooldown.

---

## Pet (xem doc 27)

---

# NPC Companion Examples

## Kirito — The Swordsman

Vai trò: DPS

Class: Dual Blades (hybrid Sword/Dagger)

Skill: Starburst Stream (multi-hit combo)

Unlock: Floor 1 Main Quest

## Asuna — The Flash

Vai trò: Hybrid DPS/Support

Class: Rapier

Skill: Flashing Penetration (high burst)

Unlock: Floor 5 Main Quest

## Klein — The Samurai

Vai trò: Tank

Class: Katana

Skill: Bushido Stance (defense buff)

Unlock: Floor 3 Main Quest

## Agil — The Merchant

Vai trò: Utility

Class: Greatsword

Passive: Shop Discount 10%

Unlock: Floor 2 Side Quest

---

# Companion AI Modes

## Follow Mode

Đi theo người chơi.

Không tấn công trừ khi bị đánh.

## Assist Mode

Tấn công mục tiêu người chơi đang đánh.

Dùng skill tự động.

## Aggressive Mode

Tự tìm và tấn công kẻ địch gần nhất.

Dùng mọi skill.

## Passive Mode

Chỉ đứng yên.

Không làm gì.

---

# Companion Roles

## DPS Companion

Tập trung damage.

Thấp HP.

Dùng offensive skill.

## Tank Companion

Tập trung aggro.

Cao HP + Defense.

Dùng taunt và guard skill.

## Healer Companion

Hồi máu người chơi.

Dispel debuff.

Buff phòng thủ.

## Support Companion

Buff người chơi.

Debuff kẻ địch.

Utility skill.

---

# Companion Stats

Companion có stats giống người chơi nhưng scale khác.

Level: 1 → 100

Stats tăng theo Level.

## Stat Growth

STR: +1 / Level

VIT: +1 / Level

AGI: +0.5 / Level

DEX: +0.5 / Level

INT: +0.5 / Level

END: +0.5 / Level

WIS: +0.5 / Level

LUK: +0.5 / Level

---

# Companion Equipment

Companion có 3 slot:

Weapon

Armor

Accessory

Không dùng chung inventory với người chơi.

---

# Companion Affection System

## Affection Levels

NEUTRAL → FRIENDLY → CLOSE → BONDED → SOULMATE

Tăng qua:

Chiến đấu cùng

Tặng quà

Hoàn thành quest của companion

## Affection Bonus

FRIENDLY: +5% Companion Damage

CLOSE: +10% Companion HP

BONDED: Unlock Companion Skill 2

SOULMATE: Unlock Companion Ultimate

---

# Companion Ultimate

Mỗi NPC Companion có 1 Ultimate skill.

Yêu cầu: Affection SOULMATE

Cooldown: 180 giây

Ví dụ:

Kirito → Dual Blades: Eclipse (massive AOE slash)

Asuna → Mother's Rosario (11-hit combo + heal)

---

# Summon Companion

## Summon Types

Fire Elemental — DPS

Water Spirit — Heal

Ice Golem — Tank

Lightning Wisp — Support

## Summon Duration

30 giây

Cooldown: 120 giây

## Summon Item

Summon Stone × loại element.

Có thể craft tại Alchemist.

---

# Companion UI

Companion Portrait (góc trái)

HP Bar

Mode Toggle (F1-F4)

Command Wheel (chuột phải companion)

Inventory Access

---

# Party with Companion

Companion không tính vào Party limit.

Max 1 companion active.

---

# Future Expansion

Companion PvP

Companion Ranking

Legendary Companion

Companion Awakening
