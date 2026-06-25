# AI DEV RULES - RPG MODPACK PROJECT

## MỤC TIÊU

AI phải hoạt động như một lập trình viên thực thụ:

- Có tiến trình rõ ràng
- Không quên trạng thái dự án
- Không làm lan man
- Luôn tiếp tục đúng phần đang làm dở

---

# RULE 1 - BẮT BUỘC ĐỌC STATE

Trước khi làm bất kỳ việc gì:

- PHẢI đọc file `PROJECT_STATE.md`
- PHẢI đọc file `TASK_QUEUE.md` (nếu có)

❌ Tuyệt đối không được bắt đầu code khi chưa đọc state.

---

# RULE 2 - CHỈ LÀM THEO TODO NEXT

AI chỉ được phép:

- Làm task trong `TODO NEXT`
- Hoặc task được user chỉ định trực tiếp

❌ Không tự ý nhảy sang system khác trong roadmap.

---

# RULE 3 - GIỚI HẠN PHẠM VI

Mỗi lần trả lời chỉ được:

- Làm tối đa 1–3 task nhỏ
- Không được triển khai toàn bộ hệ thống lớn trong 1 lần

Ví dụ:

✔ OK:

- Implement ClassType
- Fix packet sync bug

❌ NOT OK:

- Làm toàn bộ Vision + Combat + Skill Tree

---

# RULE 4 - BẮT BUỘC UPDATE STATE

Sau mỗi lần hoàn thành task:

AI PHẢI update:

- `PROJECT_STATE.md`

Bao gồm:

- Task completed → [x]
- Task in progress → cập nhật lại
- TODO NEXT → thay đổi nếu cần

❌ Không được bỏ qua bước này.

---

# RULE 5 - KHÔNG TẠO SYSTEM NGOÀI ROADMAP

AI không được:

- Tự thêm feature mới
- Tự mở rộng scope
- Tự thiết kế system không có trong roadmap

Nếu user yêu cầu:

→ phải hỏi lại hoặc đề xuất vào backlog

---

# RULE 6 - GIỮ TÍNH NHẤT QUÁN CODEBASE

AI phải:

- Giữ đúng package structure
- Không đổi architecture tùy tiện
- Không merge logic sai tầng

Ví dụ:

✔ OK:
com.rpgpack.skills

❌ NOT OK:
com.random.newsystem.skillhack

---

# RULE 7 - ƯU TIÊN PLAYABLE OVER PERFECT

Nếu có 2 lựa chọn:

- 80% hoàn thiện nhưng chạy được
- 100% nhưng chưa chạy

→ LUÔN chọn 80% playable

---

# RULE 8 - KHÔNG REWRITE TOÀN BỘ CODE

AI chỉ được:

- Sửa phần cần thiết
- Thêm phần thiếu

❌ Không được rewrite entire file trừ khi user yêu cầu.

---

# RULE 9 - STATE IS TRUTH

Trong mọi trường hợp:

PROJECT_STATE.md > memory > chat context

Nếu có mâu thuẫn:

→ PHẢI ưu tiên PROJECT_STATE.md

---

# RULE 10 - CONTINUATION MODE

Khi user nói:

- "tiếp đi"
- "làm tiếp"
- "ok tiếp"

AI phải:

- Đọc TODO NEXT
- Làm tiếp đúng phần đang dang dở
- Không nhắc lại kiến trúc trừ khi cần

---

# RULE 11 - OUTPUT FORMAT

Mỗi lần trả lời code:

1. Ngắn gọn logic
2. Code đầy đủ file
3. Không lan man giải thích dài

---

# RULE 12 - BUG HANDLING

Nếu gặp lỗi:

- Ghi vào PROJECT_STATE.md → KNOWN BUGS
- Không được ignore
- Không được “giả định đã fix”

---

# FINAL PRINCIPLE

## "AI IS A DEV, NOT A DESIGNER"

- Không design lại hệ thống mỗi lần chat
- Chỉ implement theo state
- Chỉ đi forward, không loop lại
