# PHASE 35 - ADMIN TOOLS SYSTEM ⭐⭐⭐

## Mục tiêu

Cực kỳ quan trọng cho quản trị server.

Admin phải có đầy đủ công cụ để quản lý người chơi, debug, xử lý sự cố.

---

# Package

com.rpgpack.admin

---

# Files Required

AdminCommandManager.java

AdminCommandRegistry.java

PlayerInspector.java

QuestInspector.java

EconomyInspector.java

WorldEventInspector.java

AdminPanelScreen.java

AdminPermissionManager.java

---

# Permission System

## Admin Levels

ADMIN_LEVEL_1 — Helper/Mod

ADMIN_LEVEL_2 — Moderator

ADMIN_LEVEL_3 — Admin

ADMIN_LEVEL_4 — Head Admin

ADMIN_LEVEL_5 — Owner

## Permission Nodes

rpg.admin.player — Quản lý người chơi

rpg.admin.economy — Quản lý kinh tế

rpg.admin.quest — Quản lý quest

rpg.admin.dungeon — Quản lý dungeon

rpg.admin.raid — Quản lý raid

rpg.admin.event — Quản lý event

rpg.admin.guild — Quản lý guild

rpg.admin.ban — Cấm người chơi

rpg.admin.rollback — Rollback dữ liệu

---

# Player Commands

## /rpg setlevel <player> <level>

Đặt level cho người chơi.

Permission: rpg.admin.player

## /rpg addexp <player> <amount>

Thêm EXP cho người chơi.

Permission: rpg.admin.player

## /rpg setclass <player> <class>

Đổi class (bỏ qua yêu cầu reset item).

Permission: rpg.admin.player

## /rpg resetclass <player>

Reset class về NONE.

Permission: rpg.admin.player

## /rpg givevision <player> <element> <rarity>

Đưa Vision cho người chơi.

Permission: rpg.admin.player

## /rpg setvisionlevel <player> <level>

Đặt Vision Level.

Permission: rpg.admin.player

## /rpg resetstats <player>

Reset toàn bộ Stat Points.

Permission: rpg.admin.player

## /rpg resetskills <player>

Reset toàn bộ Skill Points.

Permission: rpg.admin.player

## /rpg setmastery <player> <weapon> <level>

Đặt Weapon Mastery level.

Permission: rpg.admin.player

## /rpg heal <player>

Hồi đầy HP/Mana/Stamina.

Permission: rpg.admin.player

## /rpg kill <player>

Giết người chơi (dùng cho stuck bug).

Permission: rpg.admin.player

---

# Economy Commands

## /rpg givegold <player> <amount>

Thêm Gold.

Permission: rpg.admin.economy

## /rpg takegold <player> <amount>

Xóa Gold.

Permission: rpg.admin.economy

## /rpg giveitem <player> <item> <rarity> [amount]

Đưa item với rarity chỉ định.

Permission: rpg.admin.economy

## /rpg givegear <player> <slot> <rarity> <level>

Tạo gear và đưa cho người chơi.

Permission: rpg.admin.economy

## /rpg clearecon <player>

Xóa toàn bộ vàng và item (trừ soulbound).

Permission: rpg.admin.economy

---

# Quest Commands

## /rpg quest start <player> <questId>

Bắt đầu quest cho người chơi.

Permission: rpg.admin.quest

## /rpg quest complete <player> <questId>

Hoàn thành quest ngay lập tức.

Permission: rpg.admin.quest

## /rpg quest reset <player> <questId>

Reset quest về trạng thái ban đầu.

Permission: rpg.admin.quest

## /rpg quest list <player>

Xem danh sách quest đang active.

Permission: rpg.admin.quest

---

# Dungeon & Raid Commands

## /rpg dungeon start <dungeonId>

Mở dungeon (bỏ qua điều kiện).

Permission: rpg.admin.dungeon

## /rpg dungeon reset <player>

