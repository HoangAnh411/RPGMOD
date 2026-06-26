# PHASE 30 - RAID SYSTEM ⭐⭐⭐

## Mục tiêu

Tách riêng Raid khỏi Boss System (doc 09).

Raid là nội dung cao cấp nhất, yêu cầu tổ chức đội hình và cơ chế phức tạp.

---

# Package

com.rpgpack.raid

---

# Files Required

RaidManager.java

RaidInstance.java

RaidPartyManager.java

RaidMechanicManager.java

RaidBossManager.java

RaidRewardManager.java

RaidScreen.java

RaidQueueManager.java

---

# Raid Structure

## Party Size

8 người cố định.

## Role Composition

2 Tank

3 DPS

1 Support

1 Healer

1 Flex

## Raid Duration

60 phút (không tính thời gian chuẩn bị).

Nếu hết thời gian → Raid Failed.

---

# Raid Roles

## Tank

Nhiệm vụ:

Giữ aggro boss

Position boss đúng vị trí

Sống qua tank buster

Thực hiện tank swap nếu cần

Yêu cầu:

High DEF, High HP

Class đề xuất: Warrior

## DPS

Nhiệm vụ:

Gây damage tối đa

Pass DPS check

Xử lý add mobs

Tránh mechanic

Yêu cầu:

High Damage, Mobility

Class đề xuất: Berserker, Assassin, Mage

## Support

Nhiệm vụ:

Buff party

Debuff boss

Crowd control adds

Utility skill

Yêu cầu:

Buff/Debuff, CC

Class đề xuất: Mage (Water/Ice)

## Healer

Nhiệm vụ:

Giữ party sống

Dispel debuff

Heal qua raid-wide damage

Thực hiện heal check

Yêu cầu:

High Healing, Mana Pool

Class đề xuất: Mage (Water Vision) + Healer build

---

# Raid Mechanics

## Stack Marker

Icon xuất hiện trên 1 người chơi.

Tất cả phải stack vào (đứng chung) để chia damage.

Nếu không đủ người stack → chết.

## Spread Marker

Icon xuất hiện trên nhiều người chơi.

Phải tản ra (đứng xa nhau).

Nếu đứng gần → AOE damage toàn party.

## Safe Zone

Boss cast AOE toàn sàn trừ 1 vùng an toàn.

Người chơi phải di chuyển vào vùng an toàn.

Thời gian: 5 giây.

## Tank Buster

Đòn đánh cực mạnh vào Tank.

Tank phải dùng defensive cooldown.

Nếu không giảm sát thương → One-shot.

## Tank Swap

Boss đặt debuff stacking lên Tank.

Sau 2 stack: Tank còn lại phải lấy aggro.

Nếu không swap → Tank chết.

## Wipe Mechanic

Nếu party không hoàn thành mechanic → Wipe.

Ví dụ: không kill add kịp → AOE 999999 damage.

## DPS Check

Boss có phase cần giết trong thời gian giới hạn.

Nếu không đạt → Enrage → Wipe.

## Enrage

Khi thời gian raid hết hoặc phase quá lâu:

Boss +100% Damage

Boss +50% Attack Speed

Party wipe nếu không kết thúc nhanh.

---

# Raid Boss Phases

## Phase 1 — Introduction

HP: 100% → 70%

Mechanic: Cơ bản (Stack, Spread)

Mục tiêu: Làm quen mechanic

## Phase 2 — Escalation

HP: 70% → 40%

Mechanic: Thêm Tank Buster, DPS Check

Adds xuất hiện

Mục tiêu: Kiểm tra coordination

## Phase 3 — Final

HP: 40% → 0%

Mechanic: Tất cả mechanic + Enrage timer

Safe Zone + Spread đồng thời

Mục tiêu: Execution check

---

# Raid Tiers

## Normal Raid

Level yêu cầu: 80+

Gear Score: 500+

Mechanic: Đầy đủ nhưng forgiving

Reward: Legendary Gear

## Heroic Raid

Level yêu cầu: 100

Gear Score: 800+

Mechanic: Nhanh hơn, damage cao hơn

Reward: Legendary Gear (higher stats) + Mythic Fragment

## Mythic Raid

Level yêu cầu: 100 + Ascension

Gear Score: 1000+

Mechanic: Thêm Mythic mechanic (ẩn)

Reward: Mythic Gear

---

# Example Raid Boss

## Ignis — The Infernal Lord

Element: Fire

### Phase 1: Burning Throne

Mechanic: Stack Marker (Flame Brand)

Arena: Lửa lan từ từ vào giữa

### Phase 2: Meteor Storm

Mechanic: Spread Marker + Safe Zone đồng thời

DPS Check: Kill 4 Fire Elementals trong 30 giây

### Phase 3: Inferno

Mechanic: Tất cả mechanic + Enrage

Wipe Mechanic: Nếu không break Shield trong 20 giây → Wipe

### Rewards

Ignis Soul, Inferno Core, Fire Legendary Gear, Chance Fire Mythic Weapon

---

# Raid Lockout

## Weekly Reset

Mỗi tuần clear được 1 lần / tier / boss.

Reset: Monday 00:00.

## Lockout Types

Normal: 1 clear / week

Heroic: Share lockout với Normal (chọn 1)

Mythic: Lockout riêng

---

# Raid Preparation

## Ready Check

Tất cả thành viên phải Ready.

Nếu không → không vào được.

## Consumable Check

Game gợi ý nếu thiếu potion/food.

## Gear Check

Game cảnh báo nếu Gear Score quá thấp.

---

# Raid UI

Raid Frame (góc trái):

HP từng thành viên

Debuff icons

Role icon

Boss Frame (giữa trên):

Boss HP

Boss Cast Bar

Boss Phase

Mechanic Warnings:

Screen flash

Sound alert

Text popup (VD: "STACK!", "SPREAD!", "SAFE ZONE!")

---

# Raid Rewards

## Guaranteed

Gold, Raid Token, Boss Soul

## Roll

Legendary Gear (1-2 món / clear)

Mythic Fragment (Heroic+)

## Weekly Bonus

First clear mỗi tuần:

Bonus Gold + Raid Token ×2

---

# Raid Token

Dùng tại Raid Vendor:

Đổi Legendary Gear (cần token + boss soul)

Mythic Fragment × 10 → Mythic Essence

Raid Cosmetic

---

# Raid Leaderboard

Server ranking:

Fastest Clear Time / tier

First Clear (Hall of Fame)

Most Raid Clears

---

# Future Expansion

Raid Finder (auto-matchmaking)

Savage Raid (ultra hard)

Ultimate Raid (1 boss, 20 phút marathon)

Raid Race (world first competition)
