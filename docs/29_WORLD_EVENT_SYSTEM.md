# PHASE 29 - WORLD EVENT SYSTEM ⭐⭐⭐

## Mục tiêu

Tạo cảm giác server sống động.

World Event là sự kiện toàn server, không phải quest cá nhân.

Tất cả người chơi online đều tham gia được.

---

# Package

com.rpgpack.worldevent

---

# Files Required

WorldEventManager.java

WorldEventData.java

WorldEventType.java

WorldEventScheduler.java

WorldEventRewardManager.java

WorldEventAnnouncer.java

WorldEventBoss.java

---

# Event Categories

## Scheduled Events

Diễn ra theo lịch cố định.

Ví dụ: mỗi 6 giờ, mỗi ngày 20:00.

## Random Events

Random trigger.

Hiếm hơn nhưng reward cao hơn.

## Admin Events

Admin kích hoạt thủ công.

Dùng cho event đặc biệt.

---

# Monster Invasion

## Cơ chế

Làn sóng quái tấn công 1 khu vực.

Số lượng scale theo player online.

## Waves

Wave 1: 20 mobs thường

Wave 2: 30 mobs + 2 Elite

Wave 3: 40 mobs + 5 Elite + 1 Mini Boss

Wave Boss: Invasion Boss

## Invasion Boss

HP scale: Base × Players × 1.5

Reward: Invasion Chest (guaranteed EPIC+)

## Event Duration

30 phút

Cooldown: 4 giờ

---

# Demon Portal

## Cơ chế

Portal xuất hiện ngẫu nhiên ở 1 Floor.

Người chơi vào portal → dungeon instance đặc biệt.

## Portal Dungeon

Giới hạn 8 người / instance.

5 wave quái + 1 Demon Boss.

## Demon Boss

HP: Raid-level

Mechanic: có DPS check

Reward: Demon Soul, Legendary Gear (high chance)

## Event Duration

Portal mở 15 phút.

Sau đó dungeon vẫn clear được nếu đã vào.

---

# Elemental Storm

## Cơ chế

Toàn bộ 1 Floor bị ảnh hưởng bởi 1 element.

## Effects by Element

### Fire Storm

Tất cả quái +30% Fire Damage

Người chơi nhận DOT nhẹ

Fire reaction damage ×2

### Ice Storm

Tất cả quái gây Chill

Slow aura toàn Floor

Ice reaction duration ×1.5

### Lightning Storm

Tất cả quái +20% Attack Speed

Người chơi bị Shock mỗi 30 giây

Lightning reaction chain +2

### Water Storm

Mưa toàn Floor

Wet applied tự động

Water reaction bonus ×1.5

## Storm Boss

Giữa Storm có Storm Elemental Boss.

Defeat → Storm kết thúc sớm.

Reward: Elemental Core (Vision Ascension material)

## Event Duration

30 phút

Cooldown: 6 giờ

---

# World Boss Spawn

## Cơ chế

Boss siêu lớn spawn ở khu vực mở.

Tất cả người chơi cùng đánh.

## World Bosses

### Infernal Dragon

Floor: 10

HP: Massive (scale theo player)

Mechanic: Fire Breath, Meteor Rain, Flight Phase

Reward: Dragon Scale, Dragon Soul, Legendary Gear

### Storm Emperor

Floor: 30

HP: Massive

Mechanic: Lightning Storm, Thunder Cage, Split Phase

Reward: Storm Core, Thunder Essence, Legendary Gear

### Frozen Leviathan

Floor: 50

HP: Massive

Mechanic: Ice Breath, Freeze Prison, Submerge Phase

Reward: Leviathan Scale, Frost Core, Legendary Gear

### Void Titan

Floor: 90

HP: Colossal

Mechanic: Void Pull, Reality Break, DPS Check Phase

Reward: Void Essence, Mythic Fragment, Chance Mythic Gear

## Boss Schedule

Infernal Dragon: Daily 20:00

Storm Emperor: Weekly Saturday 20:00

Frozen Leviathan: Weekly Sunday 20:00

Void Titan: Monthly First Saturday 20:00

---

# Double EXP Weekend

## Cơ chế

+100% EXP toàn server.

48 giờ (Saturday 00:00 → Sunday 23:59).

## Bonus

+50% Rare Drop Rate

+50% Gold Drop

---

# Event Rewards

## Participation Reward

Tham gia là có (không cần MVP).

Gold × 1000

EXP × 5000

Event Token × 1

## Contribution Reward

Dựa trên damage đóng góp:

Top 1: Mythic Chance Box

Top 10: Legendary Box

Top 50: Epic Box

Tất cả: Participation Chest

## Event Currency

Event Token

Dùng đổi:

Event Shop items

Limited Cosmetics

Event Title

---

# Event Announcements

## Pre-Event

15 phút trước: Chat Warning

5 phút trước: Screen Popup

1 phút trước: Countdown

## Event Start

Server-wide broadcast

Sound effect

Screen effect

## Event End

Result announcement

MVP announcement

Reward distribution

---

# Event Calendar

Admin có thể config lịch event.

File config:

config/rpgpack/events.json

Ví dụ:

{
  "monster_invasion": {"interval_hours": 4, "random_offset": 30},
  "elemental_storm": {"interval_hours": 6, "floors": [5, 15, 25]},
  "world_boss": {"schedule": "daily", "time": "20:00"}
}

---

# Admin Commands

/rpg event start <type>

/rpg event stop

/rpg event schedule <type> <time>

/rpg event force <type> <floor>

---

# Future Expansion

Cross-server Event

Guild War Event

Holiday Special Event

Community Vote Event

Dynamic Event Chain
