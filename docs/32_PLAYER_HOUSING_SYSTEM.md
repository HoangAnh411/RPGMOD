# PHASE 32 - PLAYER HOUSING SYSTEM ⭐

## Mục tiêu

Nếu server lâu dài, Housing tạo không gian riêng cho người chơi.

Giữ chân người chơi qua customization và sưu tầm.

---

# Package

com.rpgpack.housing

---

# Files Required

HouseManager.java

HouseData.java

HouseRegion.java

HousePermissionManager.java

HouseDecorationManager.java

HouseScreen.java

GuildHouseManager.java

---

# House Types

## Personal House

1 người chơi sở hữu.

Có thể mời người khác vào thăm.

## Guild House

Cả Guild sở hữu.

Guild Leader quản lý.

Tất cả thành viên có thể vào.

---

# House Acquisition

## Personal House

Yêu cầu:

Level 30+

Gold × 50000

House Deed (quest reward hoặc mua)

Chọn vị trí (trong House Zone).

## Guild House

Yêu cầu:

Guild Level 10+

Gold × 500000

Guild House Deed

Vị trí cố định (Guild Territory).

---

# House Levels

## Level 1 — Cottage

Size: 7×7

Decoration slots: 20

Cost: Default

## Level 2 — House

Size: 10×10

Decoration slots: 40

Cost: Gold × 20000

## Level 3 — Villa

Size: 14×14

Decoration slots: 80

Cost: Gold × 50000

## Level 4 — Mansion

Size: 18×18

Decoration slots: 150

Cost: Gold × 100000

## Level 5 — Estate

Size: 24×24

Decoration slots: 300

Cost: Gold × 200000

---

# Decoration Categories

## Furniture

Bàn, ghế, giường, tủ, kệ sách.

Craft tại Blacksmith hoặc mua.

## Lighting

Đèn, nến, đèn lồng, đèn chùm.

## Decorative

Thảm, tranh, rèm, cây cảnh.

## Functional

Crafting Station (đặt trong nhà).

Storage Chest (extra inventory).

Training Dummy (test damage).

## Trophy

Boss Trophy: Treo đầu boss đã giết.

Achievement Wall: Hiển thị achievement.

Leaderboard Display.

## Boss Statue

Mô hình boss thu nhỏ.

Reward từ First Kill boss đó.

Có animation nhẹ.

---

# House Permissions

## Owner

Full control: Build, Destroy, Invite, Kick, Lock.

## Friend

Vào thăm, sử dụng functional items.

Không thể thay đổi decoration.

## Visitor

Chỉ vào thăm.

Không tương tác với đồ.

## Public

Ai cũng vào được (nếu owner bật).

---

# House Visit System

Teleport đến nhà bạn bè.

House Browser: Xem danh sách nhà public.

House Rating: Vote sao (1-5).

House of the Week: Bình chọn hàng tuần.

---

# Guild House

## Exclusive Features

Guild Meeting Hall: Tất cả thành viên gặp mặt.

Guild Vault: Shared storage (tab riêng cho material, equipment).

Guild Buff Shrine: Kích hoạt buff (tốn Guild Currency).

War Room: Bản đồ Guild War.

## Guild Trophy

Guild Boss Trophy

Guild War Trophy

Guild Achievement Display

---

# Housing Storage

House Chest: Extra storage (không liên kết inventory chính).

Decoration Storage: Lưu đồ đã mua nhưng chưa dùng.

Material Storage: Riêng cho crafting.

---

# Housing Economy

## House Market

Mua bán decoration.

Auction rare decoration.

## House Tax

Weekly tax (Gold).

Cao hơn cho nhà to hơn.

Không trả thuế → house bị khóa đến khi trả.

---

# House UI

House Management Screen:

Upgrade House

Decoration Mode (grid placement)

Permission Settings

Visitor Log

Tax Status

---

# Future Expansion

House Blueprint (save/load layout)

Neighborhood System (khu dân cư)

House PvP Arena (đấu trong nhà)

Housing Leaderboard

Seasonal Furniture Set