Reset lockout dungeon tuần này.

Permission: rpg.admin.dungeon

## /rpg raid start <raidId>

Mở raid.

Permission: rpg.admin.raid

## /rpg raid reset <player>

Reset lockout raid.

Permission: rpg.admin.raid

---

# Event Commands

## /rpg event start <eventType>

Kích hoạt event ngay lập tức.

Permission: rpg.admin.event

## /rpg event stop

Dừng event đang chạy.

Permission: rpg.admin.event

## /rpg event schedule <type> <time>

Lên lịch event.

Permission: rpg.admin.event

## /rpg event force <eventType> <floor>

Force spawn event tại Floor chỉ định.

Permission: rpg.admin.event

---

# Guild Commands

## /rpg guild list

Xem danh sách guild.

Permission: rpg.admin.guild

## /rpg guild info <guildName>

Xem thông tin guild.

Permission: rpg.admin.guild

## /rpg guild disband <guildName>

Giải tán guild.

Permission: rpg.admin.guild (Level 4+)

## /rpg guild setlevel <guildName> <level>

Đặt guild level.

Permission: rpg.admin.guild (Level 4+)

---

# Moderation Commands

## /rpg ban <player> <reason> [duration]

Cấm người chơi.

Permission: rpg.admin.ban (Level 3+)

## /rpg unban <player>

Bỏ cấm.

Permission: rpg.admin.ban (Level 3+)

## /rpg mute <player> <duration>

Mute chat.

Permission: rpg.admin.ban (Level 2+)

## /rpg kick <player> <reason>

Kick khỏi server.

Permission: rpg.admin.ban (Level 1+)

---

# Rollback Commands

## /rpg rollback player <player> <timestamp>

Rollback dữ liệu người chơi về thời điểm.

Yêu cầu backup có sẵn.

Permission: rpg.admin.rollback (Level 4+)

## /rpg rollback confirm

Xác nhận rollback.

Permission: rpg.admin.rollback (Level 4+)

---

# Admin Panels

## Player Inspector

GUI hiển thị:

Player Info (UUID, Name, IP)

Level, EXP, Class, Vision

Stats (8 chỉ số + derived)

Inventory

Quest Progress

Skill Tree

Mastery Progress

Guild Info

Login History

Action Log

Nút: Edit, Reset, Kick, Ban

## Quest Inspector

GUI hiển thị:

Danh sách tất cả quest

Trạng thái từng quest (active/completed/available)

Quest flag viewer

Nút: Start, Complete, Reset

## Economy Inspector

GUI hiển thị:

Server Gold Pool

Top Gold Holders

Item Circulation (item nào nhiều nhất)

Drop Rate Monitor

Enhancement Stats (bao nhiêu lần thành công/thất bại)

Nút: Adjust Rate, Spawn Item, Clear

## World Event Inspector

GUI hiển thị:

Active Events

Event History

Event Schedule

Event Participation Stats

Nút: Start, Stop, Schedule

---

# Anti-Abuse Protection

Admin commands log toàn bộ.

Log format:

[Timestamp] [Admin] [Command] [Target] [Result]

Review command log:

/rpg admin log [player] [page]

---

# Debug Mode

## /rpg debug <on/off>

Bật debug cho bản thân admin.

Hiển thị thông tin ẩn:

Damage calculation từng bước

Packet flow

Event trigger

## /rpg debug player <player>

Debug người chơi cụ thể.

Thấy được:

Client-Server sync status

Last save time

Active capabilities

---

# Ban & Appeal System

## Ban Types

Warning: Cảnh báo (0 ngày)

Temp Ban: 1-30 ngày

Perm Ban: Vĩnh viễn

## Appeal

Người chơi bị ban có thể gửi appeal (web form).

Admin xem appeal trong panel.

---

# Future Expansion

Web Admin Panel (HTTP API)

Automated Anti-Cheat

Admin Mobile App

Audit Report Generator

Server Health Dashboard
