# PHASE 17 - SAVE & DATABASE SYSTEM

## Mục tiêu

Đảm bảo dữ liệu ổn định.

Không mất dữ liệu khi:

- Crash
- Restart
- Update Version

---

# Package

com.rpgpack.persistence

---

# Files Required

PlayerDataSerializer.java

SaveManager.java

DataVersionManager.java

BackupManager.java

MigrationManager.java

---

# Storage Method

Primary:

Capability + NBT

---

Future:

MySQL

PostgreSQL

MongoDB

---

# Player Save Schema

Player UUID

↓

Core Data

↓

Class Data

↓

Vision Data

↓

Skill Data

↓

Mastery Data

↓

Quest Data

↓

Guild Data

---

# Core Data

Level

EXP

Stat Points

Skill Points

Gold

---

# Class Data

Current Class

Class Progress

Unlocked Skills

---

# Vision Data

Vision Type

Vision Level

Vision EXP

Vision Passives

---

# Skill Data

Unlocked Skills

Skill Levels

Cooldown Data

Skill Tree Data

---

# Weapon Mastery Data

Sword

Greatsword

Bow

Staff

Dagger

Spear

Katana

Dual Blades

---

# Quest Data

Active Quests

Completed Quests

Quest Flags

Story Progress

---

# Guild Data

Guild ID

Guild Rank

Guild Contribution

---

# Save Triggers

Player Logout

Player Death

Dimension Change

Level Up

Quest Complete

Server Shutdown

---

# Auto Save

Every 5 Minutes

---

# Backup System

Daily Backup

Weekly Backup

Manual Backup

---

# Version Control

Data Version

Current Version

Migration Version

---

# Example

Version 1

8 Stats

---

Version 2

Add Vision Level

---

Migration:

Convert Old Save

→

New Save

---

# Anti Corruption

Checksum Validation

Backup Recovery

Data Verification

---

# Dedicated Server Support

Target:

100 Concurrent Players

---

# Future Database Support

MySQL

Use Cases:

Guild Rankings

Auction House

Cross Server Data

---

# Performance Goals

Save Time

< 50 ms

---

Load Time

< 100 ms

---

Memory Usage

< 10 MB / Player

---

# Recovery Strategy

Primary Save

↓

Backup Save

↓

Emergency Recovery

↓

Default Safe State

---

# Security Rules

Client Never Owns Data

Server Always Validates Data

No Direct Client Save Access

No Client Authority
