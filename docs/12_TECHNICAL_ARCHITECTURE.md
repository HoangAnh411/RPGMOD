# PHASE 12 - TECHNICAL ARCHITECTURE

## Mục tiêu

Định nghĩa kiến trúc kỹ thuật của toàn bộ mod.

Mọi hệ thống phải tuân thủ tài liệu này.

---

# Target Environment

Minecraft 1.20.1

NeoForge

Java 17

Curios API

Epic Fight

---

# Core Architecture

Player
↓
Capability
↓
Stat System
↓
Class System
↓
Skill System
↓
Vision System
↓
Reaction System
↓
Combat System

---

# Data Ownership Rule

SERVER là nguồn dữ liệu duy nhất.

Client chỉ hiển thị.

Không cho phép:

- Client tăng stat
- Client tăng exp
- Client đổi class
- Client mở skill

---

# Capability Layout

PlayerCapability

Lưu:

Level

Exp

Stat Points

Skill Points

Current Class

Current Vision

Stats

Mastery

Unlocked Skills

Quest Progress

Dungeon Progress

---

# Packet Structure

Client
↓
C2S Packet
↓
Server Validate
↓
Server Update Data
↓
S2C Sync Packet
↓
Client Refresh GUI

---

# Sync Events

Sync khi:

Login

Respawn

Dimension Change

Level Up

Class Change

Vision Change

Skill Learn

Quest Complete

---

# Save System

Sử dụng NBT.

Player Data phải tồn tại sau:

Death

Logout

Server Restart

---

# Event Bus Usage

Player Events

Combat Events

Quest Events

Dungeon Events

Boss Events

Vision Events

---

# Registry Structure

ItemInit

EffectInit

SkillInit

MenuInit

EntityInit

PacketInit

---

# Coding Rules

Không viết logic trong Screen.

Không viết logic trong Packet.

Không viết logic trong Event.

Event chỉ gọi Manager.

Manager xử lý logic.

---

# Manager Pattern

Ví dụ:

CombatEvent

↓

CombatManager

↓

DamageCalculator

↓

Result

---

# Future Multiplayer

Mọi dữ liệu phải hỗ trợ:

Dedicated Server

LAN

Singleplayer

Cross World
