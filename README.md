# JavaProject_PiKaChu

## 球的物理

- 一般：``x`` ``y``個有基本速度（值不變），依照碰撞情況改變 ``x`` 方向，做``類拋物線運動``（`y`速度每一次更新加1，方向會逐漸往下）。
    - 碰撞情況：皮卡丘與球有交集的當下，皮卡丘的操作方向為。。。
        1. 無：「僅向上」的直線運動 (僅有y速度) 
        2. 左邊：「向左上」的拋物線運動 (``x``速度小於0、``y``速度遞增的類拋物線) 
        3. 右邊：「向右上」的拋物線運動 (``x``速度大於0、``y``速度遞增的類拋物線)
    
- 扣球：
    1. 皮卡丘與球有交集
    2. 檢查「皮卡丘是否跳起」以及「是否按下扣球按鍵」
    3. 如果為真：``x`` ``y``速度值會變一樣且比一般速度大，一樣做類拋物線運動。
    
- 功能
    1.`R`鍵重置球的位置。
    
## Class Discription

- Launcher：與遊戲的接口，之後可以在此新增不同模式的選擇，開啟不同模式的主遊戲物件。
- Display：建立視窗(JFRame)以及畫布(Canvas)物件，
- KeyManager：將按下的按鍵用布林陣列`keys[256]`儲存起來。e.g. `W`，keycode = 87，當我按下`W`，`keys[87]` = true。
- Pikachu1P：依照按鍵方向行動。`W`/ up, `S`/ down, `A`/ left, `D`/ right, `Space` / spike。
- Pikachu2P：依照按鍵方向行動。`up_arrow`/ up, `down_arrow`/ down, `left_arrow`/ left, `right_arrow`/ right, `right_shift` / spike。
- Ball：
