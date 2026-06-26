# PHASE 22 - COMBAT FRAMEWORK ⭐⭐⭐

## Mục tiêu

Đây là "luật chiến đấu" chung cho toàn bộ game.

Mọi hệ thống combat (Class, Skill, Vision, Reaction, Epic Fight) đều phải tuân thủ framework này.

Không có ngoại lệ.

---

# Package

com.rpgpack.combat

---

# Files Required

CombatFramework.java

StaminaManager.java

PoiseManager.java

GuardManager.java

DodgeManager.java

CounterManager.java

ExecutionManager.java

DownedManager.java

BossArmorManager.java

CombatFlowManager.java

---

# Combat Flow

Attack
↓
Hit Detection
↓
Damage Calculation
↓
Poise Damage
↓
Reaction Check
↓
Final Result

---

# Stamina System

## Max Stamina

100 + (END × 8)

## Stamina Costs

Light Attack: 5

Heavy Attack: 15

Dodge Roll: 20

Guard: 10 + DamageBlocked × 0.5

Sprint: 2 / second

## Stamina Regen

Base: 5 / second

END Bonus: +0.5 / second per 10 END

## Stamina Break

Khi Stamina = 0:

Không thể Dodge

Không thể Guard

Không thể Sprint

-20% Move Speed

Hồi phục sau: 2 giây

---

# Poise System

## Poise Formula

Poise = (VIT × 2) + END

## Poise Damage

Mỗi đòn đánh gây Poise Damage:

Light Attack: 10

Heavy Attack: 30

Skill: 50

Boss Skill: 100

## Poise Break

Khi Poise về 0:

Stagger 1.5 giây

+30% Damage Taken trong thời gian stagger

## Poise Regen

Poise hồi sau 3 giây không nhận sát thương.

Regen: Full Poise trong 2 giây.

---

# Hyper Armor

## Định nghĩa

Hyper Armor = Không bị stagger khi đang tấn công.

## Nguồn

Warrior Skill: Fortress Stance

Berserker Skill: Heavy Swing

Boss Phases

## Cơ chế

Khi Hyper Armor active:

Vẫn nhận Damage

Vẫn nhận Poise Damage

Nhưng KHÔNG bị stagger

---

# Guard System

## Normal Guard

Giảm: 70% Damage

Tốn: 10 Stamina + (DamageBlocked × 0.5)

Không thể di chuyển.

## Guard Break

Khi Stamina cạn khi đang Guard:

Stagger 2 giây

+50% Damage Taken

---

# Perfect Guard

## Điều kiện

Chặn đúng thời điểm (trong 6 ticks trước khi trúng đòn).

## Hiệu ứng

0 Damage

+Counter Window (10 ticks)

+5 Stamina

+5% Ultimate Gauge

---

# Dodge Roll

## Cơ chế

iFrame: 12 ticks (0.6 giây)

Distance: 3 + (AGI × 0.02) blocks

Cost: 20 Stamina

---

# Perfect Dodge

## Điều kiện

Né trong 8 ticks trước khi trúng đòn.

## Hiệu ứng

+10% Ultimate Gauge

+5 Stamina

Time Slow 0.5 giây (chỉ ảnh hưởng kẻ địch xung quanh)

Tăng iFrame thêm 4 ticks

---

# Counter Attack

## Điều kiện

Thực hiện sau Perfect Guard (trong Counter Window 10 ticks).

## Hiệu ứng

+50% Damage

+30% Poise Damage

Không tốn Stamina

---

# Execution System

## Điều kiện

Mục tiêu ở trạng thái Downed.

## Hiệu ứng

Instant Kill (PvE)

+20% Ultimate Gauge

+10 Stamina

iFrame trong animation

---

# Downed State

## Trigger

Khi HP Boss về 0 (Phase chưa kết thúc)

Khi Poise Break trên Elite

## Cơ chế

Thời gian: 5 giây

Không thể hành động

+50% Damage Taken

Có thể bị Execution

---

# Boss Armor Gauge

## Cơ chế

Boss có thêm Armor Gauge.

Armor Gauge = Base HP × 0.3

## Armor Break

Khi Armor Gauge về 0:

Stagger Boss 3 giây

+50% Damage trong thời gian stagger

Boss mất Hyper Armor

## Armor Regen

Sau stagger, Armor hồi đầy sau 10 giây.

---

# Damage Calculation Flow

## Step 1: Base Damage

WeaponDamage + (STR × 0.5) → Physical

hoặc

SkillBase + (INT × 0.8) → Magic

## Step 2: Defense

FinalDamage = BaseDamage × (1 - DefenseReduction)

PhysicalDefense = VIT × 2

MagicDefense = WIS × 1.5

DefenseReduction = Defense / (Defense + 100 + Level × 10)

## Step 3: Elemental

ElementalBonus từ Vision

Reaction Bonus

Resistance Check

## Step 4: Critical

CritChance = DEX × 0.25%

CritDamage = 150% + (LUK × 0.5%)

## Step 5: Final

FinalDamage sau các modifier.

Ghi log nếu DEBUG mode.

---

# Combat States

IDLE → ATTACKING → RECOVERY → IDLE

IDLE → GUARDING → IDLE

IDLE → DODGING → IDLE

IDLE → STAGGERED → IDLE

IDLE → DOWNED → IDLE

---

# Future Expansion

Perfect Parry (reflect damage)

Riposte Chain

Weapon Clash

Environmental Finisher

Team Combo
