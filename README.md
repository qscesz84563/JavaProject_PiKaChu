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
    4. 透過擊球累積，能量條若為滿，扣球速度會極快。
    
- 功能：
    1. `R`鍵重置球的位置。
    2. 能量條
    
## Class Discription

- `Launcher`：與遊戲的接口，之後可以在此新增不同模式的選擇，開啟不同模式的主遊戲物件。  
- `Display`：建立視窗(JFRame)以及畫布(Canvas)物件。  
- `KeyManager`：將按下的按鍵用布林陣列`keys[256]`儲存起來。e.g. `W`，keycode = 87，當我按下`W`，`keys[87]` = true，鬆鍵後`keys[87]` = false。會加到`Game2PMode1`。 
- `Pikachu1P`：依照按鍵方向行動。`W`/ up, `S`/ down, `A`/ left, `D`/ right, `Space` / spike。  
- `Pikachu2P`：依照按鍵方向行動。`up_arrow`/ up, `down_arrow`/ down, `left_arrow`/ left, `right_arrow`/ right, `right_shift` / spike。當角色往上跳起，為了避免二段跳的可能，需要等落地後才會繼續對往上的操作有反應。  
- `Ball`：判斷碰撞，主要行為。。。  
    1. 碰到左邊界：使`x`速度相反，`y`速度為正（往下掉）。  
    2. 碰到右邊界：同碰到左邊界。  
    3. 碰到上邊界：`x`速度維持，`y`速度為正（往下掉）。  
    4. 碰到下邊界：`x`速度維持，`y`速度為負（往上一段距離），因為`y`速度每次更新會加一，會有撞到地板反彈的效果（不會高於網子）。（因為勝負是用球是否落地來判斷，之後可能會拿掉。）  
    5. 碰到網子（目前有bug）  
- `Game2PMode`：所有角色物件都initialize在裡面。  

## Game Loop

<pre>
initialize
|
ˇ
Game2PMode.update()<------------+     
|                               |
ˇ                               |
player1.update()          ball.paint(Graphics g)
player2.update()          player2.paint(Graphics g)
ball.update()             player1.paint(Graphics g)
|                               ＾
ˇ                               |
Game2PMode.paint()--------------+
</pre>
    
update()：做完判斷（按鍵輸入或碰撞）後設定角色的新的座標。</br>
paint(Graphics g)：`g`為`Game2PMode`的畫筆物件，且已經跟Display的畫布連結，所以角色物件的paint()也會呈現在視窗上。 

    
    
    
    
    
    
    
