# 11_MULTIPLAYER_BALANCE.md

# PHASE 11 - MULTIPLAYER BALANCE

## Mục tiêu

Ngăn tình trạng:

* Berserker quá mạnh
* Mage one-shot
* Assassin vô dụng late game
* Warrior không ai chơi

---

# Package

com.rpgpack.balance

---

# Design Philosophy

Không có class mạnh nhất.

Chỉ có class phù hợp tình huống.

---

# Warrior

Vai trò:

Tank

Frontline

Crowd Control

---

Điểm mạnh:

Defense

HP

Stagger

---

Điểm yếu:

Damage thấp

Mobility thấp

---

# Berserker

Vai trò:

Melee DPS

---

Điểm mạnh:

Damage cao

AOE mạnh

---

Điểm yếu:

Tốn Stamina

Defense thấp

---

# Assassin

Vai trò:

Burst DPS

---

Điểm mạnh:

Crit

Mobility

Boss Killer

---

Điểm yếu:

Máu thấp

AOE yếu

---

# Mage

Vai trò:

Elemental DPS

Control

---

Điểm mạnh:

Reaction Damage

AOE

Utility

---

Điểm yếu:

Máu thấp

Mana Dependency

---

# Party Scaling

1 Player

100%

---

2 Players

175%

---

3 Players

240%

---

4 Players

300%

---

# Boss HP Formula

BossHP

=

BaseHP × PartyModifier

---

# Boss Damage Formula

BossDamage

=

BaseDamage

×

(1 + Players × 0.1)

---

# Anti One Shot System

Damage nhận tối đa:

90% Current HP

trong 1 hit.

---

# PvP Scaling

PvE

100%

---

PvP

70%

Damage Modifier

---

# Critical Scaling

PvP Crit

Giảm 30%

---

# Healing Scaling

PvP Healing

Giảm 40%

---

# Elemental Reaction PvP

Frozen

50% Duration

---

Electro Charged

50% Damage

---

Overload

Knockback Reduced

---

# Threat System

Warrior:

+300% Threat

---

Berserker:

+100%

---

Mage:

+50%

---

Assassin:

-25%

---

# Future Content

Guild War

Arena

Ranked PvP

Territory Control
