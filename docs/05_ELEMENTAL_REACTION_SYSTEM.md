# PHASE 5 - ELEMENTAL REACTIONS

## Mục tiêu

Tạo hệ thống phản ứng nguyên tố giống Genshin Impact.

Mọi phản ứng phải:

* Scale theo chỉ số nhân vật
* Scale theo cấp kỹ năng
* Scale theo cấp Vision

---

# Package

com.rpgpack.effects

com.rpgpack.skills

---

# Files Required

ElementalStatus.java

ElementalReactions.java

ReactionCalculator.java

ReactionType.java

EffectInit.java

---

# Status Effects

WET

CHILLED

BURNED

ELECTRIFIED

FROZEN

---

# Wet

Thời gian:

5 giây

Giảm:

10% Move Speed

---

# Chilled

Thời gian:

5 giây

Giảm:

15% Attack Speed

---

# Burned

Thời gian:

5 giây

Gây DOT

Mỗi giây:

2 + (INT × 0.1)

---

# Electrified

Thời gian:

5 giây

Mỗi giây:

1 lần Shock

---

# Frozen

Thời gian:

3 giây

Không thể di chuyển.

---

# Reaction Matrix

Wet + Ice

=

Frozen

---

Wet + Lightning

=

Electro Charged

---

Wet + Fire

=

Vaporize

---

Burned + Lightning

=

Overload

---

# Frozen

Hiệu ứng:

Root

Stun

Freeze

---

# Electro Charged

Damage:

5 + (INT × 0.5)

Radius:

5 blocks

Chain Count:

3

---

# Vaporize

Damage Multiplier:

1.5

(INT / 100)

---

# Overload

Damage:

10 + (INT × 1.5)

Radius:

4 blocks

Knockback:

Strong

---

# Future Reactions

Melt

Superconduct

Bloom

Hyperbloom

Burgeon

Version 2.
