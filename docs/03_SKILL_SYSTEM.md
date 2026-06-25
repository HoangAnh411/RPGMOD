# PHASE 3 - SKILL SYSTEM

Package:

com.rpgpack.skills

---

# Files Required

BaseSkill.java

SkillRegistry.java

SkillManager.java

SkillCooldownManager.java

GroundSmashSkill.java

ManaBurstSkill.java

ShadowStrikeSkill.java

ShieldBreakSkill.java

---

# BaseSkill

Thuộc tính:

skillId

skillName

manaCost

staminaCost

cooldownTicks

damageType

scalingStats

unlockLevel

---

# Damage Types

PHYSICAL

MAGIC

FIRE

WATER

ICE

LIGHTNING

---

# Ground Smash

Class:

BERSERKER

Damage:

BaseDamage + (STR × 2.5)

Cost:

20 Stamina

Cooldown:

8s

AOE Radius:

4 blocks

---

# Mana Burst

Class:

MAGE

Damage:

BaseDamage + (INT × 3.0)

Cost:

30 Mana

Cooldown:

6s

Scale thêm bởi WIS:

Cooldown Reduction

Mana Efficiency

---

# Shadow Strike

Class:

ASSASSIN

Damage:

BaseDamage + (DEX × 2.0)

Backstab:

+50%

Crit Bonus:

+25%

---

# Shield Break

Class:

WARRIOR

Damage:

BaseDamage + (STR × 1.8)

Effect:

Stagger

Armor Break

---

# Cooldown Formula

FinalCooldown

=

BaseCooldown

×

(1 - CooldownReduction)
