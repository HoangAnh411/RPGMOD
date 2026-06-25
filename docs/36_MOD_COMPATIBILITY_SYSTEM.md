# PHASE 36 - MOD COMPATIBILITY SYSTEM ⭐⭐⭐

## Mục tiêu

Vì đây là modpack, phải đảm bảo tương thích với các mod chính.

API layer để dễ dàng tích hợp và mở rộng.

---

# Package

com.rpgpack.compat

com.rpgpack.api

---

# Files Required

ModCompatManager.java

CompatAdapter.java

EpicFightAdapter.java

CuriosAdapter.java

JEIAdapter.java

JadeAdapter.java

JourneyMapAdapter.java

WaystonesAdapter.java

RPGPackAPI.java

IntegrationEvent.java

---

# Compatibility Matrix

| Mod | Priority | Status |
|-----|----------|--------|
| Epic Fight | REQUIRED | Core combat |
| Curios API | REQUIRED | Vision slot, Ring, Necklace, Artifact |
| GeckoLib | HIGH | Animation |
| JEI | HIGH | Recipe viewer |
| Jade | MEDIUM | Entity info HUD |
| JourneyMap | MEDIUM | Minimap integration |
| Waystones | MEDIUM | Fast travel |
| Corail Tombstone | LOW | Death system |
| Tetra | LOW | Custom tools |
| Iron's Spells | LOW | Spell mod compat |

---

# Architecture

RPGPack Core
↓
API Layer (rpgpack-api)
↓
Integration Layer (rpgpack-compat)
↓
Epic Fight Adapter | Curios Adapter | JEI Adapter | ...

---

# API Layer

## Mục tiêu

Cung cấp interface để mod khác tích hợp với RPGMOD.

## RPGPackAPI.java

```java
// Lấy dữ liệu người chơi
PlayerData getPlayerData(Player player);

// Đăng ký custom skill
void registerSkill(BaseSkill skill);

// Lấy Vision của người chơi
ElementType getPlayerVision(Player player);

// Tính damage với đầy đủ modifier
float calculateDamage(Player attacker, Entity target, BaseSkill skill);

// Đăng ký custom element reaction
void registerReaction(ReactionType type, ReactionHandler handler);

// Event: PlayerLevelUp, PlayerClassChange, VisionChange
```

## Integration Events

RPGPackEvent.PlayerLevelUp

RPGPackEvent.ClassChanged

RPGPackEvent.VisionChanged

RPGPackEvent.SkillUsed

RPGPackEvent.BossDefeated

RPGPackEvent.DungeonCleared

---

# Epic Fight Adapter

## Trách nhiệm

Tích hợp RPG stats vào Epic Fight combat.

## Tích hợp

Stamina sync: RPG END → Epic Fight Stamina

Attack Speed sync: RPG AGI → Epic Fight Attack Speed

Damage sync: RPG Stats → Epic Fight Damage Formula

Poise sync: RPG Poise → Epic Fight Poise

Dodge sync: RPG Dodge → Epic Fight Dodge

## Epic Fight Events

onHitEntity → RPG Damage Calculation

onDodge → RPG Perfect Dodge Check

onGuard → RPG Perfect Guard Check

onStagger → RPG Poise Break

---

# Curios Adapter

## Slots Registered

vision — Vision Item

ring_1, ring_2 — Ring Equipment

necklace — Necklace Equipment

artifact — Artifact Equipment

## Curios Events

onEquip → VisionManager.activeVision()

onUnequip → VisionManager.deactiveVision()

onSlotChange → Sync packet to client

---

# JEI Adapter

## Recipe Categories

Blacksmith Recipes

Alchemist Recipes

Vision Forge Recipes

Boss Forge Recipes

## JEI Integration

Hiển thị recipe trong JEI panel.

Click recipe → xem chi tiết.

JEI search: "@rpgpack" để lọc recipe RPG.

---

# Jade Adapter

## HUD Info

Khi nhìn vào:

Player: Class, Level, Vision, Guild

Mob: Level, Element, HP value

Boss: Boss Name, Phase, HP %

Dungeon Chest: Loot table preview

## Jade Config

Người chơi có thể bật/tắt từng loại info.

---

# JourneyMap Adapter

## Map Markers

Dungeon Entrance

Boss Arena

World Event Zone

Town

NPC Location (Trainer, Blacksmith, etc.)

Floor Portal

## Custom Icons

Mỗi loại marker có icon riêng.

---

# Waystones Adapter

## Integration

Có thể đặt Waystone tại:

Town

Dungeon Entrance (sau khi clear)

House

Guild Hall

## Restriction

Không đặt Waystone trong:

Dungeon instance

Raid instance

Boss Arena

PvP Zone

---

# Mod Detection

Tự động phát hiện mod có được load không.

Nếu không có mod → adapter bị disable.

Không crash nếu thiếu mod optional.

```java
if (ModList.get().isLoaded("jei")) {
    JEIAdapter.init();
}
if (ModList.get().isLoaded("jade")) {
    JadeAdapter.init();
}
```

---

# Config System

## Mod Compatibility Config

config/rpgpack/compat.toml

```toml
[compat]
epicfight = true      # required
curios = true          # required

[compat.optional]
jei = true
jade = true
journeymap = true
waystones = true
```

## Admin Config

Disable integration nếu gây vấn đề.

Log warning nếu mod thiếu required compat.

---

# Future Expansion

Create Mod (compat with Create trains/automation)

Farmer's Delight (compat food system)

Ars Nouveau (spell + vision combo)

Botania (magic + element system)

API Documentation Website
